package rayniery.restaurant.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder(builderClassName = "MenuItemRequestBuilder", toBuilder = true)
@AllArgsConstructor
@JsonDeserialize(builder = MenuItemRequest.MenuItemRequestBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuItemRequest {
    @NotNull
    MenuItem menuItem;

    @JsonPOJOBuilder(withPrefix = "")
    public static class MenuItemRequestBuilder {
    }
}
