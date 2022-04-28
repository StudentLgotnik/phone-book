package com.example.phonebook.application.database.repository.jpa;

import com.example.phonebook.application.database.entity.Phone;
import com.example.phonebook.application.database.repository.PhoneRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface PhoneJpaRepository extends JpaRepository<Phone, Integer>, PhoneRepository {

    @Query("SELECT p FROM Phone p join fetch p.person join fetch p.countryCode")
    List<Phone> findAll();
}
