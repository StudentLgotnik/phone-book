package com.example.phonebook.notification.strategy.impl;

import com.example.phonebook.dto.Event;
import com.example.phonebook.notification.strategy.NotificationResult;
import com.example.phonebook.notification.strategy.UserNotificationStrategy;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TrackOpens;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUserNotificationStrategy implements UserNotificationStrategy {

    Logger logger = LoggerFactory.getLogger(MailUserNotificationStrategy.class);

    @Override
    public NotificationResult notifyUser(Event event) {
        ClientOptions options = ClientOptions.builder()
                .apiKey("a15141043b800372b8a8ab8f77d1359a")
                .apiSecretKey("c182104ea76a3b73e132212672341681")
                .build();

        MailjetClient client = new MailjetClient(options);

        TransactionalEmail message1 = TransactionalEmail
                .builder()
                .to(new SendContact("kazzarin99@gmail.com","Default"))
                .from(new SendContact("Default", "Максим"))
                .subject("New user was added")
                .textPart("New User added: " + event)
                .trackOpens(TrackOpens.ENABLED)
                .build();

        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message1)
                .build();

        //TODO some retry logic
        try {
            SendEmailsResponse response = request.sendWith(client);
            logger.info("Notification was sent by an email!");
            return NotificationResult.Success;
        } catch (MailjetException e) {
            logger.error("Could not send notification by an email!", e);
            return NotificationResult.Error;
        }
    }
}
