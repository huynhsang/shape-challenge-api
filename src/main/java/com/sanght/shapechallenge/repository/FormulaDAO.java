package com.sanght.shapechallenge.repository;

import com.sanght.shapechallenge.domain.Formula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormulaDAO extends JpaRepository<Formula, Integer> {
}
