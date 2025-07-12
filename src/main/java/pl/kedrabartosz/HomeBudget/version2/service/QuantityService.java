package pl.kedrabartosz.HomeBudget.version2.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kedrabartosz.HomeBudget.version2.entities.QuantityEntity;
import pl.kedrabartosz.HomeBudget.version2.repositories.QuantityRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class QuantityService {

    private final @Autowired QuantityRepository quantityRepository;

    public QuantityEntity getQuantity(int quantityId) {
        return Optional.of(quantityRepository.getReferenceById(quantityId))
                .orElseThrow(() -> {
                    System.out.println("Could not find quantity with ID: " + quantityId);
                    return new IllegalArgumentException("Quantity not found");
                });
    }

    public List<QuantityEntity> getAllQuantities() {
        return quantityRepository.findAll();
    }

    public boolean doesQuantityExist(int id) {
        return quantityRepository.existsById(id);
    }
}