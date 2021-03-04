package com.example.phonebook.statistics.listeners;

import com.example.phonebook.statistics.database.entity.Mail;
import com.example.phonebook.statistics.service.Mailservice;
import com.mailjet.client.errors.MailjetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    private final Mailservice mailservice;

    @Autowired
    public MessageListener(Mailservice mailservice) {
        this.mailservice = mailservice;
    }

    @KafkaListener(topics = "phonebook", groupId = "phonebook-statistic")
    public void listenGroupFoo(String message) {
        try {
            mailservice.sendMailAndSave(new Mail(message));
        } catch (MailjetException e) {
            e.printStackTrace();
        }
        System.out.println("Received Message in group foo: " + message);
    }
}
