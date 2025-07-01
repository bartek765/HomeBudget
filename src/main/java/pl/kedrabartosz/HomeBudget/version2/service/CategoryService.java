package pl.kedrabartosz.HomeBudget.version2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kedrabartosz.HomeBudget.version2.entities.CategoryEntity;
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

    public CategoryEntity saveCategory(String name) {
        return categoryRepository.save(name);
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
        Optional<CategoryEntity> categoryOptional = categoryRepository.getCategory(name);
        if (categoryOptional.isEmpty()) {
            System.out.println("Could not get category with name " + name);
            throw new IllegalArgumentException("Could not get category");
        }
        return categoryOptional.get();
    }

    public CategoryEntity deleteCategory(String name) {
        Optional<CategoryEntity> deletedCategoryOptional = categoryRepository.deleteCategory(name);
        if (deletedCategoryOptional.isEmpty()) {
            System.out.println("Could not delete category with name " + name);
            throw new IllegalArgumentException("Could not delete category");
        }
        return deletedCategoryOptional.get();
    }

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.getAll();
    }
}
