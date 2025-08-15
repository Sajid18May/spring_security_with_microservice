package com.microservice.service;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtService {

	private static final String SECRET_KEY="Secret12345";
	private static final long EXPIRATION_TIME=86400000;
	
	public String validateTokenAndGetSubject(String token) {
		return JWT
				.require(Algorithm.HMAC256(SECRET_KEY))
				.build()
				.verify(token)
				.getSubject();
	}
}
