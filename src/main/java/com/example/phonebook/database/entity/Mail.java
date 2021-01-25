package com.example.phonebook.database.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mails", schema = "mail_schema")
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "mail_content")
    private String content;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
        return Id == mail.Id && Objects.equals(content, mail.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, content);
    }
}
