package rayniery.restaurant.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "menu_item", schema = "restaurant")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotNull
    String name;
    String description;
    String image;
    @NotNull
    BigDecimal price;
    String additionalDetails;
}
