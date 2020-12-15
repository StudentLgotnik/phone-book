package com.example.phonebook.controller;

import com.example.phonebook.database.entity.Phone;
import com.example.phonebook.service.CountryCodeService;
import com.example.phonebook.service.PersonService;
import com.example.phonebook.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/phone/numbers")
public class PhoneController {

    private final PhoneService phoneService;

    @Autowired
    public PhoneController(PhoneService restService) {
        this.phoneService = restService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Phone>> getAll() {
        return ResponseEntity.ok(phoneService.getAll());
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Phone> getAll(@PathVariable("id") String phoneId) {
        return ResponseEntity.ok(phoneService.get(Integer.parseInt(phoneId)));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Phone> newPhone(@RequestBody Phone phone) {
        return ResponseEntity.ok(phoneService.create(phone));
    }

    @PutMapping
    public ResponseEntity<Phone> updatePhone(@RequestBody Phone phone) {
        return ResponseEntity.ok(phoneService.update(phone));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deletePhone(@PathVariable("id") String phoneId) {
        phoneService.delete(Integer.parseInt(phoneId));
        return ResponseEntity.ok().build();
    }
}
