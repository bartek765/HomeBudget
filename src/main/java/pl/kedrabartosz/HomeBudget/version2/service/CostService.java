package pl.kedrabartosz.HomeBudget.version2.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kedrabartosz.HomeBudget.version2.entities.CostEntity;
import pl.kedrabartosz.HomeBudget.version2.entities.ItemEntity;
import pl.kedrabartosz.HomeBudget.version2.repositories.CostRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CostService {
    private CostRepository costRepository;
    private ItemService itemService;

    public CostEntity saveNewCost(double price, Instant effectiveDate, int itemId) {
        CostEntity cost = CostEntity.builder()
                .price(price)
                .effectiveDate(effectiveDate)
                .itemEntity(itemService.getItem(itemId))
                .build();
        return costRepository.save(cost);
    }

    public CostEntity findById(int costId) {
        return Optional.of(costRepository.getReferenceById(costId))
                .orElseThrow(() -> {
                    System.out.println("Could not find cost with ID: " + costId);
                    return new IllegalArgumentException("Cost not found");
                });
    }

    public List<CostEntity> getAllCost() {
        return costRepository.findAll();
    }

    public CostEntity updateCost(int costId, double newPrice, Instant newEffectiveDate, int newItemId) {
        return Optional.of(costRepository.getReferenceById(costId))
                .map(cost -> {
                  CostEntity toBeUpdated = cost.toBuilder()
                      .price(newPrice)
                      .effectiveDate(newEffectiveDate)
                      .build();
                  return costRepository.save(toBeUpdated);
                })
                .orElseThrow(() -> {
                    System.out.println("Could not update cost with ID: " + costId);
                    return new IllegalArgumentException("Cost not found");
                });
    }

    public CostEntity deleteCost(int costId) {
        CostEntity costToDelete = Optional.of(costRepository.getReferenceById(costId))
                .orElseThrow(() -> {
                    System.out.println("Could not delete cost with ID: " + costId);
                    return new IllegalArgumentException("Cost not found");
                });

        costRepository.delete(costToDelete);
        return costToDelete;
    }
}
