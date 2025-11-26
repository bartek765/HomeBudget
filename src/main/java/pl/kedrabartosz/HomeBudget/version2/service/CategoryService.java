package pl.kedrabartosz.HomeBudget.version2.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kedrabartosz.HomeBudget.version2.entities.CategoryEntity;
import pl.kedrabartosz.HomeBudget.version2.repositories.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(@Autowired CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public CategoryEntity saveCategory(String name, Instant createdAt, Instant lastUpdatedAt) {
        if (categoryRepository.getByName(name) != null) {
            System.out.println("Category with name '" + name + "' already exists.");
            throw new IllegalArgumentException("Category already exists");
        }

        CategoryEntity category = CategoryEntity.builder()
                .name(name)
                .createdAt(createdAt)
                .lastUpdatedAt(lastUpdatedAt)
                .build();

        return categoryRepository.save(category);
    }


    public CategoryEntity saveCategory(String name) {
        Instant now = Instant.now();
        return saveCategory(name, now, now);
    }


    public CategoryEntity updateCategory(String oldName, String newName) {
        return Optional.ofNullable(categoryRepository.getByName(oldName))
                .map(categoryEntity -> {
                    categoryEntity.setName(newName);
                    categoryEntity.setLastUpdatedAt(Instant.now());
                    return categoryRepository.save(categoryEntity);
                })
                .orElseThrow(() -> {
                    System.out.println("Could not update this Category because it doesn't exist: " + oldName);
                    return new IllegalArgumentException("Could not update this Category");
                });
    }


    public CategoryEntity getCategory(String name) {
        return Optional.ofNullable(categoryRepository.getByName(name))
                .orElseThrow(() -> {
                    System.out.println("Could not get this Category because it doesn't exist: " + name);
                    return new IllegalArgumentException("Category not found: " + name);
                });
    }


    public CategoryEntity getCategory(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    System.out.println("Could not get this Category because it doesn't exist with id: " + id);
                    return new IllegalArgumentException("Category not found with id: " + id);
                });
    }

    public CategoryEntity deleteCategory(String name) {
        CategoryEntity categoryToDelete = Optional.ofNullable(categoryRepository.getByName(name))
                .orElseThrow(() -> {
                    System.out.println("Could not delete this Category because it doesn't exist: " + name);
                    return new IllegalArgumentException("Category not found: " + name);
                });

        categoryRepository.delete(categoryToDelete);
        return categoryToDelete;
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }
}