package com.example.phonebook.notification.listeners;

import com.example.phonebook.dto.Event;
import com.example.phonebook.notification.service.NotificationService;
import com.mailjet.client.errors.MailjetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

    private final NotificationService notificationService;

    @Autowired
    public EventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "phonebook", groupId = "phonebook-notification",  containerFactory = "eventKafkaListenerContainerFactory")
    public void listenEvents(Event event) {
        notificationService.notifyAboutEvent(event);
    }
}
