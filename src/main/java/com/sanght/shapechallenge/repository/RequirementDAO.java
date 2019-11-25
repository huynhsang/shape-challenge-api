package com.sanght.shapechallenge.repository;

import com.sanght.shapechallenge.domain.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementDAO extends JpaRepository<Requirement, Integer> {
}
