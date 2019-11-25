package com.sanght.shapechallenge.repository;

import com.sanght.shapechallenge.domain.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpressionDAO extends JpaRepository<Expression, Integer>{
}
