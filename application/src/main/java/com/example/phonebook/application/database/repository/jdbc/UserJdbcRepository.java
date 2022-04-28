package com.example.phonebook.application.database.repository.jdbc;

import com.example.phonebook.application.database.entity.User;
import com.example.phonebook.application.database.repository.UserRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserJdbcRepository extends JdbcRepository<User, Integer> implements UserRepository {

    private static final String TABLE_NAME = "users";
    private static final String DATABASE_SCHEMA = "phonebook_schema";
    private static final List<String> ENTITY_PROPERTIES = Arrays.asList("login", "email", "phone", "password");
    private static final RowMapper<User> PROPERTY_ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    protected UserJdbcRepository() {
        super(PROPERTY_ROW_MAPPER, TABLE_NAME, DATABASE_SCHEMA, ENTITY_PROPERTIES);
    }

}
