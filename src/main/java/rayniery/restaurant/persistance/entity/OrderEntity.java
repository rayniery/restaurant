package rayniery.restaurant.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "menu_order", schema = "restaurant")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(targetEntity = MenuItemEntity.class, fetch = FetchType.LAZY)
    MenuItemEntity menuItem;

    @ManyToOne(targetEntity = BillEntity.class)
    BillEntity bill;

    int quantity;
    Instant orderedTime;

    @NotNull
    BigDecimal totalPrice;
}
