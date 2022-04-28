package com.example.phonebook.application.database.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {

    List<T> findAll();

    List<T> findAll(@Nullable Specification<T> var1);

    Optional<T> findById(ID var1);

    <S extends T> S save(S var1);

    <S extends T> List<S> saveAll(Iterable<S> var1);

    void deleteById(ID var1);

}
