package com.sanght.shapechallenge.common.constant;

public class JWTAccountConstant {
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final Integer TOKEN_EXP_TIME = 1440; // mins = 1days
	public static final Integer TOKEN_EXP_TIME_WITH_REMEMBER = 10080; // mins = 1 weeks
	public static final String TOKEN_ISSUER = "sanghuynh";
	public static final String TOKEN_SIGNING_KEY = "shape-challenge";

	private JWTAccountConstant() {}
}
