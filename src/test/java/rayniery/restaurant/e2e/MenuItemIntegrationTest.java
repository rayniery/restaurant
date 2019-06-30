package rayniery.restaurant.e2e;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rayniery.restaurant.api.MenuItemEndpoint;
import rayniery.restaurant.api.model.MenuItem;
import rayniery.restaurant.api.model.MenuItemRequest;
import rayniery.restaurant.api.model.MenuItemResponse;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * This integration test relied on te data imported by import.sql file.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuItemIntegrationTest {
    @Autowired
    private MenuItemEndpoint endpoint;

    @Test
    public void shouldReturnSixMenuItems() {
        MenuItemResponse response = endpoint.getMenuItems(Pageable.unpaged(), "").getBody();

        assertThat(response.getMenuItems().size(), is(6));
    }

    @Test
    public void shouldReturn2Pizzas() {
        MenuItemResponse response = endpoint.getMenuItems(Pageable.unpaged(), "pizza").getBody();

        assertThat(response.getMenuItems().size(), is(2));
    }

    @Test
    public void shouldReturnSecondMenuItem() {
        MenuItemResponse response = endpoint.getMenuItemById(2).getBody();

        assertThat(response.getMenuItems().size(), is(1));
        assertThat(response.getMenuItems().get(0).getPrice().intValue(), is(350));
    }

    @Test
    public void shouldCreateNewMenuItem() {
        MenuItem item = MenuItem.builder()
                .name("Dominican Dumplings")
                .price(BigDecimal.TEN)
                .build();
        MenuItemRequest request = MenuItemRequest.builder()
                .menuItem(item)
                .build();

        MenuItemResponse response = endpoint.createMenuItem(request).getBody();

        assertThat(response.getMenuItems().size(), is(1));
        assertThat(response.getMenuItems().get(0).getPrice(), is(BigDecimal.TEN));
        assertThat(response.getMenuItems().get(0).getName(), is("Dominican Dumplings"));
    }

    @Test
    public void shouldUpdateChineseBun() {
        MenuItem item = MenuItem.builder()
                .name("blah")
                .price(BigDecimal.TEN)
                .build();
        MenuItemRequest request = MenuItemRequest.builder()
                .menuItem(item)
                .build();

        final long itemId = 3;
        ResponseEntity<MenuItemResponse> response = endpoint.updateMenuItem(request, itemId);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        MenuItemResponse updatedItem = endpoint.updateMenuItem(request, itemId).getBody();

        assertThat(updatedItem.getMenuItems().get(0).getId(), is(itemId));
        assertThat(updatedItem.getMenuItems().get(0).getPrice(), is(BigDecimal.TEN));
        assertThat(updatedItem.getMenuItems().get(0).getName(), is("blah"));
    }
}