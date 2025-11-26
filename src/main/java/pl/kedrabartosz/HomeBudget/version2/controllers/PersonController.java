package pl.kedrabartosz.HomeBudget.version2.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kedrabartosz.HomeBudget.version2.entities.PersonEntity;
import pl.kedrabartosz.HomeBudget.version2.repositories.PersonRepository;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;

    @GetMapping
    public List<PersonEntity> getAllPersons() {
        return personRepository.findAll();
    }
}