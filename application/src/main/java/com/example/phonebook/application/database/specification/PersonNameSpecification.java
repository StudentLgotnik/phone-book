package com.example.phonebook.application.database.specification;

import com.example.phonebook.application.database.entity.Person;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PersonNameSpecification implements Specification<Person> {

    private final String name;

    public PersonNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (name == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return criteriaBuilder.equal(root.get("name"), this.name);
    }
}
