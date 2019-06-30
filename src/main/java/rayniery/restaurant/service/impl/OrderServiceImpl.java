package rayniery.restaurant.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rayniery.restaurant.api.model.*;
import rayniery.restaurant.service.OrderService;
import rayniery.restaurant.persistance.entity.MenuItemEntity;
import rayniery.restaurant.persistance.entity.OrderEntity;
import rayniery.restaurant.persistance.repository.MenuItemRepository;
import rayniery.restaurant.persistance.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MenuItemRepository itemRepository;

    public OrderServiceImpl(OrderRepository repository, MenuItemRepository itemRepository) {
        this.orderRepository = repository;
        this.itemRepository = itemRepository;
    }

    @Override
    public OrderResponse getOrders(Pageable page) {
        List<Order> menuItems = orderRepository.findAll(page)
                .stream()
                .map(Order::fromEntity)
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orders(menuItems)
                .build();
    }

    @Override
    public OrderResponse getOrderById(long id) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(id);
        if (orderEntity.isPresent()) {
            Order item = Order.fromEntity(orderEntity.get());
            return OrderResponse.builder()
                    .orders(Arrays.asList(item))
                    .build();
        } else {
            return OrderResponse.builder()
                    .orders(Collections.emptyList())
                    .build();
        }
    }

    @Override
    public OrderResponse createOrder(long itemId, OrderRequest request) {
        Optional<MenuItemEntity> itemEntity = itemRepository.findById(itemId);
        if (!itemEntity.isPresent()) {
            throw new RuntimeException("Menu Item " + itemId + " does not exist");
        }

        int quantity = request.getOrder().getQuantity();
        BigDecimal price = itemEntity.get().getPrice().multiply(BigDecimal.valueOf(quantity));
        OrderEntity newOrder = OrderEntity.builder()
                .quantity(quantity)
                .totalPrice(price)
                .orderedTime(Instant.now())
                .menuItem(itemEntity.get())
                .build();

        OrderEntity savedEntity = orderRepository.save(newOrder);

        return OrderResponse.builder()
                .orders(Arrays.asList(Order.fromEntity(savedEntity)))
                .build();
    }
}
