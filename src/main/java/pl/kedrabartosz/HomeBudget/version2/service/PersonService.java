package pl.kedrabartosz.HomeBudget.version2.service;

import java.time.Instant;
import java.util.Optional;
import lombok.AllArgsConstructor;
import pl.kedrabartosz.HomeBudget.version2.entities.PersonEntity;
import pl.kedrabartosz.HomeBudget.version2.repositories.PersonRepository;

@AllArgsConstructor
public class PersonService {
  private PersonRepository personRepository;

  public boolean personExists(int personId) {
    // TODO fill in this method
  }

  public PersonEntity saveNewPerson(String personName, String personLastName, Instant joinedAt) {
    // TODO fill in this method
  }

  public PersonEntity getById(int personId) {
    // TODO fill in this method
  }

  public Optional<PersonEntity> getByFirstAndLastName(String personName, String personLastName) {
    // TODO fill in this method
  }
}
