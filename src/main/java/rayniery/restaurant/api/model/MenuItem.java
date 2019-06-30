package rayniery.restaurant.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import rayniery.restaurant.persistance.entity.MenuItemEntity;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class MenuItem {
    long id;
    String name;
    String description;
    String image;
    BigDecimal price;
    String additionalDetails;

    public static MenuItem fromEntity(MenuItemEntity entity) {
        return MenuItem.builder()
                .id(entity.getId())
                .name(entity.getName())
                .additionalDetails(entity.getAdditionalDetails())
                .description(entity.getDescription())
                .image(entity.getImage())
                .price(entity.getPrice())
                .build();
    }

    public MenuItemEntity toEntity() {
        return MenuItemEntity.builder()
                .additionalDetails(additionalDetails)
                .description(description)
                .image(image)
                .name(name)
                .price(price)
                .build();
    }
}
