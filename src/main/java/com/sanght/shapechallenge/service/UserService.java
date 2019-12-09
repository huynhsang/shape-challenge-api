package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User createUser(String username, String password, String roleName) throws ValidationException;

    User getCurrentUser() throws NotFoundException;

    /**
     *  Get the user by id.
     *
     *  @param id the user id
     *  @return the entity
     */
    User getUserById(Integer id) throws NotFoundException;

    /**
     *  Get the user by username.
     *
     *  @param username the user name
     *  @return the entity
     */
    User getUserByUsername(String username) throws NotFoundException;

    Page<User> findAll(Pageable pageable);

    /**
     *  Delete the "id" user.
     *
     *  @param id the id of the entity
     */
    void delete(Integer id);
}
