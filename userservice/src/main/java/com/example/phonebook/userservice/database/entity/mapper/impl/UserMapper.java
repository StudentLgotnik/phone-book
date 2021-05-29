package com.example.phonebook.userservice.database.entity.mapper.impl;

import com.example.phonebook.userservice.database.entity.User;
import com.example.phonebook.userservice.database.entity.mapper.IMapper;
import com.example.phonebook.userservice.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IMapper<UserDto, User> {

    @Override
    public User toDbEntity(UserDto userDto) {
        User entity = new User();
        entity.setEmail(userDto.getEmail());
        entity.setLogin(userDto.getLogin());
        entity.setPhone(userDto.getPhone());
        return entity;
    }

    @Override
    public UserDto fromDbEntity(User user) {
        UserDto dto = new UserDto();
        dto.setEmail(user.getEmail());
        dto.setLogin(user.getLogin());
        dto.setPhone(user.getPhone());
        return dto;
    }
}
