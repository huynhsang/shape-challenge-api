package com.sanght.shapechallenge.service;

import com.sanght.shapechallenge.common.exception.NotFoundException;
import com.sanght.shapechallenge.common.exception.PermissionDeniedException;
import com.sanght.shapechallenge.domain.AccessToken;
import com.sanght.shapechallenge.web.vmodel.LoginVM;
import org.springframework.security.core.AuthenticationException;

public interface AccessTokenService {
    AccessToken createToken(LoginVM loginVM) throws AuthenticationException, NotFoundException;
}
