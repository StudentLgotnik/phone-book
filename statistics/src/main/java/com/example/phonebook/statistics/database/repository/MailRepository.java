package com.example.phonebook.statistics.database.repository;

import com.example.phonebook.statistics.database.entity.Mail;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailRepository extends MongoRepository<Mail, Integer> {
}
