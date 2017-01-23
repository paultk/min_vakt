package com.example.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.database_classes.Authentication;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Jens on 19-Jan-17.
 */
public class TokenManager {
	private static String issuer = "minVakt";
	private static String secret = "hemmelig"; // lol // superlol

	public static String lagToken(String username) throws UnsupportedEncodingException {
		LocalDateTime time = LocalDateTime.now();
		//Set timeouts here
		time = time.plusHours(3);
//		time = time.plusSeconds(30);
		Date date = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
		String token = JWT.create().withIssuer(issuer).withSubject(username).withExpiresAt(date).sign(Algorithm.HMAC256(secret));
		System.out.println(token);
		return token;
	}
	public static String getUserName(String token) {
		return JWT.decode(token).getSubject();
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

	//Run this to create a token, copy-paste from log
	public static void main(String[] args) {
		try {
			System.out.println(TokenManager.lagToken("admin"));
		}
		catch (Exception e) {}
	}
}