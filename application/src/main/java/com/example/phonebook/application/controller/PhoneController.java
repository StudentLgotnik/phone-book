package com.example.phonebook.application.controller;

import com.example.phonebook.application.database.entity.Phone;
import com.example.phonebook.application.database.repository.queries.PhoneQueries;
import com.example.phonebook.application.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/phone/numbers")
public class PhoneController {

    private final PhoneService phoneService;
    private final PhoneQueries phoneQueries;

    @Autowired
    public PhoneController(PhoneService restService, PhoneQueries phoneQueries) {
        this.phoneService = restService;
        this.phoneQueries = phoneQueries;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Phone>> getAll() {
        return ResponseEntity.ok(phoneService.getAll());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/limit-offset", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Phone>> getAllLimitOffset(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        return ResponseEntity.ok(phoneQueries.getPhones(limit, offset));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Phone> getOne(@PathVariable("id") String phoneId) {
        return ResponseEntity.ok(phoneService.get(Integer.parseInt(phoneId)));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Phone> newPhone(@RequestBody Phone phone) {
        return ResponseEntity.ok(phoneService.create(phone));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping
    public ResponseEntity<Phone> updatePhone(@RequestBody Phone phone) {
        return ResponseEntity.ok(phoneService.update(phone));
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping(value = "{id}")
    public ResponseEntity deletePhone(@PathVariable("id") String phoneId) {
        phoneService.delete(Integer.parseInt(phoneId));
        return ResponseEntity.ok().build();
    }
}
