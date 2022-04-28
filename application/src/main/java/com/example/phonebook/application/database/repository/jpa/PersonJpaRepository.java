package com.example.phonebook.application.database.repository.jpa;

import com.example.phonebook.application.database.entity.Person;
import com.example.phonebook.application.database.repository.PersonRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface PersonJpaRepository extends JpaRepository<Person, Integer>, JpaSpecificationExecutor<Person>, PersonRepository {

    @Query("SELECT p FROM Person p JOIN FETCH p.phoneBook JOIN FETCH p.phones ph JOIN FETCH ph.countryCode")
    List<Person> findAll();
}
