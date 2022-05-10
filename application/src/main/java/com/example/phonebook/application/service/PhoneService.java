package com.example.phonebook.application.service;

import com.example.phonebook.application.database.entity.Phone;
import com.example.phonebook.application.database.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
        return phoneRepository.getAll();
    }

    public List<Double> getFundsPoweredTo(double powNumber) {
        return phoneRepository.getAll().stream()
                .map(p -> Math.pow(p.getFunds(), powNumber))
                .collect(Collectors.toList());
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
        Phone forUpdate = phoneRepository.getOne(phone.getId());
        forUpdate.setCountryCode(phone.getCountryCode());
        forUpdate.setPhoneNumber(phone.getPhoneNumber());
        return phoneRepository.save(forUpdate);
    }

    @Override
    public void delete(int phoneId) {
        phoneRepository.deleteById(phoneId);
    }
}
