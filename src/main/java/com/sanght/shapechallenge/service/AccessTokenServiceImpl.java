package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.common.constant.JWTAccountConstant;
import com.sanght.shapechallenge.common.constant.RoleName;
import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.common.exception.PermissionDeniedException;
import com.sanght.shapechallenge.domain.AccessToken;
import com.sanght.shapechallenge.domain.Role;
import com.sanght.shapechallenge.domain.User;
import com.sanght.shapechallenge.repository.AccessTokenDAO;
import com.sanght.shapechallenge.security.jwt.JWTToken;
import com.sanght.shapechallenge.security.jwt.TokenProvider;
import com.sanght.shapechallenge.web.vmodel.LoginVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {
    private final Logger log = LoggerFactory.getLogger(AccessTokenServiceImpl.class);

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final AccessTokenDAO accessTokenDAO;

    private final UserService userService;

    private final MessageSource messageSource;

    public AccessTokenServiceImpl(AccessTokenDAO accessTokenDAO, AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserService userService, MessageSource messageSource) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.accessTokenDAO = accessTokenDAO;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @Override
    public AccessToken createToken(LoginVM loginVM) throws AuthenticationException, NotFoundException {
        User user = userService.getUserByUsername(loginVM.getUsername());
        return createAccessToken(user, loginVM);
    }

    @Override
    public AccessToken createAdminToken(LoginVM loginVM) throws AuthenticationException, NotFoundException, PermissionDeniedException {
        User user = userService.getUserByUsername(loginVM.getUsername());
        if (isAdmin(user)) {
            return createAccessToken(user, loginVM);
        }
        String errorMsg = messageSource.getMessage("err.notAllow", null, LocaleContextHolder.getLocale());
        throw new PermissionDeniedException(errorMsg);
    }

    private AccessToken createAccessToken(User user, LoginVM loginVM) throws AuthenticationException, NotFoundException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = loginVM.isRememberMe();
        Integer expirationTime = rememberMe ? JWTAccountConstant.TOKEN_EXP_TIME_WITH_REMEMBER : JWTAccountConstant.TOKEN_EXP_TIME;
        JWTToken jwt = tokenProvider.createToken(authentication, rememberMe);
        AccessToken accessToken = new AccessToken();
        accessToken.setId(jwt.getIdToken());
        accessToken.setUser(user);
        accessToken.setTtl(expirationTime);
        accessToken = accessTokenDAO.save(accessToken);
        log.debug("Created new accessToken: {}", accessToken);
        return accessToken;
    }

    private boolean isAdmin(User user) {
        Optional<Role> adminRole = user.getRoles()
                .stream()
                .filter(role -> role.getName().equals(RoleName.ROLE_ADMIN.name()))
                .findFirst();
        return adminRole.isPresent();
    }
}
