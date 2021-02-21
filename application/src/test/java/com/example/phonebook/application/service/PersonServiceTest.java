package com.example.phonebook.application.service;

import com.example.phonebook.application.database.entity.Person;
import com.example.phonebook.application.database.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@RunWith(SpringRunner.class)
public class PersonServiceTest {

    @TestConfiguration
    static class PersonServiceTestContextConfiguration {

        @Autowired
        private PersonRepository employeeRepository;

        @Bean
        public PersonService employeeService() {
            return new PersonService(employeeRepository);
        }
    }

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    private List<Person> databaseData;

    @Before
    public void setUp() {
        databaseData = getDatabaseDataSet();

        Mockito.when(personRepository.findById(anyInt()))
                .thenAnswer(x -> java.util.Optional.of(databaseData.get(x.getArgument(0))));

        Mockito.when(personRepository.getOne(anyInt()))
                .thenAnswer(x -> databaseData.get(x.getArgument(0)));

        Mockito.when(personRepository.findAll(any(Specification.class)))
                .thenReturn(databaseData);

        Mockito.when(personRepository.findAll())
                .thenReturn(databaseData);

        Mockito.when(personRepository.save(any(Person.class)))
                .thenAnswer(x -> x.getArgument(0));
    }

    @Test
    public void getAllPersonsTest() {
        List<Person> allPersons = personService.getAll();

        assertThat(allPersons.size()).isEqualTo(5);
        assertThat(allPersons).isEqualTo(databaseData);
    }

    @Test
    public void findAllPersonsTest() {
        List<Person> allPersons = personService.find("name", "secondName", "phone");

        assertThat(allPersons.size()).isEqualTo(5);
        assertThat(allPersons).isEqualTo(databaseData);
    }

    @Test
    public void getPersonTest() {
        int id = 1;
        Person found = personService.get(id);

        assertThat(found).isEqualTo(databaseData.get(1));
    }

    @Test
    public void createPersonTest() {
        Person created = personService.create(databaseData.get(1));

        assertThat(created).isEqualTo(databaseData.get(1));
    }

    @Test
    public void updatePersonTest() {
        Person created = personService.update(databaseData.get(1));

        assertThat(created).isEqualTo(databaseData.get(1));
    }

    private List<Person> getDatabaseDataSet() {
        return Arrays.asList(
                newPerson(0, "User0", "SecondName0", 20),
                newPerson(1, "User1", "SecondName1", 21),
                newPerson(2, "User2", "SecondName2", 22),
                newPerson(3, "User3", "SecondName3", 23),
                newPerson(4, "User4", "SecondName4", 24)
        );
    }

    private Person newPerson(int id, String name, String secondName, int age) {
        Person newPerson = new Person();
        newPerson.setId(id);
        newPerson.setName(name);
        newPerson.setSecondName(secondName);
        newPerson.setAge(age);
        return newPerson;
    }
}
