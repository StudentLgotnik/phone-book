package com.example.phonebook.userservice.database.entity.mapper.impl;

import com.example.phonebook.userservice.database.entity.User;
import com.example.phonebook.userservice.database.entity.mapper.IMapper;
import com.example.phonebook.userservice.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper implements IMapper<UserDto, User> {

    @Override
    public User toDbEntity(UserDto userDto) {
        if (Objects.isNull(userDto)) {
            return null;
        }
        User entity = new User();
        entity.setEmail(userDto.getEmail());
        entity.setLogin(userDto.getLogin());
        entity.setPhone(userDto.getPhone());
        return entity;
    }

    @Override
    public UserDto fromDbEntity(User user) {
        if (Objects.isNull(user)) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setEmail(user.getEmail());
        dto.setLogin(user.getLogin());
        dto.setPhone(user.getPhone());
        return dto;
    }
}
