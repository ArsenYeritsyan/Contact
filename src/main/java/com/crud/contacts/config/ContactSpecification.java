package com.crud.contacts.config;

import com.crud.contacts.model.Contact;
import com.crud.contacts.model.Email;
import com.crud.contacts.model.PhoneNumber;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ContactSpecification {
    public static Specification<Contact> phoneNumberContains(String phoneNumber) {
        return (root, query, criteriaBuilder) -> {
            Join<Contact, PhoneNumber> phoneNumberJoin = root.join("phoneNumbers", JoinType.INNER);
            return criteriaBuilder.like(phoneNumberJoin.get("phoneNumber"), "%" + phoneNumber + "%");
        };
    }

    public static Specification<Contact> emailContains(String email) {
        return (root, query, criteriaBuilder) -> {
            Join<Contact, Email> emailJoin = root.join("emails", JoinType.INNER);
            return criteriaBuilder.like(emailJoin.get("email"), "%" + email + "%");
        };
    }

    public static Specification<Contact> firstNameContains(String firstName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<Contact> lastNameContains(String lastName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%");
    }
}
