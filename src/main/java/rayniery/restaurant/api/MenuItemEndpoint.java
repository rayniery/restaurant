package rayniery.restaurant.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rayniery.restaurant.api.model.MenuItemRequest;
import rayniery.restaurant.api.model.MenuItemResponse;
import rayniery.restaurant.service.MenuItemService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(
        value = "/menu/item",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuItemEndpoint {
    private final MenuItemService service;

    @Autowired
    public MenuItemEndpoint(MenuItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<MenuItemResponse>
    getMenuItems(Pageable page,
                 @RequestParam(value = "search", required = false) String searchQuery) {
        return ResponseEntity.ok(service.getMenuItems(page, searchQuery));
    }

    @GetMapping("{itemId}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable("itemId") long itemId) {
        return ResponseEntity.ok(service.getMenuItemById(itemId));
    }

    @PostMapping
    public ResponseEntity<MenuItemResponse> createMenuItem(@RequestBody @Valid MenuItemRequest request) {
        MenuItemResponse response = service.createMenuItem(request);
        return createdResourceResponse(response);
    }

    @PutMapping("{itemId}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@RequestBody @Valid MenuItemRequest updatedMenuItem,
                                                           @PathVariable("itemId") long itemId) {
        MenuItemResponse response = service.updateMenuItem(itemId, updatedMenuItem);
        return createdResourceResponse(response);
    }

    @DeleteMapping("{itemId}")
    public ResponseEntity<MenuItemResponse> deleteMenuItem(@PathVariable("itemId") long itemId) {
        service.deleteMenuItem(itemId);
        return ResponseEntity.noContent()
                .build();
    }

    private static ResponseEntity<MenuItemResponse> createdResourceResponse(MenuItemResponse response) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getMenuItems().get(0).getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(MenuItemResponse.builder()
                        .menuItems(response.getMenuItems())
                        .build()
                );
    }
}
