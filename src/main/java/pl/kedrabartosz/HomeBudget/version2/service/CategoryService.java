package pl.kedrabartosz.HomeBudget.version2.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import lombok.Builder;
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
        CategoryEntity category = CategoryEntity.builder()
                .name(name)
                .createdAt(createdAt)
                .lastUpdatedAt(lastUpdatedAt)
                .build();
        return categoryRepository.save(category);
    }

    public CategoryEntity updateCategory(String oldName, String newName) {
        return Optional.ofNullable(categoryRepository.getByName(oldName))
                .map(categoryEntity -> {
                    categoryEntity.setName(newName);
                    return categoryEntity;
                })
                .orElseThrow(() -> {
                    System.out.println("Could not update this Category because doesn't exists " + oldName);
                    return new IllegalArgumentException("Could not update this Category");
                });
    }

    public CategoryEntity getCategory(String name) {
        return Optional.ofNullable(categoryRepository.getByName(name))
                .orElseThrow(() -> {
                    System.out.println("Could not get this Category because doesn't exist: " + name);
                    return new IllegalArgumentException("Category not found: " + name);
                });
    }

    public CategoryEntity deleteCategory(String name) {
        CategoryEntity categoryToDelete = Optional.ofNullable(categoryRepository.getByName(name))
                .orElseThrow(() -> {
                    System.out.println("Could not delete this Category because doesn't exist: " + name);
                    return new IllegalArgumentException("Category not found: " + name);
                });
        categoryRepository.delete(categoryToDelete);
        return categoryToDelete;
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }
}
