package com.sanght.shapechallenge.repository;

import com.sanght.shapechallenge.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDAO extends JpaRepository<Role, Integer> {
    Optional<Role> findOneByName(String name);
}
