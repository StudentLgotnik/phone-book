package com.example.phonebook.service;

import com.example.phonebook.database.entity.Person;
import com.example.phonebook.database.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PersonService implements IService<Person> {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public Person get(int id){
        return personRepository.findById(id).orElse(null);

    }

    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person update(Person person) {
        Person forUpdate = personRepository.getOne(person.getId());
        forUpdate.setName(person.getName());
        forUpdate.setSecondName(person.getSecondName());
        forUpdate.setAge(person.getAge());
        return personRepository.save(forUpdate);
    }

    @Override
    public void delete(int personId) {
        personRepository.deleteById(personId);
    }
}
