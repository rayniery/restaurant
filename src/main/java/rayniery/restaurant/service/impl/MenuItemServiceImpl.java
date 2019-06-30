package rayniery.restaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rayniery.restaurant.api.model.MenuItem;
import rayniery.restaurant.api.model.MenuItemRequest;
import rayniery.restaurant.api.model.MenuItemResponse;
import rayniery.restaurant.service.MenuItemService;
import rayniery.restaurant.persistance.entity.MenuItemEntity;
import rayniery.restaurant.persistance.repository.MenuItemRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repository;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public MenuItemResponse getMenuItems(Pageable page, String searchQuery) {
        List<MenuItem> menuItems;
        if (searchQuery == null || searchQuery.isEmpty()) {
            menuItems = repository.findAll(page)
                    .stream()
                    .map(MenuItem::fromEntity)
                    .collect(Collectors.toList());
        } else {
            menuItems = repository
                    .findBySearchQuery(page, searchQuery)
                    .stream()
                    .map(MenuItem::fromEntity)
                    .collect(Collectors.toList());
        }

        return MenuItemResponse.builder()
                .menuItems(menuItems)
                .build();
    }

    @Override
    public MenuItemResponse getMenuItemById(long itemId) {
        Optional<MenuItemEntity> menuItemEntity = repository.findById(itemId);
        if (menuItemEntity.isPresent()) {
            MenuItem item = MenuItem.fromEntity(menuItemEntity.get());
            return MenuItemResponse.builder()
                    .menuItems(Arrays.asList(item))
                    .build();
        } else {
            return MenuItemResponse.builder()
                    .menuItems(Collections.emptyList())
                    .build();
        }
    }

    @Override
    public MenuItemResponse createMenuItem(MenuItemRequest request) {
        MenuItemEntity requestEntity = request.getMenuItem().toEntity();
        MenuItemEntity savedEntity = repository.save(requestEntity);

        return MenuItemResponse.builder()
                .menuItems(Arrays.asList(MenuItem.fromEntity(savedEntity)))
                .build();
    }

    @Override
    public MenuItemResponse updateMenuItem(long id, MenuItemRequest request) {
        MenuItem newItem = request.getMenuItem();
        MenuItemEntity updatedItem = repository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setAdditionalDetails(newItem.getAdditionalDetails());
                    item.setDescription(newItem.getDescription());
                    item.setImage(newItem.getImage());
                    item.setPrice(newItem.getPrice());

                    return repository.save(item);
                })
                .orElseGet(() -> repository.save(newItem.toEntity()));

        return MenuItemResponse.builder()
                .menuItems(Arrays.asList(MenuItem.fromEntity(updatedItem)))
                .build();
    }

    @Override
    public void deleteMenuItem(long id) {
        repository.deleteById(id);
    }
}
