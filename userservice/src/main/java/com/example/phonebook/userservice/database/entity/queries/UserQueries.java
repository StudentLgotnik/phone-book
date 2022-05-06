package com.example.phonebook.userservice.database.entity.queries;

import com.example.phonebook.userservice.database.entity.User;
import com.example.phonebook.userservice.database.entity.mapper.UserRowMapper;
import com.example.phonebook.userservice.database.entity.mapper.impl.UserMapper;
import com.example.phonebook.userservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@PropertySource("classpath:db/queries/user.properties")
public class UserQueries {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final UserRowMapper userRowMapper;
    private final UserMapper userMapper;

    @Value("${updateLogin}")
    private String updateLogin;

    @Value("${selectById}")
    private String selectById;

    @Value("${deletePhoneByUserId}")
    private String deletePhoneByUserId;

    @Value("${deletePersonByUserId}")
    private String deletePersonByUserId;

    @Value("${deletePhoneBookByUserId}")
    private String deletePhoneBookByUserId;

    @Value("${deleteUserById}")
    private String deleteUserById;

    @Autowired
    public UserQueries(NamedParameterJdbcTemplate namedJdbcTemplate, UserRowMapper userRowMapper, UserMapper userMapper) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.userRowMapper = userRowMapper;
        this.userMapper = userMapper;
    }

    public UserDto getById(Integer id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        User user = namedJdbcTemplate.queryForObject(selectById, namedParameters, userRowMapper);
        return userMapper.fromDbEntity(user);
    }

    public UserDto updateLogin(Integer id, String newLogin) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("login", newLogin);
        namedJdbcTemplate.update(updateLogin, namedParameters);
        return getById(id);
    }

    @Transactional
    public void deleteUser(Integer id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        namedJdbcTemplate.update(deletePhoneByUserId, namedParameters);
        namedJdbcTemplate.update(deletePersonByUserId, namedParameters);
        namedJdbcTemplate.update(deletePhoneBookByUserId, namedParameters);
        namedJdbcTemplate.update(deleteUserById, namedParameters);
    }

}


