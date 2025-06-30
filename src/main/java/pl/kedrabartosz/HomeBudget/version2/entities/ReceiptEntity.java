package pl.kedrabartosz.HomeBudget.version2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "receipt")
@Entity
public class ReceiptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "purchased_at")
    private Instant purchasedAt;
    @Column(name = "total_cost")
    private double totalCost;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity personEntity;
}
