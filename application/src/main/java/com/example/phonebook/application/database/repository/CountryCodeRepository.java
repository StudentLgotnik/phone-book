package com.example.phonebook.application.database.repository;

import com.example.phonebook.application.database.entity.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryCodeRepository extends JpaRepository<CountryCode, Integer> {
}
