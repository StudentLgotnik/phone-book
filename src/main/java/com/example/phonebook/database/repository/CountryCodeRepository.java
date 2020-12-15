package com.example.phonebook.database.repository;

import com.example.phonebook.database.entity.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryCodeRepository extends JpaRepository<CountryCode, Integer> {
}
