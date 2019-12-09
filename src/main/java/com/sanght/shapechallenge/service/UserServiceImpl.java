package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.common.util.SecurityUtil;
import com.sanght.shapechallenge.domain.Role;
import com.sanght.shapechallenge.domain.User;
import com.sanght.shapechallenge.repository.RoleDAO;
import com.sanght.shapechallenge.repository.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final RoleDAO roleDAO;

    private final UserDAO userDAO;

    private final PasswordEncoder passwordEncoder;

    private final MessageSource messageSource;

    public UserServiceImpl(RoleDAO roleDAO, UserDAO userDAO, PasswordEncoder passwordEncoder, MessageSource messageSource) {
        this.roleDAO = roleDAO;
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
    }

    @Override
    public User createUser(String username, String password, String roleName) throws ValidationException {
        if (!isUsernameExists(username)) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(passwordEncoder.encode(password));
            Set<Role> roles = new HashSet<>();
            Optional<Role> role = roleDAO.findOneByName(roleName);
            if (!role.isPresent()) {
                String errMsg = messageSource.getMessage("err.role.name.notExists", null, LocaleContextHolder.getLocale());
                throw new ValidationException(errMsg);
            }
            roles.add(role.get());
            newUser.setRoles(roles);
            newUser = userDAO.save(newUser);
            log.debug("Created new User: {}", newUser);
            return newUser;
        }
        return null;
    }

    @Override
    public User getCurrentUser() throws NotFoundException {
        return getUserByUsername(SecurityUtil.getCurrentUsername());
    }

    @Override
    public User getUserById(Integer id) throws NotFoundException {
        Optional<User> user = userDAO.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        String errMsg = messageSource.getMessage("err.user.notExists", null, LocaleContextHolder.getLocale());
        throw new NotFoundException(errMsg);
    }

    @Override
    public User getUserByUsername(String username) throws NotFoundException {
        Optional<User> user = userDAO.findOneByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        String errMsg = messageSource.getMessage("err.user.notExists", null, LocaleContextHolder.getLocale());
        throw new NotFoundException(errMsg);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        log.debug("Request to get all users");
        return userDAO.findAll(pageable);
    }

    @Override
    public void delete(Integer id) {
        userDAO.deleteById(id);
        log.debug("Deleted User by id: {}", id);
    }

    private boolean isUsernameExists(String username) throws ValidationException {
        if (userDAO.findOneByUsername(username).isPresent()) {
            String errorMsg = messageSource.getMessage("err.user.username.duplicate", null, LocaleContextHolder.getLocale());
            throw new ValidationException(errorMsg);
        }
        return false;
    }
}
