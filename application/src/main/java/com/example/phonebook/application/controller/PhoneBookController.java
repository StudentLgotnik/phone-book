package com.example.phonebook.application.controller;

import com.example.phonebook.application.database.entity.PhoneBook;
import com.example.phonebook.application.database.repository.queries.PhoneBookQueries;
import com.example.phonebook.application.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/phonebook")
public class PhoneBookController {

    private final PhoneBookService phoneBookService;
    private final PhoneBookQueries phoneBookQueries;

    @Autowired
    public PhoneBookController(PhoneBookService restService, PhoneBookQueries phoneBookQueries) {
        this.phoneBookService = restService;
        this.phoneBookQueries = phoneBookQueries;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PhoneBook>> getAll() {
        return ResponseEntity.ok(phoneBookService.getAll());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhoneBook> getOne(@PathVariable("id") String phoneBookId) {
        return ResponseEntity.ok(phoneBookService.get(Integer.parseInt(phoneBookId)));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PhoneBook> newPhoneBook(@RequestBody PhoneBook phoneBook) {
        return ResponseEntity.ok(phoneBookService.create(phoneBook));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping
    public ResponseEntity<PhoneBook> updatePhoneBook(@RequestBody PhoneBook phoneBook) {
        return ResponseEntity.ok(phoneBookService.update(phoneBook));
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping(value = "{id}")
    public ResponseEntity deletePhoneBook(@PathVariable("id") String phoneBookId) {
        phoneBookService.delete(Integer.parseInt(phoneBookId));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/have-users-more-than")
    public ResponseEntity<List<PhoneBook>> deletePhoneBook(@RequestParam("personsNumber") int personsNumber) {
        return ResponseEntity.ok(phoneBookQueries.getPhoneBookWithUserMoreThan(personsNumber));
    }
}
