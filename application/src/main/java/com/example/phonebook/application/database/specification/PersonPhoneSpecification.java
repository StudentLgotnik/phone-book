package com.example.phonebook.application.database.specification;

import com.example.phonebook.application.database.entity.Person;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class PersonPhoneSpecification implements Specification<Person> {

    private final String phone;

    public PersonPhoneSpecification(String phone) {
        this.phone = phone;
    }

    @Override
    public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (phone == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        Join join = root.join("phones");
        return criteriaBuilder.equal(join.get("phoneNumber"), this.phone);
    }
}
