package com.example.phonebook.application.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "phones", schema = "phonebook_schema")
public class Phone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "country_code_id", referencedColumnName = "id")
    private CountryCode countryCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "funds")
    private double funds;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "activation_date")
    private LocalDateTime activationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    @JsonIgnoreProperties({"phoneBook", "phones"})
    private Person person;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return id == phone.id && Double.compare(phone.funds, funds) == 0 && Objects.equals(countryCode, phone.countryCode) && Objects.equals(phoneNumber, phone.phoneNumber) && Objects.equals(operatorName, phone.operatorName) && Objects.equals(registrationDate, phone.registrationDate) && Objects.equals(activationDate, phone.activationDate) && Objects.equals(person, phone.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countryCode, phoneNumber, operatorName, funds, registrationDate, activationDate, person);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", areaCode='" + countryCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
