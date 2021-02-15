package com.example.phonebook.integration;

import com.example.phonebook.PhonebookApplication;
import com.example.phonebook.database.configuration.PostgresqlContainer;
import com.example.phonebook.database.entity.Person;
import com.example.phonebook.database.entity.PhoneBook;
import com.example.phonebook.database.entity.User;
import com.example.phonebook.database.repository.PersonRepository;
import com.example.phonebook.database.repository.PhoneBookRepository;
import com.example.phonebook.security.utils.JwtTokenUtil;
import com.example.phonebook.service.UserService;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectWriter;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PhonebookApplication.class)
@AutoConfigureMockMvc
public class PersonIntegrationTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgresqlContainer.getNewInstance();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PhoneBookRepository phoneBookRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private List<Person> databaseData;

    private User currentUser;

    @Before
    public void setUp() {
        currentUser = getCurrentUser();

        databaseData = getDatabaseDataSet();
    }

    @Test
    public void getAllPersonsControllerTest()
            throws Exception {
        mvc.perform(get("/person")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenUtil.generateAccessToken(currentUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(databaseData.size())))
                .andExpect(jsonPath("$[0].name", is(databaseData.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(databaseData.get(1).getName())))
                .andExpect(jsonPath("$[2].name", is(databaseData.get(2).getName())))
                .andExpect(jsonPath("$[3].name", is(databaseData.get(3).getName())));
    }

    @Test
    public void findPersonsControllerTest()
            throws Exception {
        mvc.perform(get("/person/find")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "  + jwtTokenUtil.generateAccessToken(currentUser))
                .param("name", databaseData.get(0).getName())
                .param("secondName", databaseData.get(0).getSecondName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(databaseData.get(0).getName())))
                .andExpect(jsonPath("$[0].secondName", is(databaseData.get(0).getSecondName())));
    }

    @Test
    public void createPersonControllerTest()
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(databaseData.get(0));


        mvc.perform(post("/person")
                .content(requestJson)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenUtil.generateAccessToken(currentUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(databaseData.get(0).getName())))
                .andExpect(jsonPath("$.secondName", is(databaseData.get(0).getSecondName())));
    }

    private List<Person> getDatabaseDataSet() {

        PhoneBook phoneBookEntity = new PhoneBook();
        phoneBookEntity.setUser(currentUser);
        PhoneBook testPhoneBook = phoneBookRepository.save(phoneBookEntity);
        Person person1 = newPerson(1, "User1", "SecondName1", 21, testPhoneBook);
        Person person2 = newPerson(2, "User2", "SecondName2", 22, testPhoneBook);
        Person person3 = newPerson(3, "User3", "SecondName3", 23, testPhoneBook);
        Person person4 = newPerson(4, "User4", "SecondName4", 24, testPhoneBook);
        Person dbPerson1 = personRepository.save(person1);
        Person dbPerson2 = personRepository.save(person2);
        Person dbPerson3 = personRepository.save(person3);
        Person dbPerson4 = personRepository.save(person4);
        return Arrays.asList(dbPerson1, dbPerson2, dbPerson3, dbPerson4);
    }

    private Person newPerson(int id, String name, String secondName, int age, PhoneBook phoneBook) {
        Person newPerson = new Person();
        newPerson.setId(id);
        newPerson.setName(name);
        newPerson.setSecondName(secondName);
        newPerson.setAge(age);
        newPerson.setPhones(Collections.emptyList());
        newPerson.setPhoneBook(phoneBook);
        return newPerson;
    }

    private User getCurrentUser() {
        User testUser = new User();
        testUser.setLogin("testUser");
        testUser.setPassword("pass");
        return userService.create(testUser);
    }

//    private Person newPersonWithPhone(int id, String name, String secondName, int age, String phoneNumber) {
//        Person newPerson = newPerson(id, name, secondName, age);
//        Phone phone = new Phone();
//        phone.setPhoneNumber(phoneNumber);
//        newPerson.setPhones(Collections.singletonList(phone));
//        return newPerson;
//    }
}
