package com.sanght.shapechallenge.repository;

import com.sanght.shapechallenge.domain.Shape;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShapeDAO extends JpaRepository<Shape, Integer> {
}
