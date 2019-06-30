package rayniery.restaurant.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(builderClassName = "OrderResponseBuilder", toBuilder = true)
@AllArgsConstructor
@JsonDeserialize(builder = OrderResponse.OrderResponseBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {
    List<Order> orders;

    @JsonPOJOBuilder(withPrefix = "")
    public static class OrderResponseBuilder {
    }
}
