package pl.kedrabartosz.HomeBudget.version2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kedrabartosz.HomeBudget.version2.entities.CategoryEntity;
import pl.kedrabartosz.HomeBudget.version2.entities.ItemEntity;
import pl.kedrabartosz.HomeBudget.version2.repositories.ItemRepository;


@Service
public class ItemService {
    private ItemRepository itemRepository;
    private QuantityService quantityService;


    public ItemService(@Autowired ItemRepository itemRepository, @Autowired QuantityService quantityService) {
        // = to przypisanie = to jest włąśnie wstrzykiwanie zależności
        this.itemRepository = itemRepository;
        this.quantityService = quantityService;
    }

    public ItemEntity saveItem(String name, int quantityId, CategoryEntity category) {
        ItemEntity itemEntity = ItemEntity.builder()
                .categoryEntity(category)
                .quantityEntity(quantityService.getQuantity(quantityId))
                .name(name)
                .build();
        return itemRepository.save(itemEntity);
    }

    public ItemEntity getItem(int itemId) {
        Optional<ItemEntity> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isEmpty()) {
            System.out.println("Could not get item with product" + itemId);
            throw new IllegalArgumentException("Could not get item");
        }
        return itemOptional.get();
    }

    public List<ItemEntity> getAllItems() {
        return itemRepository.findAll();
    }

    public ItemEntity updateItem(int itemId, String newName, int newQuantityId, CategoryEntity newCategory) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    item.setName(newName);
                    item.setQuantityEntity(quantityService.getQuantity(newQuantityId));
                    item.setCategoryEntity(newCategory);
                    return itemRepository.save(item);
                })
                .orElseThrow(() -> {
                    System.out.println("Could not update item with ID: " + itemId);
                    return new IllegalArgumentException("Item not found");
                });
    }

    public void deleteItem(int itemId) {
        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> {
                    System.out.println("Could not delete item with ID: " + itemId);
                    return new IllegalArgumentException("Item not found");
                });

        itemRepository.delete(item);
    }
}
