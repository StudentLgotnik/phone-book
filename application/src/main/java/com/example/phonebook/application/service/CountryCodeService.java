package com.example.phonebook.application.service;

import com.example.phonebook.application.database.entity.CountryCode;
import com.example.phonebook.application.database.repository.CountryCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Cacheable("countryCodes")
    public CountryCode get(int id){
        return countryCodeRepository.findById(id).orElse(null);
    }

    @Override
    public CountryCode create(CountryCode countryCode) {
        return countryCodeRepository.save(countryCode);
    }

    @Override
    public CountryCode update(CountryCode countryCode) {
        CountryCode forUpdate = countryCodeRepository.findById(countryCode.getId())
                .orElseThrow((() -> new IllegalArgumentException(String.format("Country code with id %s doesn't exist", countryCode.getId()))));
        forUpdate.setCode(countryCode.getCode());
        forUpdate.setCountry(countryCode.getCountry());
        return countryCodeRepository.save(forUpdate);
    }

    @Override
    public void delete(int countryCodeId) {
        countryCodeRepository.deleteById(countryCodeId);
    }
}
