package rayniery.restaurant.service;

import org.springframework.data.domain.Pageable;
import rayniery.restaurant.api.model.MenuItemRequest;
import rayniery.restaurant.api.model.MenuItemResponse;

public interface MenuItemService {
    MenuItemResponse getMenuItems(Pageable page, String searchQuery);
    MenuItemResponse getMenuItemById(long itemId);
    MenuItemResponse createMenuItem(MenuItemRequest request);
    MenuItemResponse updateMenuItem(long id, MenuItemRequest request);
    void deleteMenuItem(long id);
}
