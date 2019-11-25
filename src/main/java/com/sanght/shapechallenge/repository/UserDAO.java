package com.sanght.shapechallenge.repository;

import com.sanght.shapechallenge.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Integer> {
    Optional<User> findOneByUsername(String username);
}
