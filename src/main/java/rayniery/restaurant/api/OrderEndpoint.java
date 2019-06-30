package rayniery.restaurant.api;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rayniery.restaurant.api.model.OrderRequest;
import rayniery.restaurant.api.model.OrderResponse;
import rayniery.restaurant.service.OrderService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(
        value = "/menu/",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderEndpoint {
    private final OrderService service;

    public OrderEndpoint(OrderService service) {
        this.service = service;
    }

    @GetMapping("order")
    public ResponseEntity<OrderResponse> getOrders(Pageable page) {
        return ResponseEntity.ok(service.getOrders(page));
    }

    @GetMapping("order/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("orderId") long id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @PostMapping("item/{itemId}/order")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable("itemId") long itemId,
                                                        @RequestBody @Valid OrderRequest request) {
        OrderResponse response = service.createOrder(itemId, request);
        return createdResourceResponse(response);
    }

    private static ResponseEntity<OrderResponse> createdResourceResponse(OrderResponse response) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getOrders().get(0).getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(OrderResponse.builder()
                        .orders(response.getOrders())
                        .build()
                );
    }
}
