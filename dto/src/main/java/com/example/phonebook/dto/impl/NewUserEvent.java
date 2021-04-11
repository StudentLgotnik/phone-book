package com.example.phonebook.dto.impl;

import com.example.phonebook.dto.Event;

import java.util.Objects;

public final class NewUserEvent implements Event {

    private String login;
    private String email;
    private String phone;

    public NewUserEvent() {
    }

    public NewUserEvent(String login, String email, String phone) {
        this.login = login;
        this.email = email;
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewUserEvent that = (NewUserEvent) o;
        return Objects.equals(login, that.login) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, email, phone);
    }
}
