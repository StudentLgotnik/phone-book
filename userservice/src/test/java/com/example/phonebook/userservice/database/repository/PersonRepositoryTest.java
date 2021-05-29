package com.example.phonebook.userservice.database.repository;

import com.example.phonebook.userservice.database.configuration.PostgresqlContainer;
import com.example.phonebook.userservice.database.entity.User;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgresqlContainer.getNewInstance();

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        User user = new User();
        user.setLogin("Alex");
        user.setPassword("123");

        userRepository.save(user);

        // when
        List<User> foundPersons = userRepository.findAll();

        // then
        assertThat(foundPersons.size())
                .isEqualTo(1);
    }
}
