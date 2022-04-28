package com.example.phonebook.application.database.repository.jpa;

import com.example.phonebook.application.database.entity.PhoneBook;
import com.example.phonebook.application.database.repository.PhoneBookRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface PhoneBookJpaRepository extends JpaRepository<PhoneBook, Integer>, PhoneBookRepository {

    @Query("SELECT pb FROM PhoneBook pb join fetch pb.user")
    List<PhoneBook> findAll();
}
