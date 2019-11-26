package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);
}
