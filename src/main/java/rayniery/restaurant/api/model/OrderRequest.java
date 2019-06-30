package rayniery.restaurant.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Value
@Builder(builderClassName = "OrderRequestBuilder", toBuilder = true)
@AllArgsConstructor
@JsonDeserialize(builder = OrderRequest.OrderRequestBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {
    @Valid
    @NotNull
    Order order;

    @JsonPOJOBuilder(withPrefix = "")
    public static class OrderRequestBuilder {
    }
}
