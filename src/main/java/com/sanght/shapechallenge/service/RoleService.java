package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.domain.Role;

public interface RoleService {
    Role findOrCreate(String roleName) throws ValidationException;
}