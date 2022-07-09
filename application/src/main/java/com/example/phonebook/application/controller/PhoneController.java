package com.example.phonebook.application.controller;

import com.example.phonebook.application.database.entity.Phone;
import com.example.phonebook.application.database.repository.queries.PhoneQueries;
import com.example.phonebook.application.dto.Wrapper;
import com.example.phonebook.application.dto.metadata.PhoneMetadata;
import com.example.phonebook.application.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Wrapper<List<Phone>, PhoneMetadata>> getAllLimitOffset(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
        return ResponseEntity.ok(
                Wrapper.of(
                        phoneQueries.getPhones(limit, offset),
                        new PhoneMetadata(phoneQueries.getPhonesCount())
                ));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/just-numbers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAllPhoneNumbers() {
        return ResponseEntity.ok(phoneQueries.getPhonesNumbers());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/just-numbers-projection", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAllPhoneNumbersProjection() {
        return ResponseEntity.ok(phoneService.getPhonesNumbersProjection());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/funds/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Double>> getFundsStatistics() {
        return ResponseEntity.ok(phoneQueries.getFundsStatistic());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/country-starts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Phone>> getCountryStartWithLetter(@RequestParam("letter") char letter) {
        return ResponseEntity.ok(phoneQueries.getPhonesByCountryFirstLetter(letter));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/dates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LocalDateTime>> getPhonesDates() {
        return ResponseEntity.ok(phoneQueries.getPhonesDates());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/rounded-funds", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Double>> getRoundedFunds() {
        return ResponseEntity.ok(phoneQueries.getRoundedFunds());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/powered-funds-jsp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Double>> getPoweredFundsJsp(@RequestParam("powNumber") double powNumber) {
        return ResponseEntity.ok(phoneService.getFundsPoweredTo(powNumber));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/powered-funds-jdbc", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Double>> getPoweredFundsJdbc(@RequestParam("powNumber") double powNumber) {
        return ResponseEntity.ok(phoneQueries.getFundsPoweredTo(powNumber));
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
