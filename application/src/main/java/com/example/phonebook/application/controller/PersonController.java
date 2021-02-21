package com.example.phonebook.application.controller;

import com.example.phonebook.application.database.entity.Person;
import com.example.phonebook.application.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService restService) {
        this.personService = restService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.ok(personService.getAll());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> find(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "secondName", required = false) String secondName,
            @RequestParam(value = "phone", required = false) String phone) {
        return ResponseEntity.ok(personService.find(name, secondName, phone));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getOne(@PathVariable("id") String personId) {
        return ResponseEntity.ok(personService.get(Integer.parseInt(personId)));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> newPerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.create(person));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.update(person));
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping(value = "{id}")
    public ResponseEntity deletePerson(@PathVariable("id") String personId) {
        personService.delete(Integer.parseInt(personId));
        return ResponseEntity.ok().build();
    }
}
