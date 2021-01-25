package com.example.phonebook.service;

import com.example.phonebook.database.entity.Mail;
import com.example.phonebook.database.entity.User;
import com.example.phonebook.database.repository.UserRepository;
import com.mailjet.client.errors.MailjetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements IService<User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Mailservice mailservice;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, Mailservice mailservice) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailservice = mailservice;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByName(String name) {
        return userRepository.findAll().stream().filter(u -> u.getLogin().equals(name)).findFirst().orElse(null);
    }


    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User createUserAndNotify(User user) {
        User created = create(user);
        Mail mail = new Mail();
        mail.setContent("New user was added: " + user);
        try {
            mailservice.sendMailAndSave(mail);
        } catch (MailjetException e) {
            e.printStackTrace();
        }
        return created;
    }

    @Override
    @Transactional
    public User update(User user) {
        User forUpdate = userRepository.getOne(user.getId());
        forUpdate.setLogin(user.getLogin());
        forUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(forUpdate);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
