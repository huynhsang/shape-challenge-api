package com.sanght.shapechallenge.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix="variable.security.jwt")
public class JWTSettings {
	
	//{JwtToken} will expire after this time.
	private Integer tokenExpirationTime;

	private Integer tokenExpirationTimeWithRememberMe;
	
	//Token issuer
	private String tokenIssuer;
	
	//Key is used to sign {JwtToken}.
	private String tokenSigningKey;

	public Integer getTokenExpirationTime() {
		return tokenExpirationTime;
	}

	public void setTokenExpirationTime(Integer tokenExpirationTime) {
		this.tokenExpirationTime = tokenExpirationTime;
	}

	public String getTokenIssuer() {
		return tokenIssuer;
	}

	public void setTokenIssuer(String tokenIssuer) {
		this.tokenIssuer = tokenIssuer;
	}

	public String getTokenSigningKey() {
		return tokenSigningKey;
	}

	public void setTokenSigningKey(String tokenSigningKey) {
		this.tokenSigningKey = tokenSigningKey;
	}

	public Integer getTokenExpirationTimeWithRememberMe() {
		return tokenExpirationTimeWithRememberMe;
	}

	public void setTokenExpirationTimeWithRememberMe(Integer tokenExpirationTimeWithRememberMe) {
		this.tokenExpirationTimeWithRememberMe = tokenExpirationTimeWithRememberMe;
	}
}
