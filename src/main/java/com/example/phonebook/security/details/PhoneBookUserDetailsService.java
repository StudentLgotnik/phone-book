package com.example.phonebook.security.details;

import com.example.phonebook.database.entity.User;
import com.example.phonebook.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class PhoneBookUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findAll().stream().filter(u -> u.getLogin().equals(s)).findFirst().orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new PhoneBookUserDetails(user);
    }
}
