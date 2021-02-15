package com.example.phonebook.database.repository;

import com.example.phonebook.database.entity.PhoneBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneBookRepository extends JpaRepository<PhoneBook, Integer> {
}
