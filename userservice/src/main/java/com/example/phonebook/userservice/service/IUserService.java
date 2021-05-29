package com.example.phonebook.userservice.service;

import com.example.phonebook.userservice.dto.UserDto;

public interface IUserService {

    UserDto create(UserDto userDto);

    AuthenticationResult authenticate(UserDto userDto);

}
