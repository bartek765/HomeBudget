package pl.kedrabartosz.HomeBudget.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kedrabartosz.HomeBudget.version1.Category;
import pl.kedrabartosz.HomeBudget.version1.repository.CategoryRepository;
import pl.kedrabartosz.HomeBudget.version2.service.CategoryService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.kedrabartosz.HomeBudget.service.MyTestCategoryRepository.CATEGORY_NAME;

class CategoryServiceTest {

    private static final int DEFAULT_ID = 1;

    @Mock
    private CategoryRepository myTestCategoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryService(myTestCategoryRepository);
    }

    @Test
    public void shouldGetCategory() {
        //given
        Category category = new Category(DEFAULT_ID, CATEGORY_NAME);
        Optional<Category> getCategoryOptional = Optional.of(category);
        when(myTestCategoryRepository.getCategory(eq("Bartek"))).thenReturn(getCategoryOptional);
        String name = "Bartek";

        //when
        Category actual = categoryService.getCategory(name);
        //then
        Assertions.assertEquals(CATEGORY_NAME, actual.getName());
    }

    @Test
    public void shouldSaveCategory() {
        // given
        String name = "Jerzy";
        Category category = new Category(DEFAULT_ID, name);
        when(myTestCategoryRepository.save(eq(name))).thenReturn(category);

        // when
        Category actual = categoryService.saveCategory(name);

        // then
        assertEquals(name, actual.getName());
    }

    @Test
    public void shouldUpdateCategory() {
        // given
        String oldName = "Old";
        String newName = "New";
        Category category = new Category(DEFAULT_ID, newName);
        when(myTestCategoryRepository.update(eq(oldName), eq(newName))).thenReturn(Optional.of(category));

        // when
        Category actual = categoryService.updateCategory(oldName, newName);

        // then
        assertEquals(newName, actual.getName());
    }

    @Test
    public void shouldDeleteCategory() {
        // given
        String name = "Entertainment";
        Category category = new Category(DEFAULT_ID, name);
        when(myTestCategoryRepository.deleteCategory(eq(name))).thenReturn(Optional.of(category));

        // when
        Category actual = categoryService.deleteCategory(name);

        // then
        assertEquals(name, actual.getName());
    }

    @Test
    public void shouldReturnAllCategories() {
        // given
        List<Category> expected = List.of(
                new Category(1, "A"),
                new Category(2, "B")
        );
        when(myTestCategoryRepository.getAll()).thenReturn(expected);

        // when
        List<Category> actual = categoryService.getAllCategories();

        // then
        assertEquals(expected, actual);
        verify(myTestCategoryRepository).getAll();
    }

}