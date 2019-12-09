package com.sanght.shapechallenge.web.controller;

import com.sanght.shapechallenge.common.constant.JWTAccountConstant;
import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.common.exception.ValidationException;
import com.sanght.shapechallenge.common.util.ResponseUtil;
import com.sanght.shapechallenge.domain.AccessToken;
import com.sanght.shapechallenge.domain.User;
import com.sanght.shapechallenge.security.jwt.AuthorityConstant;
import com.sanght.shapechallenge.service.AccessTokenService;
import com.sanght.shapechallenge.service.UserService;
import com.sanght.shapechallenge.web.vmodel.AccountVM;
import com.sanght.shapechallenge.web.vmodel.LoginVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@RestController
public class AccountJWTController {
	private final Logger log = LoggerFactory.getLogger(AccountJWTController.class);

	private final UserService userService;

	private final AccessTokenService accessTokenService;

	public AccountJWTController(AccessTokenService accessTokenService, UserService userService) {
		this.accessTokenService = accessTokenService;
		this.userService = userService;
	}

	@PostMapping(path = "/login", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> authorize(@Valid @RequestBody LoginVM loginVM, HttpServletResponse response) {
		try {
			AccessToken accessToken =  accessTokenService.createToken(loginVM);
			response.addHeader(JWTAccountConstant.AUTHORIZATION_HEADER, "Bearer " + accessToken.getId());
			return ResponseEntity.ok(accessToken);
		} catch (Exception e) {
			log.error("Authentication exception trace: {}", e);
			return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",
					e.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * POST  /register : register the account.
	 *
	 * @param accountVM the account view model
	 * @return the ResponseEntity with status 201 (Created) if the user is registered or 400 (Bad Request) if the username or email is already in use
	 */
	@PostMapping(path = "/register", produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<?> registerAccount(@Valid @RequestBody AccountVM accountVM) {
		HttpHeaders textPlainHeaders = new HttpHeaders();
		textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
		try {
			userService.createUser(accountVM.getUsername().toLowerCase(), accountVM.getPassword(), AuthorityConstant.ROLE_USER);
		} catch (ValidationException e) {
			return new ResponseEntity<>(e.getMessage(), textPlainHeaders, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/account")
	public ResponseEntity<User> getAccount() {
		log.debug("REST request to get current account");
		try {
			User user = userService.getCurrentUser();
			return ResponseUtil.wrapOrNotFound(Optional.ofNullable(user));
		} catch (NotFoundException e) {
			log.error(e.getMessage());
			return ResponseUtil.wrapOrNotFound(Optional.empty());
		}
	}

	@GetMapping("/logout")
	public ResponseEntity<?> logout() {
		log.debug("REST request to logout account");
		try {
			User user = userService.getCurrentUser();
			return ResponseUtil.wrapOrNotFound(Optional.ofNullable(user));
		} catch (NotFoundException e) {
			log.error(e.getMessage());
			return ResponseUtil.wrapOrNotFound(Optional.empty());
		}
	}
}
