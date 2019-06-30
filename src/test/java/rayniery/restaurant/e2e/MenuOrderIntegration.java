package rayniery.restaurant.e2e;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rayniery.restaurant.api.MenuItemEndpoint;
import rayniery.restaurant.api.OrderEndpoint;
import rayniery.restaurant.api.model.*;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


/**
 * This integration test relied on te data imported by import.sql file.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MenuOrderIntegration {
    @Autowired
    private OrderEndpoint endpoint;

    @Autowired
    private MenuItemEndpoint itemEndpoint;

    @Test
    public void shouldReturn7Orders() {
        OrderResponse response = endpoint.getOrders(Pageable.unpaged()).getBody();

        assertThat(response.getOrders().size(), is(7));
    }

    @Test
    public void shouldReturnOrderWithId4() {
        final long orderId = 4;
        OrderResponse response = endpoint.getOrderById(orderId).getBody();

        assertThat(response.getOrders().size(), is(1));
        assertThat(response.getOrders().get(0).getId(), is(orderId));
        assertThat(response.getOrders().get(0).getQuantity(), is(1));
        assertThat(response.getOrders().get(0).getTotalPrice().intValue(), is(200));
    }

    @Test
    public void shouldCreateNewOrder() {
        MenuItemResponse itemResponse = itemEndpoint.getMenuItemById(1).getBody();
        MenuItem menuItem = itemResponse.getMenuItems().get(0);
        Order order = Order.builder()
                .menuItem(menuItem)
                .quantity(10)
                .build();

        OrderRequest request = OrderRequest.builder()
                .order(order)
                .build();

        ResponseEntity<OrderResponse> response = endpoint.createOrder(menuItem.getId(), request);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        OrderResponse newOrder = endpoint.getOrderById(response.getBody().getOrders().get(0).getId()).getBody();

        assertThat(newOrder.getOrders().size(), is(1));
        assertThat(newOrder.getOrders().get(0).getQuantity(), is(10));
        assertThat(newOrder.getOrders().get(0).getTotalPrice().intValue(),
                is(menuItem.getPrice().multiply(BigDecimal.TEN).intValue()));
    }
}
