package com.example.phonebook.service;

import java.util.List;

public interface IService<T> {

    List<T> getAll();

    T get(int id);

    T create(T t);

    T update(T t);

    void delete(int id);
}
