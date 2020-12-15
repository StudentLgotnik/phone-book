package com.example.phonebook.service;

import com.example.phonebook.database.entity.CountryCode;
import com.example.phonebook.database.repository.CountryCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CountryCodeService implements IService<CountryCode> {

    private final CountryCodeRepository countryCodeRepository;

    @Autowired
    public CountryCodeService(CountryCodeRepository countryCodeRepository) {
        this.countryCodeRepository = countryCodeRepository;
    }

    @Override
    public List<CountryCode> getAll() {
        return countryCodeRepository.findAll();
    }

    @Override
    public CountryCode get(int id){
        return countryCodeRepository.findById(id).orElse(null);
    }

    @Override
    public CountryCode create(CountryCode countryCode) {
        return countryCodeRepository.save(countryCode);
    }

    @Override
    public CountryCode update(CountryCode countryCode) {
        CountryCode forUpdate = countryCodeRepository.getOne(countryCode.getId());
        forUpdate.setCode(countryCode.getCode());
        forUpdate.setCountry(countryCode.getCountry());
        return countryCodeRepository.save(forUpdate);
    }

    @Override
    public void delete(int countryCodeId) {
        countryCodeRepository.deleteById(countryCodeId);
    }
}
