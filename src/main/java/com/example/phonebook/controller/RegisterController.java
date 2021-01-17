package com.example.phonebook.controller;

import com.example.phonebook.database.entity.Person;
import com.example.phonebook.database.entity.User;
import com.example.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> newUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String userId) {
        userService.delete(Integer.parseInt(userId));
        return ResponseEntity.ok().build();
    }
}
