package com.example.phonebook.application.database.repository;

import com.example.phonebook.application.database.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>, JpaSpecificationExecutor<Person> {

    @Query("SELECT p FROM Person p JOIN FETCH p.phoneBook JOIN FETCH p.phones ph JOIN FETCH ph.countryCode")
    List<Person> getAll();
}
