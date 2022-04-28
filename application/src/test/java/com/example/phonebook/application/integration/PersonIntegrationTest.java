package com.example.phonebook.application.integration;

import com.example.phonebook.application.PhonebookApplication;
import com.example.phonebook.application.database.configuration.PostgresqlContainer;
import com.example.phonebook.application.database.entity.*;
import com.example.phonebook.application.database.repository.jpa.CountryCodeJpaRepository;
import com.example.phonebook.application.database.repository.jpa.PersonJpaRepository;
import com.example.phonebook.application.database.repository.PhoneBookRepository;
import com.example.phonebook.application.database.repository.PhoneRepository;
import com.example.phonebook.application.security.utils.JwtTokenUtil;
import com.example.phonebook.application.service.UserService;
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
    private CountryCodeJpaRepository countryCodeRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private PersonJpaRepository personRepository;

    @Autowired
    private PhoneBookRepository phoneBookRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static List<Person> databaseData;

    private static User currentUser;

    @Before
    public void setUp() {
        if (currentUser == null) {
            currentUser = getCurrentUser();
        }

        if (databaseData == null) {
            databaseData = getDatabaseDataSet();
        }
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
        dbPerson1.setPhoneBook(testPhoneBook);
        dbPerson2.setPhoneBook(testPhoneBook);
        dbPerson3.setPhoneBook(testPhoneBook);
        dbPerson4.setPhoneBook(testPhoneBook);
        List<CountryCode>  countryCodes = countryCodeRepository.findAll();

        Phone phone1 = new Phone();
        phone1.setPhoneNumber("123");
        phone1.setPerson(dbPerson1);
        phone1.setCountryCode(countryCodes.get(0));
        phoneRepository.save(phone1);

        Phone phone2 = new Phone();
        phone2.setPhoneNumber("123");
        phone2.setPerson(dbPerson2);
        phone2.setCountryCode(countryCodes.get(0));
        phoneRepository.save(phone2);

        Phone phone3 = new Phone();
        phone3.setPhoneNumber("123");
        phone3.setPerson(dbPerson3);
        phone3.setCountryCode(countryCodes.get(0));
        phoneRepository.save(phone3);

        Phone phone4 = new Phone();
        phone4.setPhoneNumber("123");
        phone4.setPerson(dbPerson4);
        phone4.setCountryCode(countryCodes.get(0));
        phoneRepository.save(phone4);

        return Arrays.asList(dbPerson1, dbPerson2, dbPerson3, dbPerson4);
    }

    private Person newPerson(int id, String name, String secondName, int age, PhoneBook phoneBook) {
        Person newPerson = new Person();
        newPerson.setId(id);
        newPerson.setName(name);
        newPerson.setSecondName(secondName);
        newPerson.setAge(age);
//        newPerson.setPhones(Collections.emptyList());
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
