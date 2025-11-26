package pl.kedrabartosz.HomeBudget.version2.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kedrabartosz.HomeBudget.version2.entities.ItemEntity;
import pl.kedrabartosz.HomeBudget.version2.entities.CategoryEntity;
import pl.kedrabartosz.HomeBudget.version2.service.ItemService;
import pl.kedrabartosz.HomeBudget.version2.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @GetMapping
    public List<ItemEntity> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ItemEntity getItem(@PathVariable int id) {
        return itemService.getItem(id);
    }

    @PostMapping
    public ItemEntity createItem(@RequestParam String name,
                                 @RequestParam int quantityId,
                                 @RequestParam int categoryId) {

        CategoryEntity category = categoryService.getCategory(categoryId);
        return itemService.saveItem(name, quantityId, category);
    }

    @PutMapping("/{id}")
    public ItemEntity updateItem(@PathVariable int id,
                                 @RequestParam String name,
                                 @RequestParam int quantityId,
                                 @RequestParam int categoryId) {

        CategoryEntity category = categoryService.getCategory(categoryId);
        return itemService.updateItem(id, name, quantityId, category);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable int id) {
        itemService.deleteItem(id);
    }
}