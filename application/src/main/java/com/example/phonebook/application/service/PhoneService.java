package com.example.phonebook.application.service;

import com.example.phonebook.application.database.entity.Phone;
import com.example.phonebook.application.database.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PhoneService implements IService<Phone>{

    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public List<Phone> getAll() {
        return phoneRepository.findAll();
    }

    @Override
    @Cacheable("phones")
    public Phone get(int id){
        return phoneRepository.findById(id).orElse(null);
    }

    @Override
    public Phone create(Phone phone) {
        return phoneRepository.save(phone);
    }

    @Override
    public Phone update(Phone phone) {
        Phone forUpdate = phoneRepository.findById(phone.getId())
                .orElseThrow((() -> new IllegalArgumentException(String.format("Phone code with id %s doesn't exist", phone.getId()))));
        forUpdate.setCountryCode(phone.getCountryCode());
        forUpdate.setPhoneNumber(phone.getPhoneNumber());
        return phoneRepository.save(forUpdate);
    }

    @Override
    public void delete(int phoneId) {
        phoneRepository.deleteById(phoneId);
    }
}
