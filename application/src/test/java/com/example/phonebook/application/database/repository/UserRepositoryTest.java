package com.example.phonebook.application.database.repository;


import com.example.phonebook.application.database.configuration.PostgresqlContainer;
import com.example.phonebook.application.database.entity.User;
import com.example.phonebook.application.database.repository.jdbc.UserJdbcRepository;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = PostgresqlContainer.getNewInstance();

    @Autowired
    private UserJdbcRepository userRepository;

    @After
    public void cleanUp() {
        userRepository.findAll().forEach(u -> userRepository.deleteById(u.getId()));
    }

    @Test
    public void saveTest() {
        // given
        User user = new User();
        user.setLogin("Alex");
        user.setEmail("alex322@gemail.com");
        user.setPassword("123");
        user.setPhone("0123456789");

        User saved = userRepository.save(user);

        // then
        assertThat(saved.getId()).isNotEqualTo(0);
        assertThat(saved.getLogin()).isEqualTo(user.getLogin());
        assertThat(saved.getEmail()).isEqualTo(user.getEmail());
        assertThat(saved.getPassword()).isEqualTo(user.getPassword());
        assertThat(saved.getPhone()).isEqualTo(user.getPhone());
    }

    @Test
    public void saveAllTest() {
        // given
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            User user = new User();
            user.setLogin("Alex" + i);
            user.setEmail("alex322@gemail.com" + i);
            user.setPassword("123" + i);
            user.setPhone("0123456789" + i);

            users.add(user);
        }

        userRepository.saveAll(users);

        List<User> found = userRepository.findAll();

        // then
        assertEquals(users.size(), found.size());
    }

    @Test
    public void updateAllTest() {
        // given
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            User user = new User();
            user.setLogin("Alex" + i);
            user.setEmail("alex322@gemail.com" + i);
            user.setPassword("123" + i);
            user.setPhone("0123456789" + i);

            users.add(user);
        }

        userRepository.saveAll(users);

        List<User> found = userRepository.findAll();

        assertTrue(users.size() == found.size());

        found.forEach(u -> u.setPhone(u.getPhone() + 0));

        userRepository.saveAll(found);

        List<User> updated = userRepository.findAll();

        // then
        assertTrue(updated.size() == found.size() && updated.containsAll(found) && found.containsAll(updated));
    }

    @Test
    public void findAllTest() {
        // given
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            User user = new User();
            user.setLogin("Alex" + i);
            user.setEmail("alex322@gemail.com" + i);
            user.setPassword("123" + i);
            user.setPhone("0123456789" + i);

            User saved = userRepository.save(user);
            users.add(saved);
        }

        List<User> found = userRepository.findAll();

        // then
        assertTrue(users.size() == found.size() && users.containsAll(found) && found.containsAll(users));
    }

    @Test
    public void findByIdTest() {
        // given
        User user = new User();
        user.setLogin("Alex");
        user.setEmail("alex322@gemail.com");
        user.setPassword("123");
        user.setPhone("0123456789");

        User saved = userRepository.save(user);

        User found = userRepository.findById(saved.getId()).get();

        // then
        assertThat(found).isEqualTo(saved);
    }

    @Test
    public void updateTest() {
        // given
        User user = new User();
        user.setLogin("Alex");
        user.setEmail("alex322@gemail.com");
        user.setPassword("123");
        user.setPhone("0123456789");

        User saved = userRepository.save(user);

        assertThat(saved.getId()).isNotEqualTo(0);
        assertThat(saved.getLogin()).isEqualTo(user.getLogin());
        assertThat(saved.getEmail()).isEqualTo(user.getEmail());
        assertThat(saved.getPassword()).isEqualTo(user.getPassword());
        assertThat(saved.getPhone()).isEqualTo(user.getPhone());

        saved.setLogin("AlexUpd");
        saved.setEmail("alex322Upd@gemail.com");
        saved.setPassword("123Upd");
        saved.setPhone("0123456789Upd");

        User updated = userRepository.save(saved);

        // then
        assertThat(updated).isEqualTo(saved);
    }

    @Test
    public void deleteByIdTest() {
        // given
        User user = new User();
        user.setLogin("Alex");
        user.setEmail("alex322@gemail.com");
        user.setPassword("123");
        user.setPhone("0123456789");

        User saved = userRepository.save(user);

        int savedSize = userRepository.findAll().size();

        // when
        userRepository.deleteById(saved.getId());

        int afterDeleteSize = userRepository.findAll().size();

        // then
        assertThat(savedSize).isEqualTo(afterDeleteSize + 1);
    }
}
