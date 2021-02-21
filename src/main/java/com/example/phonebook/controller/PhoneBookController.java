package com.example.phonebook.controller;

import com.example.phonebook.database.entity.PhoneBook;
import com.example.phonebook.service.PhoneBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/phonebook")
public class PhoneBookController {

    private final PhoneBookService phoneBookService;

    @Autowired
    public PhoneBookController(PhoneBookService restService) {
        this.phoneBookService = restService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PhoneBook>> getAll() {
        return ResponseEntity.ok(phoneBookService.getAll());
    }
}
