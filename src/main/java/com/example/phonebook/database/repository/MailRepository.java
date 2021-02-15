package com.example.phonebook.database.repository;

import com.example.phonebook.database.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends JpaRepository<Mail, Integer> {
}
