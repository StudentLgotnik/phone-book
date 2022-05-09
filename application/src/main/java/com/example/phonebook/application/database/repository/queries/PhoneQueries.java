package com.example.phonebook.application.database.repository.queries;

import com.example.phonebook.application.database.entity.Phone;
import com.example.phonebook.application.database.entity.mapper.PhoneRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:db/queries/phone.properties")
public class PhoneQueries {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final PhoneRowMapper phoneRowMapper;

    @Value("${getPhonesLimitOffset}")
    private String getPhonesLimitOffset;

    @Autowired
    public PhoneQueries(NamedParameterJdbcTemplate namedJdbcTemplate, PhoneRowMapper phoneRowMapper) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.phoneRowMapper = phoneRowMapper;
    }

    public List<Phone> getPhones(int limit, int offset) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);
        return namedJdbcTemplate.query(getPhonesLimitOffset, sqlParameterSource, phoneRowMapper);
    }
}
