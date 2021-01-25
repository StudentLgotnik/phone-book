package com.example.phonebook.database.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "area_codes", schema = "phonebook_schema")
public class CountryCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code")
    private int code;

    @Column(name = "country")
    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryCode that = (CountryCode) o;
        return id == that.id && code == that.code && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, country);
    }

    @Override
    public String toString() {
        return "CountryCode{" +
                "id=" + id +
                ", code=" + code +
                ", country='" + country + '\'' +
                '}';
    }
}
