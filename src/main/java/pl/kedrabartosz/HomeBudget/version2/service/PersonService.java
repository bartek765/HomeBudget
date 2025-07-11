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
        return personRepository.existsById(personId);
    }

    public PersonEntity saveNewPerson(String personName, String personLastName, Instant joinedAt) {
        if (personName == null || personName.isBlank() ||
                personLastName == null || personLastName.isBlank()) {
            throw new IllegalArgumentException("First name and last name must not be empty");
        }

        PersonEntity person = PersonEntity.builder()
                .firstName(personName)
                .lastName(personLastName)
                .joinedAt(joinedAt)
                .build();

        return personRepository.save(person);
    }

    public PersonEntity getById(int personId) {
        return Optional.of(personRepository.getReferenceById(personId))
                .orElseThrow(() -> {
                    System.out.println("Could not find person with ID: " + personId);
                    return new IllegalArgumentException("Person not found");
                });
    }

    public Optional<PersonEntity> getByFirstAndLastName(String personName, String personLastName) {
        if (personName == null || personName.isBlank() || personLastName == null || personLastName.isBlank()) {
            System.out.println("Could not find person – first name or last name is null or blank");
            throw new IllegalArgumentException("First name and last name must not be null or blank");
        }

        return personRepository.getByFirstAndLastName(personName, personLastName);
    }
}
