package com.LoginSignUp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LoginSignUp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

    User findByUsername(String username);

    Page<User> findAll(Specification<User> searchUsers, Pageable pageable);

    Page<User> findAll(Pageable pageable);
    
}
