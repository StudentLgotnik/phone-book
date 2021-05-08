package com.example.phonebook.notification.service;

import com.example.phonebook.event.NewUser;
import com.example.phonebook.notification.strategy.NotificationResult;
import com.example.phonebook.notification.strategy.UserNotificationManager;
import com.example.phonebook.notification.strategy.impl.DoveUserNotificationStrategy;
import com.example.phonebook.notification.strategy.impl.MailUserNotificationStrategy;
import com.example.phonebook.notification.strategy.impl.PhoneUserNotificationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationService {

    Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void notifyAboutEvent(NewUser.NewUserEvent event) {

        UserNotificationManager emailNotificationManager = new UserNotificationManager(new MailUserNotificationStrategy());

        NotificationResult result = emailNotificationManager.notifyUser(event);

        if (result.equals(NotificationResult.Success)) {
            logger.info("User was notified successfully by email");
            return;
        }

        UserNotificationManager phoneNotificationManager = new UserNotificationManager(new PhoneUserNotificationStrategy());

        result = phoneNotificationManager.notifyUser(event);

        if (result.equals(NotificationResult.Success)) {
            logger.info("User was notified successfully by phone");
            return;
        }

        UserNotificationManager doveNotificationManager = new UserNotificationManager(new DoveUserNotificationStrategy());

        result = doveNotificationManager.notifyUser(event);

        if (result.equals(NotificationResult.Success)) {
            logger.info("User was notified successfully by dove");
        }
        else {
            logger.info("User wasn't successfully notified");
        }
    }
}
