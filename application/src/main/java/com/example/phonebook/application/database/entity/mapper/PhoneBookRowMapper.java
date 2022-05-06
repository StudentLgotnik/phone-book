package com.example.phonebook.application.database.entity.mapper;

import com.example.phonebook.application.database.entity.PhoneBook;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class PhoneBookRowMapper implements RowMapper<PhoneBook> {

    @Override
    public PhoneBook mapRow(ResultSet rs, int rowNum) throws SQLException {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.setTitle(rs.getString("TITLE"));
        phoneBook.setCreationDate(rs.getObject("CREATION_DATE", LocalDateTime.class));
        phoneBook.setUpdateDate(rs.getObject("UPDATE_DATE", LocalDateTime.class));
        return phoneBook;
    }
}
