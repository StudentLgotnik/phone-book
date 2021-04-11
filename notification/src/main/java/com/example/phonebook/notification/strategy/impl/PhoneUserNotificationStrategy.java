package com.example.phonebook.notification.strategy.impl;

import com.example.phonebook.dto.Event;
import com.example.phonebook.notification.strategy.NotificationResult;
import com.example.phonebook.notification.strategy.UserNotificationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneUserNotificationStrategy implements UserNotificationStrategy {

    Logger logger = LoggerFactory.getLogger(PhoneUserNotificationStrategy.class);

    @Override
    public NotificationResult notifyUser(Event event) {
        logger.info("Notification was sent by SMS!");
        return NotificationResult.Success;
    }
}
