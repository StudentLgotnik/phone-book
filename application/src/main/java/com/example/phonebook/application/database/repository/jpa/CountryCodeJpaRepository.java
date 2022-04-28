package com.example.phonebook.application.database.repository.jpa;

import com.example.phonebook.application.database.entity.CountryCode;
import com.example.phonebook.application.database.repository.CountryCodeRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface CountryCodeJpaRepository extends JpaRepository<CountryCode, Integer>, CountryCodeRepository {
}
