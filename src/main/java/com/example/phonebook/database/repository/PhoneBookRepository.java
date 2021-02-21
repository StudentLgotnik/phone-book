package com.example.phonebook.database.repository;

import com.example.phonebook.database.entity.PhoneBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneBookRepository extends JpaRepository<PhoneBook, Integer> {

    @Query("SELECT pb FROM PhoneBook pb join fetch pb.user")
    List<PhoneBook> getAll();
}
