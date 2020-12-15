package com.example.phonebook.controller;

import com.example.phonebook.database.entity.CountryCode;
import com.example.phonebook.service.CountryCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/country/code")
public class CountryCodeController {

    private final CountryCodeService countryCodeService;

    @Autowired
    public CountryCodeController(CountryCodeService restService) {
        this.countryCodeService = restService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CountryCode>> getAll() {
        return ResponseEntity.ok(countryCodeService.getAll());
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CountryCode> getAll(@PathVariable("id") String countryCodeId) {
        return ResponseEntity.ok(countryCodeService.get(Integer.parseInt(countryCodeId)));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CountryCode> newCountryCode(@RequestBody CountryCode person) {
        return ResponseEntity.ok(countryCodeService.create(person));
    }

    @PutMapping
    public ResponseEntity<CountryCode> updateCountryCode(@RequestBody CountryCode person) {
        return ResponseEntity.ok(countryCodeService.update(person));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteCountryCode(@PathVariable("id") String countryCodeId) {
        countryCodeService.delete(Integer.parseInt(countryCodeId));
        return ResponseEntity.ok().build();
    }
}
