package pl.kedrabartosz.HomeBudget.version2.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pl.kedrabartosz.HomeBudget.version2.entities.CostEntity;
import pl.kedrabartosz.HomeBudget.version2.service.CostService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cost")
@RequiredArgsConstructor
public class CostController {

    private final CostService costService;


    @PostMapping("/create")
    public CostEntity createCost(@RequestParam BigDecimal price,
                                 @RequestParam
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                 LocalDate effectiveDate,
                                 @RequestParam int itemId) {
        return costService.saveNewCost(price, effectiveDate, itemId);
    }


    @GetMapping
    public List<CostEntity> getAllCosts() {
        return costService.getAllCost();
    }


    @GetMapping("/by-id")
    public CostEntity getCostById(@RequestParam int costId) {
        return costService.findById(costId);
    }


    @PutMapping("/update")
    public CostEntity updateCost(@RequestParam int costId,
                                 @RequestParam BigDecimal newPrice,
                                 @RequestParam
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                 LocalDate newEffectiveDate,
                                 @RequestParam int newItemId) {
        return costService.updateCost(costId, newPrice, newEffectiveDate, newItemId);
    }

    @DeleteMapping("/delete")
    public CostEntity deleteCost(@RequestParam int costId) {
        return costService.deleteCost(costId);
    }
}