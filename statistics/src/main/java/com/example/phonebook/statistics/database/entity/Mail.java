package com.example.phonebook.statistics.database.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Mail implements Serializable {


    private String content;

    LocalDateTime createdOn;

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Mail() {
    }

    public Mail(String content) {
        this.content = content;
        createdOn = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return createdOn == mail.createdOn && Objects.equals(content, mail.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdOn, content);
    }
}
