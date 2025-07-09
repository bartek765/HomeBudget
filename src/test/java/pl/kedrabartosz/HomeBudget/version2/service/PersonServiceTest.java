package pl.kedrabartosz.HomeBudget.version2.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kedrabartosz.HomeBudget.version2.entities.PersonEntity;
import pl.kedrabartosz.HomeBudget.version2.repositories.PersonRepository;

class PersonServiceTest {

    public static final String ANY_PERSON_FIRST_NAME = "anyName";
    public static final int ANY_PERSON_ID = 1;
    private static final String ANY_PERSON_LAST_NAME = "anyLastName";
    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonEntity personEntity;
    private Instant joinedAt;

    private PersonService personService;

    public static Stream<Arguments> namesProvider() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of(null, ""),
                Arguments.of("", null),
                Arguments.of(null, null),
                Arguments.of("Nane", null),
                Arguments.of(null, "name")
        );
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personService = new PersonService(personRepository);
        joinedAt = Instant.now();
    }

    @Test
    public void shouldAddNewPerson() {
        // given
        var expected = PersonEntity.builder()
                .firstName(ANY_PERSON_FIRST_NAME)
                .lastName(ANY_PERSON_LAST_NAME)
                .joinedAt(joinedAt)
                .build();
        when(personRepository.save(eq(expected))).thenReturn(personEntity);

        //when
        PersonEntity newPerson = personService.saveNewPerson(ANY_PERSON_FIRST_NAME, ANY_PERSON_LAST_NAME, joinedAt);

        //then
        verify(personRepository).save(eq(expected));
        Assertions.assertEquals(personEntity, newPerson, "saved new person had different data than expected!");
    }


    @Test
    public void shouldReturnTrueIfPersonExists() {
        // given
        when(personRepository.existsById(eq(ANY_PERSON_ID))).thenReturn(true);

        //when
        boolean actual = personService.personExists(ANY_PERSON_ID);

        //then
        verify(personRepository).existsById(eq(ANY_PERSON_ID));
        Assertions.assertTrue(actual);
    }

    @Test
    public void shouldReturnTrueIfPersonDoesNotExist() {
        // given
        when(personRepository.existsById(eq(ANY_PERSON_ID))).thenReturn(false);

        //when
        boolean actual = personService.personExists(ANY_PERSON_ID);

        //then
        verify(personRepository).existsById(eq(ANY_PERSON_ID));
        Assertions.assertFalse(actual);
    }

    @Test
    public void shouldReturnPersonIfPersonExists() {
        // given
        when(personRepository.getReferenceById(eq(ANY_PERSON_ID))).thenReturn(personEntity);

        //when
        var actual = personService.getById(ANY_PERSON_ID);

        //then
        verify(personRepository).getReferenceById(eq(ANY_PERSON_ID));
        Assertions.assertEquals(personEntity, actual);
    }

    @Test
    public void shouldFindByNameAndSurname() {
        // given
        when(personRepository.getByFirstAndLastName(eq(ANY_PERSON_FIRST_NAME), eq(ANY_PERSON_LAST_NAME)))
                .thenReturn(Optional.of(personEntity));

        // when
        var actual = personService.getByFirstAndLastName(ANY_PERSON_FIRST_NAME, ANY_PERSON_LAST_NAME);

        // then
        verify(personRepository).getByFirstAndLastName(eq(ANY_PERSON_FIRST_NAME), eq(ANY_PERSON_LAST_NAME));
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(personEntity, actual.get());
    }

    @ParameterizedTest(name = "when firstName is {0} and lastName is {1}")
    @MethodSource("namesProvider")
    public void shouldThrowExceptionWhenNamesNull(String firstName, String lastName) {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                personService.getByFirstAndLastName(firstName, lastName));
    }

}