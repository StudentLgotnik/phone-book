package com.example.phonebook.notification.strategy;

import com.example.phonebook.event.NewUser;

public interface UserNotificationStrategy {

    NotificationResult notifyUser(NewUser.NewUserEvent event);

}
