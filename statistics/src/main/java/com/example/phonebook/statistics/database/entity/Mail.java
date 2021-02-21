package com.example.phonebook.statistics.database.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Objects;

public class Mail implements Serializable {

    @Id
    private int id;

    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return id == mail.id && Objects.equals(content, mail.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }
}
