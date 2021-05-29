package com.example.phonebook.userservice.database.entity.mapper;

public interface IMapper<T, E> {

    E toDbEntity(T t);

    T fromDbEntity(E e);

}
