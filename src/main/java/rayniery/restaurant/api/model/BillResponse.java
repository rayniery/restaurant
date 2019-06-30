package rayniery.restaurant.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(builderClassName = "BillResponseBuilder", toBuilder = true)
@AllArgsConstructor
@JsonDeserialize(builder = BillResponse.BillResponseBuilder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillResponse {
    List<Bill> bills;

    @JsonPOJOBuilder(withPrefix = "")
    public static class BillResponseBuilder {
    }
}
