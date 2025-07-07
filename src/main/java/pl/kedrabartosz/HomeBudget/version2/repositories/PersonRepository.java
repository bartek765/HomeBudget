package pl.kedrabartosz.HomeBudget.version2.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kedrabartosz.HomeBudget.version2.entities.PersonEntity;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

  @Query
  // TODO fill in this method
  Optional<PersonEntity> getByFirstAndLastName(String firstName, String lastName);
}
