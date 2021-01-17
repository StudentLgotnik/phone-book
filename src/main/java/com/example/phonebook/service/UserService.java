package com.example.phonebook.service;

import com.example.phonebook.database.entity.User;
import com.example.phonebook.database.repository.PersonRepository;
import com.example.phonebook.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService implements IService<User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
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

    @Override
    public User update(User user) {
        User forUpdate = userRepository.getOne(user.getId());
        forUpdate.setLogin(user.getLogin());
        forUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(forUpdate);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
