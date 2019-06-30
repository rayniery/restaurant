package rayniery.restaurant.service;

import org.springframework.data.domain.Pageable;
import rayniery.restaurant.api.model.BillRequest;
import rayniery.restaurant.api.model.BillResponse;
import rayniery.restaurant.api.model.OrderRequest;
import rayniery.restaurant.api.model.OrderResponse;

public interface BillService {
    BillResponse getBills(Pageable page);
    BillResponse getBillById(long id);
    BillResponse createBill(BillRequest request);
    BillResponse updateBill(long id, BillRequest request);
}
