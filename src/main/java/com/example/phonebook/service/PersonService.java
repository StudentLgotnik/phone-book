package com.example.phonebook.service;

import com.example.phonebook.database.entity.Person;
import com.example.phonebook.database.repository.PersonRepository;
import com.example.phonebook.database.specification.PersonNameSpecification;
import com.example.phonebook.database.specification.PersonPhoneSpecification;
import com.example.phonebook.database.specification.PersonSecondNameSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService implements IService<Person> {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAll() {
        return personRepository.getAll();
    }

    @Cacheable("findpersons")
    public List<Person> find(String name, String secondName, String phone) {

        PersonNameSpecification nameSpecification = new PersonNameSpecification(name);
        PersonSecondNameSpecification secondNameSpecification = new PersonSecondNameSpecification(secondName);
        PersonPhoneSpecification phoneSpecification = new PersonPhoneSpecification(phone);

        return personRepository.findAll(nameSpecification.and(secondNameSpecification).and(phoneSpecification));
    }

    @Override
    @Cacheable("persons")
    public Person get(int id){
        return personRepository.findById(id).orElse(null);

    }

    @Override
    @Transactional
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Override
    @Transactional
    public Person update(Person person) {
        Person forUpdate = personRepository.getOne(person.getId());
        forUpdate.setName(person.getName());
        forUpdate.setSecondName(person.getSecondName());
        forUpdate.setAge(person.getAge());
        return personRepository.save(forUpdate);
    }

    @Override
    @Transactional
    public void delete(int personId) {
        personRepository.deleteById(personId);
    }
}
