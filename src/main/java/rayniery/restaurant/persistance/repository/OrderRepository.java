package rayniery.restaurant.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rayniery.restaurant.persistance.entity.BillEntity;
import rayniery.restaurant.persistance.entity.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByBill(BillEntity bill);
}
