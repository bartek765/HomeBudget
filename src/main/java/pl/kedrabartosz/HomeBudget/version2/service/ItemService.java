package pl.kedrabartosz.HomeBudget.version2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kedrabartosz.HomeBudget.version1.Category;
import pl.kedrabartosz.HomeBudget.version1.Item;
import pl.kedrabartosz.HomeBudget.version1.repository.ItemRepository;

@Service
public class ItemService {

    private ItemRepository listBasedRepository;

    public ItemService(@Autowired ItemRepository listBasedRepository) {
        // = to przypisanie = to jest włąśnie wstrzykiwanie zależności
        this.listBasedRepository = listBasedRepository;
    }

    public Item saveItem(String name, double price, Category category) {
        return listBasedRepository.addItem(name, price, category);
    }

    public Item updateItem(String oldProduct, String newProduct, double newPrice) {
        Optional<Item> updateItemOptional = listBasedRepository.updateItem(oldProduct, newProduct, newPrice);
        if (updateItemOptional.isEmpty()) {
            System.out.println("Could not update item with oldProduct" + oldProduct);
            throw new IllegalArgumentException("Could not update item");
        }
        return updateItemOptional.get();
    }

    public boolean doesItemExits(String product) {
        Optional<Item> itemOptional = listBasedRepository.getItemByName(product);
        if (itemOptional.isPresent()) {
            return true;
        }
        return false;
    }

    public Item getItem(String product) {
        Optional<Item> itemOptional = listBasedRepository.getItemByName(product);
        if (itemOptional.isEmpty()) {
            System.out.println("Could not get item with product" + product);
            throw new IllegalArgumentException("Could not get item");
        }
        return itemOptional.get();
    }

    public Item deleteItem(String itemName) {
        Optional<Item> deletedItemOptional = listBasedRepository.deleteItem(itemName);
        if (deletedItemOptional.isEmpty()) {
            System.out.println("Could not delete item with name " + itemName);
            throw new IllegalArgumentException("Could not delete item");
        }
        return deletedItemOptional.get();
    }

    public List<Item> getAllItems() {
        return listBasedRepository.getAll();
    }
}
