package com.example.phonebook.database.repository;

import com.example.phonebook.database.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {

    @Query("SELECT p FROM Phone p join fetch p.person join fetch p.countryCode")
    List<Phone> getAll();
}
