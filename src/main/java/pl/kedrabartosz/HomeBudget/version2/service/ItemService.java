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


    public ItemService(@Autowired ItemRepository itemRepository) {
        // = to przypisanie = to jest włąśnie wstrzykiwanie zależności
        this.itemRepository = itemRepository;
    }

    public ItemEntity saveItem(String name, int quantityId, CategoryEntity category) {
        ItemEntity itemEntity = ItemEntity.builder()
                .categoryEntity(category)
                .quantityEntity(null) //TODO
                .name(name)
                .build();
        return itemRepository.save(itemEntity);
    }


    public boolean doesItemExits(int itemId) {
        Optional<ItemEntity> itemOptional = itemRepository.findById(itemId);
        if (itemOptional.isPresent()) {
            return true;
        }
        return false;
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
}
