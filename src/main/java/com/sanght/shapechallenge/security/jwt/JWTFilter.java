package com.sanght.shapechallenge.security.jwt;

import com.sanght.shapechallenge.common.constant.JWTAccountConstant;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter{

	private final Logger log = LoggerFactory.getLogger(JWTFilter.class);
	private TokenProvider tokenProvider;
	
	public JWTFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}
	
	@Override
	public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			String jwt = resolveToken(httpServletRequest);
			if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
                Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}catch (ExpiredJwtException e) {
			log.info("Security exception for user {} - {}",
	                e.getClaims().getSubject(), e.getMessage());
            log.trace("Security exception trace: {}", e);
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
	
	private String resolveToken(HttpServletRequest request) {
		try {
			String bearerToken = request.getHeader(JWTAccountConstant.AUTHORIZATION_HEADER);
	        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
	            return bearerToken.substring(7, bearerToken.length());
	        }
		}catch (NullPointerException e) {
			return null;
		}
		return null;
	}

}
