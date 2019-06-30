package rayniery.restaurant.persistance.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rayniery.restaurant.persistance.entity.MenuItemEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> findByNameContainingIgnoreCase(Pageable page, String searchQuery);
    List<MenuItemEntity> findByDescriptionContainingIgnoreCase(Pageable page, String searchQuery);
    List<MenuItemEntity> findByAdditionalDetailsContainingIgnoreCase(Pageable page, String searchQuery);

    default List<MenuItemEntity> findBySearchQuery(Pageable page, String searchQuery) {
        Set<MenuItemEntity> results = new HashSet<>(findByNameContainingIgnoreCase(page, searchQuery));
        results.addAll(findByDescriptionContainingIgnoreCase(page, searchQuery));
        results.addAll(findByAdditionalDetailsContainingIgnoreCase(page, searchQuery));

        return new ArrayList<>(results);
    }
}
