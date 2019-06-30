package rayniery.restaurant.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu_bill", schema = "restaurant")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @OneToMany(mappedBy = "bill")
    List<OrderEntity> orders;
}
