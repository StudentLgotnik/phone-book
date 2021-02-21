package com.example.phonebook.statistics.controller;

import com.example.phonebook.statistics.database.entity.Mail;
import com.example.phonebook.statistics.service.Mailservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "/mail")
public class MailController {

    private Mailservice mailservice;

    @Autowired
    public MailController(Mailservice mailservice) {
        this.mailservice = mailservice;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Mail>> getAll() {
        return ResponseEntity.ok(mailservice.getAll());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mail> createMail(@RequestBody Mail mail) {
        return ResponseEntity.ok(mailservice.create(mail));
    }

}
