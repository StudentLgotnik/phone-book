package com.example.phonebook.application.service;

import com.example.phonebook.application.database.entity.User;
import com.example.phonebook.application.database.repository.UserRepository;
import com.example.phonebook.event.NewUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements IService<User> {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final KafkaTemplate<String, NewUser.NewUserEvent> eventKafkaTemplate;
    private final String topicName = "phonebook";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, KafkaTemplate<String, NewUser.NewUserEvent> eventKafkaTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventKafkaTemplate = eventKafkaTemplate;
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
        logger.info("User successfully created");
        NewUser.NewUserEvent newUserEvent = NewUser.NewUserEvent.newBuilder()
                .setLogin(created.getLogin())
                .setEmail(created.getEmail())
                .setPhone(created.getPhone())
                .build();

        eventKafkaTemplate.send(topicName, newUserEvent);
        logger.info("New user event sent");
        return created;
    }

    @Override
    @Transactional
    public User update(User user) {
        User forUpdate = userRepository.findById(user.getId())
                .orElseThrow((() -> new IllegalArgumentException(String.format("User with id %s doesn't exist", user.getId()))));
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
