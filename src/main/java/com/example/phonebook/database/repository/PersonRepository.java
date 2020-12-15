package com.example.phonebook.database.repository;

import com.example.phonebook.database.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
