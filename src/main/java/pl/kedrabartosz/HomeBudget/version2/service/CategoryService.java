package pl.kedrabartosz.HomeBudget.version2.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kedrabartosz.HomeBudget.version2.entities.CategoryEntity;
import pl.kedrabartosz.HomeBudget.version2.repositories.CategoryRepository;

@Service
//service sie dodaje dla springa! nie jest to koniecznie bez tego tez by smigalo :D!
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(@Autowired CategoryRepository categoryRepository) {
        // tu nastepuje wstrzykneicie zaleznosci!
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity saveCategory(String name, Instant createdAt, Instant lastUpdatedAt) {
        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        category.setCreatedAt(createdAt);
        category.setLastUpdatedAt(lastUpdatedAt);
        return categoryRepository.save(category);
    }

    public CategoryEntity updateCategory(String oldName, String newName) {
        CategoryEntity posibleCategory = categoryRepository.getByName(oldName);
        if (posibleCategory != null) {
            posibleCategory.setName(newName);
            return posibleCategory;
        } else {
            System.out.println("Could not update this Category because doesn't exists" + oldName);
            throw new IllegalArgumentException("Could not update this Category");
        }
    }

    public CategoryEntity getCategory(String name) {
        CategoryEntity category = categoryRepository.getByName(name);
        if (category != null) {
            return category;
        } else {
            System.out.println("Could not get this Category because doesn't exists" + name);
            throw new IllegalArgumentException("Category not found: " + name);
        }
    }

    public CategoryEntity deleteCategory(String name) {
        CategoryEntity toDelete = categoryRepository.getByName(name);
        if (toDelete != null) {
            categoryRepository.delete(toDelete);
            return toDelete;
        } else {
            System.out.println("Could not delete this Category because doesn't exists" + name);
            throw new IllegalArgumentException("Category not found: " + name);
        }
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }
}
