package rayniery.restaurant.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import rayniery.restaurant.persistance.entity.OrderEntity;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
@AllArgsConstructor
public class Order {
    long id;
    MenuItem menuItem;
    @Min(value = 1L, message = "Quantity must be greater than 0")
    int quantity;
    Instant orderedTime;
    BigDecimal totalPrice;

    public static Order fromEntity(OrderEntity entity) {
        return Order.builder()
                .id(entity.getId())
                .menuItem(MenuItem.fromEntity(entity.getMenuItem()))
                .quantity(entity.getQuantity())
                .orderedTime(entity.getOrderedTime())
                .totalPrice(entity.getTotalPrice())
                .build();
    }

    public OrderEntity toEntity() {
        return OrderEntity.builder()
                .menuItem(menuItem.toEntity())
                .orderedTime(orderedTime)
                .quantity(quantity)
                .totalPrice(totalPrice)
                .build();
    }
}
