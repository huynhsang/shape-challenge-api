package com.sanght.shapechallenge.web.controller;

import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.common.util.PaginationUtil;
import com.sanght.shapechallenge.domain.User;
import com.sanght.shapechallenge.security.jwt.AuthorityConstant;
import com.sanght.shapechallenge.service.UserService;
import com.sanght.shapechallenge.web.vmodel.AccountVM;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Secured(AuthorityConstant.ROLE_ADMIN)
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private static final String ENTITY_NAME = "user";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Users");
        Page<User> page = userService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * POST  /user/admin : create admin account.
     *
     * @param accountVM the account view model
     * @return the ResponseEntity with status 201 (Created) if the user is registered or 400 (Bad Request) if the username or email is already in use
     */
    @PostMapping(path = "/user/admin", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> createAdmin(@Valid @RequestBody AccountVM accountVM) {
        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        try {
            userService.createUser(accountVM.getUsername().toLowerCase(), accountVM.getPassword(), AuthorityConstant.ROLE_ADMIN);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getMessage(), textPlainHeaders, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
