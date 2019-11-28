package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.common.exception.PermissionDeniedException;
import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.domain.Shape;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShapeService {
    Shape submit(Shape shape) throws NotFoundException, ValidationException;

    Shape save(Shape shape) throws NotFoundException, ValidationException;

    Page<Shape> findAll(Pageable pageable) throws NotFoundException;

    void deleteById(Integer id);

    Shape findById(Integer id) throws NotFoundException;
}
