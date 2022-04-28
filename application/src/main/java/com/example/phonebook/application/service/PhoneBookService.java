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
        return phoneBookRepository.findAll();
    }

    @Override
    public PhoneBook get(int id) {
        return null;
    }

    @Override
    public PhoneBook create(PhoneBook phoneBook) {
        return phoneBookRepository.save(phoneBook);
    }

    @Override
    public PhoneBook update(PhoneBook phoneBook) {
        PhoneBook existed = phoneBookRepository.findById(phoneBook.getId())
                .orElseThrow((() -> new IllegalArgumentException(String.format("Phone book code with id %s doesn't exist", phoneBook.getId()))));
        existed.setTitle(phoneBook.getTitle());
        existed.setUser(phoneBook.getUser());
        return phoneBookRepository.save(phoneBook);
    }

    @Override
    public void delete(int id) {
        phoneBookRepository.deleteById(id);
    }
}
