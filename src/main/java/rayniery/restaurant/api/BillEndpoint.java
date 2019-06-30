package rayniery.restaurant.api;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rayniery.restaurant.api.model.*;
import rayniery.restaurant.service.BillService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(
        value = "/menu/bill",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class BillEndpoint {
    private final BillService service;

    public BillEndpoint(BillService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<BillResponse> getBills(Pageable page) {
        return ResponseEntity.ok(service.getBills(page));
    }

    @GetMapping("{billId}")
    public ResponseEntity<BillResponse> getBillById(@PathVariable("billId") long id) {
        return ResponseEntity.ok(service.getBillById(id));
    }

    @PostMapping
    public ResponseEntity<BillResponse> createBill(@RequestBody @Valid BillRequest request) {
        BillResponse response = service.createBill(request);
        return createdResourceResponse(response);
    }

    @PutMapping("{billId}")
    public ResponseEntity<BillResponse> updateBill(@RequestBody @Valid BillRequest request,
                                                   @PathVariable("billId") long id) {
        BillResponse response = service.updateBill(id, request);
        return createdResourceResponse(response);
    }

    private static ResponseEntity<BillResponse> createdResourceResponse(BillResponse response) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getBills().get(0).getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(BillResponse.builder()
                        .bills(response.getBills())
                        .build()
                );
    }
}
