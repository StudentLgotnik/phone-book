package com.example.phonebook.application.database.repository.queries;

import com.example.phonebook.application.database.entity.PhoneBook;
import com.example.phonebook.application.database.entity.mapper.PhoneBookRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:db/queries/phone-book.properties")
public class PhoneBookQueries {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final PhoneBookRowMapper phoneBookRowMapper;

    @Value("${phoneBookPersonsMoreThan}")
    private String phoneBookPersonsMoreThan;

    @Autowired
    public PhoneBookQueries(NamedParameterJdbcTemplate namedJdbcTemplate, PhoneBookRowMapper phoneBookRowMapper) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.phoneBookRowMapper = phoneBookRowMapper;
    }

    public List<PhoneBook> getPhoneBookWithUserMoreThan(int personNumber) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("personsNumber", personNumber);
        return namedJdbcTemplate.query(phoneBookPersonsMoreThan, sqlParameterSource, phoneBookRowMapper);
    }
}
