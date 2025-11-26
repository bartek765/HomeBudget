package pl.kedrabartosz.HomeBudget.version2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.kedrabartosz.HomeBudget.version2.entities.QuantityEntity;
import pl.kedrabartosz.HomeBudget.version2.service.QuantityService;

import java.util.List;

@RestController
@RequestMapping("/api/quantity")
public class QuantityController {

    private final QuantityService quantityService;

    public QuantityController(@Autowired QuantityService quantityService) {
        this.quantityService = quantityService;
    }

    @GetMapping
    public List<QuantityEntity> getAllQuantities() {
        return quantityService.getAllQuantities();
    }

    @GetMapping("/{id}")
    public QuantityEntity getQuantityById(@PathVariable int id) {
        return quantityService.getQuantity(id);
    }

    @GetMapping("/{id}/exists")
    public boolean doesQuantityExist(@PathVariable int id) {
        return quantityService.doesQuantityExist(id);
    }
}