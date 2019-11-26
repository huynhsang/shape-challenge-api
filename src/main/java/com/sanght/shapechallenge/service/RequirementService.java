package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.domain.Requirement;

public interface RequirementService {
    Requirement findOneById(Integer id) throws NotFoundException;
}
