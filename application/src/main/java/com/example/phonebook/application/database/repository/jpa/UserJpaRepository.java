package com.example.phonebook.application.database.repository.jpa;

import com.example.phonebook.application.database.entity.User;
import com.example.phonebook.application.database.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface UserJpaRepository extends JpaRepository<User, Integer>, UserRepository {

}
