package com.example.phonebook.notification.strategy.impl;

import com.example.phonebook.event.NewUser;
import com.example.phonebook.notification.strategy.NotificationResult;
import com.example.phonebook.notification.strategy.UserNotificationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoveUserNotificationStrategy implements UserNotificationStrategy {

    Logger logger = LoggerFactory.getLogger(DoveUserNotificationStrategy.class);

    @Override
    public NotificationResult notifyUser(NewUser.NewUserEvent event) {
        logger.info("Notification was sent by a dove!");
        return NotificationResult.Success;
    }
}
