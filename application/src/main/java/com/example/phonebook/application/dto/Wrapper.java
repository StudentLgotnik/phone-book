package com.example.phonebook.application.dto;

public class Wrapper<D,M> {

    private final D data;
    private final M meta;

    public Wrapper(D data, M meta) {
        this.data = data;
        this.meta = meta;
    }

    public D getData() {
        return data;
    }

    public M getMeta() {
        return meta;
    }

    public static <T,S> Wrapper<T,S> of(T data, S meta) {
        return new Wrapper<>(data, meta);
    }
}
