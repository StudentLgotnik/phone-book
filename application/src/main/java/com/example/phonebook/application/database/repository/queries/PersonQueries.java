package com.example.phonebook.application.database.repository.queries;

import com.example.phonebook.application.database.entity.Person;
import com.example.phonebook.application.database.entity.mapper.PersonRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:db/queries/person.properties")
public class PersonQueries {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final PersonRowMapper personRowMapper;

    @Value("${personsByPhBookTitleAndCountry}")
    private String personsByPhBookTitleAndCountry;

    public PersonQueries(NamedParameterJdbcTemplate namedJdbcTemplate, PersonRowMapper personRowMapper) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.personRowMapper = personRowMapper;
    }

    public List<Person> getPersonsFromPhBookByCountry(String phoneBookTitle, String phoneCountry) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("title", phoneBookTitle)
                .addValue("country", phoneCountry);
        return namedJdbcTemplate.query(personsByPhBookTitleAndCountry, sqlParameterSource, personRowMapper);
    }
}
