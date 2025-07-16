package pl.kedrabartosz.HomeBudget.version2.entities;

import jakarta.persistence.*;
import java.util.Objects;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ItemEntity that = (ItemEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(
            categoryEntity,
            that.categoryEntity
        ) && Objects.equals(quantityEntity, that.quantityEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, categoryEntity, quantityEntity);
    }
}
