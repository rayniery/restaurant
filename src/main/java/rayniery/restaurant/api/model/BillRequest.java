package rayniery.restaurant.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;

@Value
@Builder(builderClassName = "BillRequestBuilder", toBuilder = true)
@AllArgsConstructor
@JsonDeserialize(builder = BillRequest.BillRequestBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillRequest {
    @Valid
    Bill bill;

    @JsonPOJOBuilder(withPrefix = "")
    public static class BillRequestBuilder {
    }
}
