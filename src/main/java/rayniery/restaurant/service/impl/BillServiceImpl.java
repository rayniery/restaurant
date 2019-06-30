package rayniery.restaurant.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rayniery.restaurant.api.model.Bill;
import rayniery.restaurant.api.model.BillRequest;
import rayniery.restaurant.api.model.BillResponse;
import rayniery.restaurant.persistance.entity.BillEntity;
import rayniery.restaurant.persistance.entity.OrderEntity;
import rayniery.restaurant.persistance.repository.BillRepository;
import rayniery.restaurant.persistance.repository.OrderRepository;
import rayniery.restaurant.service.BillService;
import rayniery.restaurant.service.OrderService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public BillServiceImpl(BillRepository billRepository,
                           OrderService orderService,
                           OrderRepository orderRepository) {
        this.billRepository = billRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @Override
    public BillResponse getBills(Pageable page) {
        List<Bill> bills = billRepository.findAll(page)
                .stream()
                .map(Bill::fromEntity)
                .collect(Collectors.toList());

        List<BigDecimal> prices = calculatePrices(bills);

        return BillResponse.builder()
                .bills(billsWithPrices(bills, prices))
                .build();
    }

    @Override
    public BillResponse getBillById(long id) {
        Optional<BillEntity> billEntity = billRepository.findById(id);
        if (billEntity.isPresent()) {
            List<Bill> bill = Arrays.asList(Bill.fromEntity(billEntity.get()));
            List<BigDecimal> price = calculatePrices(bill);
            return BillResponse.builder()
                    .bills(billsWithPrices(bill, price))
                    .build();
        } else {
            return BillResponse.builder()
                    .bills(Collections.emptyList())
                    .build();
        }
    }

    @Override
    public BillResponse createBill(BillRequest request) {
        List<OrderEntity> orders = request.getBill()
                .getOrders()
                .stream()
                .map(order -> orderRepository.findById(order.getId()).get())
                .collect(Collectors.toList());

        BillEntity newBill = BillEntity.builder().build();
        BillEntity savedBill = saveBillOrder(orders, newBill);

        List<Bill> bill = Arrays.asList(Bill.fromEntity(savedBill));
        List<BigDecimal> price = calculatePrices(bill);

        return BillResponse.builder()
                .bills(billsWithPrices(bill, price))
                .build();
    }

    @Override
    @Transactional
    public BillResponse updateBill(long id, BillRequest request) {
        Optional<BillEntity> updatedBill = billRepository.findById(id);
        if (updatedBill.isPresent()) {
            List<OrderEntity> requestOrders = request.getBill()
                    .getOrders()
                    .stream()
                    .map(order -> orderRepository.findById(order.getId()).get())
                    .collect(Collectors.toList());
            List<OrderEntity> oldOrders = orderRepository.findByBill(updatedBill.get());
            oldOrders.forEach(order -> order.setBill(null));

            BillEntity savedBill = saveBillOrder(requestOrders, updatedBill.get());

            List<Bill> bill = Arrays.asList(Bill.fromEntity(savedBill));
            List<BigDecimal> price = calculatePrices(bill);

            return BillResponse.builder()
                    .bills(billsWithPrices(bill, price))
                    .build();
        } else {
            return createBill(request);
        }
    }

    private List<BigDecimal> calculatePrices(List<Bill> bills) {
        ArrayList<BigDecimal> prices = new ArrayList<>(bills.size());
        bills.forEach(bill -> {
            prices.add(bill.getOrders()
                    .stream()
                    .map(order -> order.getTotalPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
            );
        });

        return prices;
    }

    private List<Bill> billsWithPrices(List<Bill> bills, List<BigDecimal> prices) {
        List<Bill> billsAndPrices = new ArrayList<>(bills.size());
        for (int i = 0; i < bills.size(); ++i) {
            billsAndPrices.add(Bill.builder()
                    .id(bills.get(i).getId())
                    .orders(bills.get(i).getOrders())
                    .totalPrice(prices.get(i))
                    .build()
            );
        }

        return billsAndPrices;
    }

    private BillEntity saveBillOrder(List<OrderEntity> orders, BillEntity newBill) {
        newBill.setOrders(orders);
        BillEntity savedBill = billRepository.save(newBill);
        for (int i = 0; i < orders.size(); ++i) {
            orders.get(i).setBill(newBill);
        }
        orderRepository.saveAll(orders);
        return savedBill;
    }
}
