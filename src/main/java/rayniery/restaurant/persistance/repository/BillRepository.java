package rayniery.restaurant.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rayniery.restaurant.persistance.entity.BillEntity;

public interface BillRepository extends JpaRepository<BillEntity, Long> {
}
