package com.example.phonebook.notification.strategy;

import com.example.phonebook.dto.Event;

public class UserNotificationManager {

    private final UserNotificationStrategy strategy;

    public UserNotificationManager(UserNotificationStrategy strategy) {
        this.strategy = strategy;
    }

    public NotificationResult notifyUser(Event event) {
        return strategy.notifyUser(event);
    }
}
