package pl.kedrabartosz.HomeBudget.version1.repository;

import pl.kedrabartosz.HomeBudget.version1.Category;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileBasedCategoryRepository implements CategoryRepository {

    public FileBasedCategoryRepository(String CSV_PATH) {
        this.CSV_PATH = CSV_PATH;
    }

    private String CSV_PATH = "src/main/resources/categories.csv";

    @Override
    public Category save(String name) {
        List<Category> categories = getAll();

        int maxId = categories.stream()
                .mapToInt(Category::getId)
                .max()
                .orElse(0);

        int newId = maxId + 1;
        Category newCategory = new Category(newId, name);
        categories.add(newCategory);

        BufferedWriter writer = null;
        try {
            Path path = Paths.get(CSV_PATH);

            writer = Files.newBufferedWriter(path);
            for (Category category : categories) {
                writer.write(category.getId() + "," + category.getName());
                writer.newLine();
            }
            writer.flush();

        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    System.err.println("Error closing writer: " + ex.getMessage());
                }
            }
        }

        return newCategory;
    }

    @Override
    public Optional<Category> update(String oldName, String newName) {
        List<Category> categories = getAll();
        boolean updated = false;
        Category updatedCategory = null;

        for (Category category : categories) {
            if (category.getName().equalsIgnoreCase(oldName)) {
                updatedCategory = new Category(category.getId(), newName);
                categories.set(categories.indexOf(category), updatedCategory);
                updated = true;
                break;
            }
        }

        if (!updated) {
            return Optional.empty();
        }

        BufferedWriter writer = null;
        try {
            Path path = Paths.get(
                    getClass().getClassLoader()
                            .getResource(CSV_PATH)
                            .toURI()
            );

            writer = Files.newBufferedWriter(path);
            for (Category category : categories) {
                writer.write(category.getId() + "," + category.getName());
                writer.newLine();
            }

        } catch (IOException | URISyntaxException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
            return Optional.empty();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    System.err.println("Error closing writer: " + ex.getMessage());
                }
            }
        }

        return Optional.of(updatedCategory);
    }

    @Override
    public Optional<Category> getCategory(String name) {
        List<Category> categories = getAll();
        return categories.stream()
                .filter(category -> category.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public Optional<Category> deleteCategory(String name) {
        List<Category> categories = getAll();
        Optional<Category> categoryToDelete = categories.stream()
                .filter(category -> category.getName().equalsIgnoreCase(name))
                .findFirst();

        if (categoryToDelete.isEmpty()) {
            return Optional.empty();
        }

        categories.remove(categoryToDelete.get());

        BufferedWriter writer = null;
        try {
            Path path = Paths.get(
                    CSV_PATH
            );

            writer = Files.newBufferedWriter(path);
            for (Category category : categories) {
                writer.write(category.getId() + "," + category.getName());
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
            return Optional.empty();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    System.err.println("Error closing writer: " + ex.getMessage());
                }
            }
        }

        return categoryToDelete;
    }

    @Override
    public List<Category> getAll() {
        List<Category> category = new ArrayList<>();
        BufferedReader reader = null;

        try {
            Path path = Paths.get(CSV_PATH);


            reader = Files.newBufferedReader(path);


            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[0].trim());
                String name = tokens[1].trim();
                category.add(new Category(id, name));
            }

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    System.err.println("Error closing reader: " + ex.getMessage());
                }
            }
        }
        return category;
    }
}
