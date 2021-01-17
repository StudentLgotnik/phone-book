package com.example.phonebook.database.repository;

import com.example.phonebook.database.entity.PhoneBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneBookRepository extends JpaRepository<PhoneBook, Integer> {
}
