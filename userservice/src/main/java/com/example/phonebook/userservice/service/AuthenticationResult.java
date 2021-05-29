package com.example.phonebook.userservice.service;

public enum AuthenticationResult {
    Success,
    Failure;

    private String message;
    private String token;

    public AuthenticationResult withMessage(String message){
        this.message = message;
        return this;
    }

    public AuthenticationResult withToken(String token){
        this.token = token;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
