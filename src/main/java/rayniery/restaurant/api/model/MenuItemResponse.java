package rayniery.restaurant.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(builderClassName = "MenuItemResponseBuilder", toBuilder = true)
@AllArgsConstructor
@JsonDeserialize(builder = MenuItemResponse.MenuItemResponseBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuItemResponse {
    List<MenuItem> menuItems;

    @JsonPOJOBuilder(withPrefix = "")
    public static class MenuItemResponseBuilder {
    }
}
