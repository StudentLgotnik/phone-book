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

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:db/queries/phone.properties")
public class PhoneQueries {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final PhoneRowMapper phoneRowMapper;

    @Value("${getPhonesLimitOffset}")
    private String getPhonesLimitOffset;

    @Value("${getPhonesCount}")
    private String getPhonesCount;

    @Value("${getPhonesWithCountryCode}")
    private String getPhonesWithCountryCode;

    @Value("${getFundsStatistic}")
    private String getFundsStatistic;

    @Value("${phonesByFirstCountryLetter}")
    private String phonesByFirstCountryLetter;

    @Value("${getPhonesDates}")
    private String getPhonesDates;

    @Value("${getRoundedFunds}")
    private String getRoundedFunds;

    @Value("${getPoweredFunds}")
    private String getPoweredFunds;

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

    public Integer getPhonesCount() {
        return namedJdbcTemplate.query(getPhonesCount, (ResultSet rs) -> {
            rs.next();
            return rs.getInt("count");
        });
    }

    public List<String> getPhonesNumbers() {
        return namedJdbcTemplate.query(getPhonesWithCountryCode, (rs, rowNum) -> rs.getString(1));
    }

    public Map<String, Double> getFundsStatistic() {
        return namedJdbcTemplate.query(getFundsStatistic, (ResultSet rs) -> {
            rs.next();
            Map<String, Double> statistic = new HashMap<>();
            statistic.put("avg", rs.getDouble("avg"));
            statistic.put("min", rs.getDouble("min"));
            statistic.put("max", rs.getDouble("max"));
            return statistic;
        });
    }

    public List<Phone> getPhonesByCountryFirstLetter(char countryLetter) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("countryLetter",countryLetter);
        return namedJdbcTemplate.query(phonesByFirstCountryLetter, sqlParameterSource, phoneRowMapper);
    }

    public List<LocalDateTime> getPhonesDates() {
        return namedJdbcTemplate.query(getPhonesDates, (rs, rowNum) -> rs.getObject("date", LocalDateTime.class));
    }

    public List<Double> getRoundedFunds() {
        return namedJdbcTemplate.query(getRoundedFunds, (rs, rowNum) -> rs.getDouble("funds"));
    }

    public List<Double> getFundsPoweredTo(double powNumber) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("powNumber",powNumber);
        return namedJdbcTemplate.query(getPoweredFunds, sqlParameterSource, (rs, rowNum) -> rs.getDouble("funds"));
    }
}
