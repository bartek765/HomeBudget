package pl.kedrabartosz.HomeBudget.version2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.kedrabartosz.HomeBudget.version2.entities.PersonEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

    @Query(nativeQuery = true, value = """
                SELECT *
                FROM PERSON
                WHERE first_name = :firstName
                  AND last_name = :lastName
            """)
    Optional<PersonEntity> getByFirstAndLastName(
            @Param(value = "firstName") String firstName,
            @Param(value = "lastName") String lastName
    );
}