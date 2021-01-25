package com.example.phonebook.database.repository;

import com.example.phonebook.database.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail, Integer> {
}
