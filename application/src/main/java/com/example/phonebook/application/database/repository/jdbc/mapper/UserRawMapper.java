package com.example.phonebook.application.database.repository.jdbc.mapper;

import com.example.phonebook.application.database.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRawMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("ID"));
        user.setEmail(resultSet.getString("EMAIL"));
        user.setLogin(resultSet.getString("LOGIN"));
        user.setPassword(resultSet.getString("PASSWORD"));
        user.setPhone(resultSet.getString("PHONE"));
        return user;
    }
}
