package pl.kedrabartosz.HomeBudget.version2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "cost")
@Entity
public class CostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(
            name = "price",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal price;

    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private ItemEntity itemEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CostEntity that = (CostEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(price, that.price)
                && Objects.equals(effectiveDate, that.effectiveDate)
                && Objects.equals(itemEntity, that.itemEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, effectiveDate, itemEntity);
    }

    @Override
    public String toString() {
        return "CostEntity{" +
                "id=" + id +
                ", price=" + price +
                ", effectiveDate=" + effectiveDate +
                ", itemEntity=" + itemEntity +
                '}';
    }
}