package pl.kedrabartosz.HomeBudget.version2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cost")
@Entity
public class CostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "price")
    private double price;
    @Column(name = "effective_date")
    private Instant effectiveDate;
    @ManyToOne
    @JoinColumn(name = "item_id") // to jest fk
    private ItemEntity itemEntity;
}
