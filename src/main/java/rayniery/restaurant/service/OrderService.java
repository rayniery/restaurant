package rayniery.restaurant.service;

import org.springframework.data.domain.Pageable;
import rayniery.restaurant.api.model.OrderRequest;
import rayniery.restaurant.api.model.OrderResponse;

public interface OrderService {
    OrderResponse getOrders(Pageable page);
    OrderResponse getOrderById(long id);
    OrderResponse createOrder(long id, OrderRequest request);
}
