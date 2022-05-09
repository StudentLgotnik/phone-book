package com.example.phonebook.userservice.controller;

import com.example.phonebook.userservice.database.entity.queries.UserQueries;
import com.example.phonebook.userservice.dto.UserDto;
import com.example.phonebook.userservice.security.details.PhoneBookUserDetails;
import com.example.phonebook.userservice.security.utils.JwtTokenUtil;
import com.example.phonebook.userservice.service.AuthenticationResult;
import com.example.phonebook.userservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(path = "/user")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final IUserService userService;
    private final UserQueries userQueries;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, IUserService userService, UserQueries userQueries) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.userQueries = userQueries;
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> newUser(@RequestBody UserDto userBody) {
        return ResponseEntity.ok(userService.create(userBody));
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginUser(@RequestBody UserDto userBody) {
        AuthenticationResult authenticationResult = userService.authenticate(userBody);

        if (authenticationResult.equals(AuthenticationResult.Success)) {
            return ResponseEntity
                    .ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            authenticationResult.getToken())
                    .body(authenticationResult.getMessage());
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(authenticationResult.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/change-login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> changeLogin(@RequestParam String login) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PhoneBookUserDetails user = (PhoneBookUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userQueries.updateLogin(user.getUser().getId(), login));
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PhoneBookUserDetails user = (PhoneBookUserDetails) authentication.getPrincipal();
        userQueries.deleteUser(user.getUser().getId());
        return ResponseEntity.noContent().build();
    }
}
