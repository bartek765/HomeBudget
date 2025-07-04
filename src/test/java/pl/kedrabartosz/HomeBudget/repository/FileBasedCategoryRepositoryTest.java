package pl.kedrabartosz.HomeBudget.repository;

import org.junit.jupiter.api.Test;
import pl.kedrabartosz.HomeBudget.version1.Category;
import pl.kedrabartosz.HomeBudget.version1.repository.CategoryRepository;
import pl.kedrabartosz.HomeBudget.version1.repository.FileBasedCategoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class FileBasedCategoryRepositoryTest {
    private CategoryRepository fileBasedCategoryRepository =
            new FileBasedCategoryRepository("src/test/java/pl/kedrabartosz/HomeBudget/test.csv");

    @Test
    public void shouldGetAll() {
                // given
        fileBasedCategoryRepository.save("asder");
        fileBasedCategoryRepository.save("tomek");
        fileBasedCategoryRepository.save("bolek");
        fileBasedCategoryRepository.save("atomek");
        List<Category> expected = List.of(
                new Category(1, "asder"),
                new Category(2, "tomek"),
                new Category(3, "bolek"),
                new Category(4, "atomek")
        );

        // when
        List<Category> actual = fileBasedCategoryRepository.getAll();

        // then
        assertIterableEquals(expected, actual);
        fileBasedCategoryRepository.deleteCategory("asder");
        fileBasedCategoryRepository.deleteCategory("tomek");
        fileBasedCategoryRepository.deleteCategory("bolek");
        fileBasedCategoryRepository.deleteCategory("atomek");
    }

}
