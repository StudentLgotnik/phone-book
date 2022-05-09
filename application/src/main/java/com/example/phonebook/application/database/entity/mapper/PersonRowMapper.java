package com.example.phonebook.application.database.entity.mapper;

import com.example.phonebook.application.database.entity.Person;
import com.example.phonebook.application.database.entity.PhoneBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PersonRowMapper implements RowMapper<Person> {

    private PhoneBookRowMapper phoneBookRowMapper;

    @Autowired
    public void setPhoneBookRowMapper(PhoneBookRowMapper phoneBookRowMapper) {
        this.phoneBookRowMapper = phoneBookRowMapper;
    }

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("ID"));
        person.setName(rs.getString("NAME"));
        person.setSecondName(rs.getString("SECOND_NAME"));
        person.setAge(rs.getInt("AGE"));
        return null;
    }
}
