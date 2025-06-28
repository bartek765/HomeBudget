package pl.kedrabartosz.HomeBudget.version2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "items_in_receipt")
@Entity
public class ItemsInReceiptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;
    @Column(name = "quantity")
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemEntity itemEntity;
    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private ReceiptEntity receiptEntity;
}
