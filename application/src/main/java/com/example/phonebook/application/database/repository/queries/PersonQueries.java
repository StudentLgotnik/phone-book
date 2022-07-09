package com.example.phonebook.application.database.repository.queries;

import com.example.phonebook.application.database.entity.Person;
import com.example.phonebook.application.database.entity.mapper.PersonRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:db/queries/person.properties")
public class PersonQueries {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final PersonRowMapper personRowMapper;

    @Value("${personsByPhBookTitleAndCountry}")
    private String personsByPhBookTitleAndCountry;

    @Value("${getAgePersonsGroups}")
    private String getAgePersonsGroups;

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

    public List<Pair<String, String>> getAgePersonsGroups() {
        return namedJdbcTemplate.query(getAgePersonsGroups,
                (rs, rowNum) -> Pair.of( rs.getString("name"),  rs.getString("age_group")));
    }

    public List<Map.Entry<String, String>> getAgePersonsGroups2() {
        return namedJdbcTemplate.query(getAgePersonsGroups,
                (rs, rowNum) -> new AbstractMap.SimpleEntry<>(rs.getString("name"), rs.getString("age_group")));
    }
}
