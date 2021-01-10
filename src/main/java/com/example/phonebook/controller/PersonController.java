package com.example.phonebook.controller;

import com.example.phonebook.database.entity.Person;
import com.example.phonebook.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.ok(personService.getAll());
    }

    @GetMapping(value = "find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> find(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "secondName", required = false) String secondName,
            @RequestParam(value = "phone", required = false) String phone) {
        return ResponseEntity.ok(personService.find(name, secondName, phone));
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getOne(@PathVariable("id") String personId) {
        return ResponseEntity.ok(personService.get(Integer.parseInt(personId)));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> newPerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.create(person));
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.update(person));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deletePerson(@PathVariable("id") String personId) {
        personService.delete(Integer.parseInt(personId));
        return ResponseEntity.ok().build();
    }
}
