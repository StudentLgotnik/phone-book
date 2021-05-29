package com.example.phonebook.userservice.controller;

import com.example.phonebook.userservice.database.entity.User;
import com.example.phonebook.userservice.dto.UserDto;
import com.example.phonebook.userservice.security.details.PhoneBookUserDetails;
import com.example.phonebook.userservice.security.utils.JwtTokenUtil;
import com.example.phonebook.userservice.service.AuthenticationResult;
import com.example.phonebook.userservice.service.IUserService;
import com.example.phonebook.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final IUserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, IUserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
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
//        try {
//            Authentication authenticate = authenticationManager
//                    .authenticate(
//                            new UsernamePasswordAuthenticationToken(
//                                    userBody.getLogin(), userBody.getPassword()
//                            )
//                    );
//
//            User user = ((PhoneBookUserDetails) authenticate.getPrincipal()).getUser();
//
//            return ResponseEntity.ok()
//                    .header(
//                            HttpHeaders.AUTHORIZATION,
//                            jwtTokenUtil.generateAccessToken(user)
//                    )
//                    .body(user);
//        } catch (BadCredentialsException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
    }
}
