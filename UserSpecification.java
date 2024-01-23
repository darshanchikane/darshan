package com.LoginSignUp.specification;

import org.springframework.data.jpa.domain.Specification;

import com.LoginSignUp.model.User;

public class UserSpecification {
    
    public static Specification<User> searchUsers(String search) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + search.toLowerCase() + "%"));
    }
}
