package com.example.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Jens on 19-Jan-17.
 */
public class TokenManager {
	public static String issuer = "minVakt";
	public static String secret = "hemmelig";
	public static String lagToken() throws UnsupportedEncodingException {
		LocalDateTime time = LocalDateTime.now();
		time = time.plusHours(3);
		Date date = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
		String token = JWT.create().withIssuer(issuer).withExpiresAt(date).sign(Algorithm.HMAC256(secret));
		System.out.println(token);
		return token;
	}
	public static boolean verifiser(String token) {
		try {
			JWTVerifier verify = JWT.require(Algorithm.HMAC256(secret)).withIssuer(issuer).build();
			return verify.verify(token) != null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}