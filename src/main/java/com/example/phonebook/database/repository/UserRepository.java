package com.example.phonebook.database.repository;

import com.example.phonebook.database.entity.Person;
import com.example.phonebook.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
