package com.example.phonebook.controler;

import com.example.phonebook.controller.PersonController;
import com.example.phonebook.database.entity.Person;
import com.example.phonebook.database.entity.Phone;
import com.example.phonebook.database.entity.User;
import com.example.phonebook.database.repository.UserRepository;
import com.example.phonebook.security.utils.JwtTokenUtil;
import com.example.phonebook.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectWriter;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private UserRepository userRepository;

    private List<Person> databaseData;

    @Before
    public void setUp() {
        databaseData = getDatabaseDataSet();

        Mockito.when(personService.getAll())
                .thenReturn(databaseData);

        Mockito.when(personService.find(any(), any(), any()))
                .thenAnswer(x -> Collections.singletonList(newPersonWithPhone(0, x.getArgument(0), x.getArgument(1), 21, x.getArgument(2))));

        Mockito.when(personService.create(any(Person.class)))
                .thenAnswer(x -> newPerson(
                        ((Person)(x.getArgument(0))).getId(),
                        ((Person)(x.getArgument(0))).getName(),
                        ((Person)(x.getArgument(0))).getSecondName(),
                        ((Person)(x.getArgument(0))).getAge()
                        ));

        Mockito.when(jwtTokenUtil.validate(any(String.class)))
                .thenReturn(true);

        Mockito.when(jwtTokenUtil.getUsername(any(String.class)))
                .thenReturn(getCurrentUser().getLogin());

        Mockito.when(userRepository.findAll())
                .thenReturn(Collections.singletonList(getCurrentUser()));
    }

    @Test
    public void getAllPersonsControllerTest()
            throws Exception {
        mvc.perform(get("/person")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(databaseData.size())))
                .andExpect(jsonPath("$[0].name", is(databaseData.get(0).getName())));
    }

    @Test
    public void findPersonsControllerTest()
            throws Exception {
        mvc.perform(get("/person/find")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .param("name", databaseData.get(0).getName())
                .param("secondName", databaseData.get(0).getSecondName())
                .param("phone", "1234")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(databaseData.get(0).getName())))
                .andExpect(jsonPath("$[0].secondName", is(databaseData.get(0).getSecondName())))
                .andExpect(jsonPath("$[0].phones[0].phoneNumber", is("1234")));
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
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(databaseData.get(0).getName())))
                .andExpect(jsonPath("$.secondName", is(databaseData.get(0).getSecondName())));
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

    private User getCurrentUser() {
        User testUser = new User();
        testUser.setLogin("testUser");
        testUser.setPassword("pass");
        return testUser;
    }

    private Person newPersonWithPhone(int id, String name, String secondName, int age, String phoneNumber) {
        Person newPerson = newPerson(id, name, secondName, age);
        Phone phone = new Phone();
        phone.setPhoneNumber(phoneNumber);
        newPerson.setPhones(Collections.singletonList(phone));
        return newPerson;
    }

}
