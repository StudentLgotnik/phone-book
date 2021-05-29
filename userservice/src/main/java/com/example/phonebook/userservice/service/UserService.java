package com.example.phonebook.userservice.service;

import com.example.phonebook.userservice.database.entity.User;
import com.example.phonebook.userservice.database.entity.mapper.impl.UserMapper;
import com.example.phonebook.userservice.database.repository.UserRepository;
import com.example.phonebook.event.NewUser;
import com.example.phonebook.userservice.dto.UserDto;
import com.example.phonebook.userservice.security.details.PhoneBookUserDetails;
import com.example.phonebook.userservice.security.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements IUserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;

    private KafkaTemplate<String, NewUser.NewUserEvent> eventKafkaTemplate;
    private final String topicName = "phonebook";

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    public void setEventKafkaTemplate(KafkaTemplate<String, NewUser.NewUserEvent> eventKafkaTemplate) {
        this.eventKafkaTemplate = eventKafkaTemplate;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = userMapper.toDbEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User created = userRepository.save(user);

        NewUser.NewUserEvent newUserEvent = NewUser.NewUserEvent.newBuilder()
                .setLogin(created.getLogin())
                .setEmail(created.getEmail())
                .setPhone(created.getPhone())
                .build();

        eventKafkaTemplate.send(topicName, newUserEvent);

        return userMapper.fromDbEntity(created);
    }

    @Override
    public AuthenticationResult authenticate(UserDto userDto) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    userDto.getLogin(), userDto.getPassword()
                            )
                    );

            User user = ((PhoneBookUserDetails) authenticate.getPrincipal()).getUser();

            return AuthenticationResult.Success
                    .withToken(jwtTokenUtil.generateAccessToken(user))
                    .withMessage("User " + userDto.getLogin() + " successfully authenticated");

        } catch (BadCredentialsException ex) {
            return AuthenticationResult.Failure
                    .withMessage("Login or password is incorrect");
        }
    }
}
