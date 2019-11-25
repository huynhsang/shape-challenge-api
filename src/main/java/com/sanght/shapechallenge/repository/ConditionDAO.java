package com.sanght.shapechallenge.repository;

import com.sanght.shapechallenge.domain.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConditionDAO extends JpaRepository<Condition, Integer> {
}