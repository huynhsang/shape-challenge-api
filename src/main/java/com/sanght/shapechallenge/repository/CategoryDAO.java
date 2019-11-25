package com.sanght.shapechallenge.repository;

import com.sanght.shapechallenge.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Integer> {
}