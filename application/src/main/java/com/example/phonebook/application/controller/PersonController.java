package com.example.phonebook.application.controller;

import com.example.phonebook.application.database.entity.Person;
import com.example.phonebook.application.database.repository.queries.PersonQueries;
import com.example.phonebook.application.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/person")
public class PersonController {

    private final PersonService personService;
    private final PersonQueries personQueries;

    @Autowired
    public PersonController(PersonService restService, PersonQueries personQueries) {
        this.personService = restService;
        this.personQueries = personQueries;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> getAll() {
        return ResponseEntity.ok(personService.getAll());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/tittle-country", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> getAllPhBookTitleAndPhoneCountry(@RequestParam("title") String title, @RequestParam("country") String country) {
        return ResponseEntity.ok(personQueries.getPersonsFromPhBookByCountry(title, country));
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
    @GetMapping(value = "/persons-groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Pair<String, String>>> getAgePersonsGroups() {
        return ResponseEntity.ok(personQueries.getAgePersonsGroups());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/persons-groups2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map.Entry<String, String>>> getAgePersonsGroups2() {
        return ResponseEntity.ok(personQueries.getAgePersonsGroups2());
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
