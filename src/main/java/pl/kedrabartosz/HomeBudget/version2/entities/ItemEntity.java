package pl.kedrabartosz.HomeBudget.version2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "item")
@Entity
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @OneToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;
    @OneToOne
    @JoinColumn(name = "quantity_id")
    private QuantityEntity quantityEntity;
}
