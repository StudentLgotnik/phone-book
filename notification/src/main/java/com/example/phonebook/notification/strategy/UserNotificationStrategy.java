package com.example.phonebook.notification.strategy;

import com.example.phonebook.dto.Event;

public interface UserNotificationStrategy {

    NotificationResult notifyUser(Event event);

}
