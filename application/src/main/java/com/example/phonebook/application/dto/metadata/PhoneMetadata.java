package com.example.phonebook.application.dto.metadata;

public class PhoneMetadata {

    private Integer phonesCount;

    public PhoneMetadata(Integer phonesCount) {
        this.phonesCount = phonesCount;
    }

    public Integer getPhonesCount() {
        return phonesCount;
    }

    public void setPhonesCount(Integer phonesCount) {
        this.phonesCount = phonesCount;
    }
}
