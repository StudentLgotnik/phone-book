package com.example.phonebook.database.specification;

import com.example.phonebook.database.entity.Person;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PersonSecondNameSpecification implements Specification<Person> {

    private final String secondName;

    public PersonSecondNameSpecification(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (secondName == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return criteriaBuilder.equal(root.get("secondName"), this.secondName);
    }
}
