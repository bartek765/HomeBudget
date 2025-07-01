package pl.kedrabartosz.HomeBudget.version2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.kedrabartosz.HomeBudget.version2.entities.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    @Query(nativeQuery = true, value = """
            SELECT name
            FROM CATEGORY
            WHERE name = :oldName
            """)

    public CategoryEntity getByName(@Param(value = "oldName") String oldName);
}
