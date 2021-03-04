package com.example.phonebook.statistics.service;

import com.example.phonebook.statistics.database.entity.Mail;
import com.example.phonebook.statistics.database.repository.MailRepository;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TrackOpens;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class Mailservice {

    private final MailRepository mailRepository;

    @Autowired
    public Mailservice(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    public List<Mail> getAll() {
        return mailRepository.findAll();
    }

    public Mail create(Mail mail) {
        return mailRepository.save(mail);
    }

    @Transactional(rollbackFor = MailjetException.class)
    public void sendMailAndSave(Mail mail) throws MailjetException {
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
                .textPart(mail.getContent())
                .trackOpens(TrackOpens.ENABLED)
                .build();

        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message1)
                .build();

        SendEmailsResponse response = request.sendWith(client);

        mailRepository.save(mail);
    }

    public void delete(int id) {
        mailRepository.deleteById(id);
    }
}
