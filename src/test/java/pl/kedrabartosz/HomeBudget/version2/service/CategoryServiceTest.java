package pl.kedrabartosz.HomeBudget.version2.service;

import java.time.Instant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kedrabartosz.HomeBudget.version2.repositories.CategoryRepository;

import java.util.List;
import pl.kedrabartosz.HomeBudget.version2.entities.CategoryEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    public static final String ANY_CATEGORY_NAME = "anyCategoryName";

    @Mock
    private CategoryRepository myTestCategoryRepository;
    @Mock
    private CategoryEntity categoryEntity1;
    @Mock
    private CategoryEntity categoryEntity2;
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryService(myTestCategoryRepository);
        when(categoryEntity1.getName()).thenReturn(ANY_CATEGORY_NAME);
    }

    @Test
    public void shouldGetCategory() {
        //given
        when(myTestCategoryRepository.getByName(eq(ANY_CATEGORY_NAME))).thenReturn(categoryEntity1);

        //when
        CategoryEntity actual = categoryService.getCategory(ANY_CATEGORY_NAME);
        //then
        Assertions.assertEquals(ANY_CATEGORY_NAME, actual.getName());
    }

    @Test
    public void shouldThrowExceptionWhenCannotReturnCategory() {
        //given
        when(myTestCategoryRepository.getByName(eq(ANY_CATEGORY_NAME))).thenReturn(null);

      //when
        Assertions.assertThrows(IllegalArgumentException.class, () -> categoryService.getCategory(ANY_CATEGORY_NAME));
    }

    @Test
    public void shouldSaveCategory() {
        // given
        String name = "newCategory";
        Instant createdAt = Instant.now();
        Instant lastUpdatedAt = Instant.now();
        CategoryEntity expected = CategoryEntity.builder()
            .name(name)
            .createdAt(createdAt)
            .lastUpdatedAt(lastUpdatedAt)
            .build();
        when(myTestCategoryRepository.save(any())).thenReturn(expected);

        // when
        CategoryEntity actual = categoryService.saveCategory(name, createdAt, lastUpdatedAt);

        // then
        assertEquals(expected, actual);
        verify(myTestCategoryRepository).save(expected);
    }

    @Test
    public void shouldUpdateCategory() {
        // given
        String oldName = "Old";
        String newName = "New";
        when(myTestCategoryRepository.getByName(eq(oldName))).thenReturn(categoryEntity1);
        when(categoryEntity1.getName()).thenReturn(newName);

        // when
        CategoryEntity actual = categoryService.updateCategory(oldName, newName);

        // then
        assertEquals(newName, actual.getName());
        verify(categoryEntity1).setName(eq(newName));
    }

    @Test
    public void shouldThrowExceptionWhenNoSuchCategoryExists() {
        // given
        String oldName = "Old";
        String newName = "New";
        when(myTestCategoryRepository.getByName(eq(oldName))).thenReturn(null);
        when(categoryEntity1.getName()).thenReturn(newName);

        // when
        Assertions.assertThrows(IllegalArgumentException.class, () -> categoryService.updateCategory(oldName, newName));
    }

    @Test
    public void shouldDeleteCategory() {
        // given
        when(myTestCategoryRepository.getByName(ANY_CATEGORY_NAME)).thenReturn(categoryEntity1);

        // when
        CategoryEntity actual = categoryService.deleteCategory(ANY_CATEGORY_NAME);

        // then
        assertEquals(categoryEntity1, actual);
        verify(myTestCategoryRepository).delete(eq(categoryEntity1));
    }

    @Test
    public void shouldThrowExceptionWhenDeletingWhenNoSuchCategory() {
        // given
        when(myTestCategoryRepository.getByName(ANY_CATEGORY_NAME)).thenReturn(null);

        // when
        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> categoryService.deleteCategory(ANY_CATEGORY_NAME));

    }

    @Test
    public void shouldReturnAllCategories() {
        // given
        List<CategoryEntity> expected = List.of(categoryEntity1, categoryEntity2);
        when(myTestCategoryRepository.findAll()).thenReturn(expected);

        // when
        List<CategoryEntity> actual = categoryService.getAllCategories();

        // then
        assertEquals(expected, actual);
        verify(myTestCategoryRepository).findAll();
    }

}