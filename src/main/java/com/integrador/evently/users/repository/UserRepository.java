package com.integrador.evently.users.repository;

import com.integrador.evently.users.model.User;
import com.integrador.evently.users.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByType(UserType userType);
}