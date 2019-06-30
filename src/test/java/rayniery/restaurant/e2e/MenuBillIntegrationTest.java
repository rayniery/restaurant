package rayniery.restaurant.e2e;

import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rayniery.restaurant.api.BillEndpoint;
import rayniery.restaurant.api.model.*;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * This integration test relied on te data imported by import.sql file.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MenuBillIntegrationTest {
    @Autowired
    private BillEndpoint endpoint;

    @Test
    public void shouldReturn3Bills() {
        BillResponse response = endpoint.getBills(Pageable.unpaged()).getBody();

        assertThat(response.getBills().size(), is(3));
    }

    @Test
    public void shouldReturnOrderWithId4() {
        final long billId = 2;
        BillResponse response = endpoint.getBillById(billId).getBody();

        assertThat(response.getBills().size(), is(1));
        assertThat(response.getBills().get(0).getId(), is(billId));
        assertThat(response.getBills().get(0).getOrders().size(), is(2));
        assertThat(response.getBills().get(0).getTotalPrice().intValue(), is(260));
    }

    @Test
    public void shouldCreateNewBill() {
        Order order1 = Order.builder()
                .id(1)
                .build();
        Order order2 = Order.builder()
                .id(2)
                .build();
        Bill bill = Bill.builder()
                .orders(Arrays.asList(order1, order2))
                .build();

        BillRequest request = BillRequest.builder()
                .bill(bill)
                .build();

        ResponseEntity<BillResponse> response = endpoint.createBill(request);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        BillResponse newBill = endpoint.getBillById(response.getBody().getBills().get(0).getId()).getBody();
        assertThat(newBill.getBills().size(), is(1));
        assertThat(newBill.getBills().get(0).getOrders().size(), is(2));
    }
}
