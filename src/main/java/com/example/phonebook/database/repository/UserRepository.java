package com.example.phonebook.database.repository;

import com.example.phonebook.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
