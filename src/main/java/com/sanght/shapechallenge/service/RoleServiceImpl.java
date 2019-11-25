package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.common.constant.RoleName;
import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.domain.Role;
import com.sanght.shapechallenge.repository.RoleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleDAO roleDAO;

    final
    MessageSource messageSource;

    public RoleServiceImpl(RoleDAO roleDAO, MessageSource messageSource) {
        this.roleDAO = roleDAO;
        this.messageSource = messageSource;
    }

    @Override
    public Role findOrCreate(String roleName) throws ValidationException {
        if (!RoleName.contains(roleName)) {
            String errMsg = messageSource.getMessage("err.role.name.notExists", null, LocaleContextHolder.getLocale());
            throw new ValidationException(errMsg);
        }
        Optional<Role> role = roleDAO.findOneByName(roleName);
        if (role.isPresent()) {
            return role.get();
        }
        Role newRole = new Role();
        newRole.setName(roleName);
        newRole = roleDAO.save(newRole);
        log.debug("Created new Role: {}", newRole);
        return newRole;
    }
}
