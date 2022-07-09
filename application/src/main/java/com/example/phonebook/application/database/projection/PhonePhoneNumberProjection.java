package com.example.phonebook.application.database.projection;

import org.springframework.beans.factory.annotation.Value;

public interface PhonePhoneNumberProjection {

    @Value("#{'+' + target.countryCode.code + target.phoneNumber}")
    String getFullPhoneNumber();
}
