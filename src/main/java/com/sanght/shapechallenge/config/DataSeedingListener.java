package com.sanght.shapechallenge.config;

import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.security.jwt.AuthorityConstant;
import com.sanght.shapechallenge.service.RoleService;
import com.sanght.shapechallenge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger log = LoggerFactory.getLogger(DataSeedingListener.class);

    private final UserService userService;

    private final RoleService roleService;

    private final RoleService categoryService;

    public DataSeedingListener(UserService userService, RoleService roleService, RoleService categoryService) {
        this.userService = userService;
        this.roleService = roleService;
        this.categoryService = categoryService;
    }

    @Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
	    try {
		    initRoles();
		    initAdmin();
	    } catch (ValidationException e) {
	        log.error(e.getMessage());
	    }
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    private void initRoles() throws ValidationException {
        for (String name: AuthorityConstant.ROLE_NAMES) {
            roleService.findOrCreate(name);
        }
    }

    private void initAdmin() throws ValidationException {
        userService.createUser("admin", "admin", AuthorityConstant.ROLE_ADMIN);
    }

    private void initCategories() {

    }
}