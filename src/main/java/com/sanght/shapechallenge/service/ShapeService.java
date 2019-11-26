package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.domain.Shape;
import com.sanght.shapechallenge.service.dto.ShapeDTO;
import com.sanght.shapechallenge.web.vmodel.ShapeVM;

public interface ShapeService {
    ShapeDTO submit(ShapeVM shapeVM) throws NotFoundException, ValidationException;

    Shape save(ShapeVM shapeVM) throws NotFoundException, ValidationException;
}
