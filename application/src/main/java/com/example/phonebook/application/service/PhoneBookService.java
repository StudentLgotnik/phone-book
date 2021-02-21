package com.example.phonebook.application.service;

import com.example.phonebook.application.database.entity.PhoneBook;
import com.example.phonebook.application.database.repository.PhoneBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneBookService implements IService<PhoneBook>{

    private PhoneBookRepository phoneBookRepository;

    @Autowired
    public PhoneBookService(PhoneBookRepository phoneBookRepository) {
        this.phoneBookRepository = phoneBookRepository;
    }

    @Override
    public List<PhoneBook> getAll() {
        return phoneBookRepository.getAll();
    }

    @Override
    public PhoneBook get(int id) {
        return null;
    }

    @Override
    public PhoneBook create(PhoneBook phoneBook) {
        return null;
    }

    @Override
    public PhoneBook update(PhoneBook phoneBook) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
