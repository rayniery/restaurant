package rayniery.restaurant.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import rayniery.restaurant.persistance.entity.BillEntity;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class Bill {
    long id;
    @NotEmpty
    List<Order> orders;
    BigDecimal totalPrice;

    public static Bill fromEntity(BillEntity entity) {
        List<Order> orderList = entity.getOrders()
                .stream()
                .map(order -> Order.fromEntity(order))
                .collect(Collectors.toList());

        return Bill.builder()
                .id(entity.getId())
                .orders(orderList)
                .build();
    }
}
