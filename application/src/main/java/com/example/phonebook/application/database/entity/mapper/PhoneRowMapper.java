package com.example.phonebook.application.database.entity.mapper;

import com.example.phonebook.application.database.entity.Phone;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class PhoneRowMapper implements RowMapper<Phone> {

    @Override
    public Phone mapRow(ResultSet rs, int rowNum) throws SQLException {
        Phone phone = new Phone();
        phone.setPhoneNumber(rs.getString("PHONE_NUMBER"));
        phone.setOperatorName(rs.getString("OPERATOR_NAME"));
        phone.setFunds(rs.getInt("FUNDS"));
        phone.setRegistrationDate(rs.getObject("REGISTRATION_DATE", LocalDateTime.class));
        phone.setActivationDate(rs.getObject("ACTIVATION_DATE", LocalDateTime.class));
        return phone;
    }
}
