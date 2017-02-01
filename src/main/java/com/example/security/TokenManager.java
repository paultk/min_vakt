package com.example.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

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

	public static String lagToken(String username, boolean admin) throws UnsupportedEncodingException {
		LocalDateTime time = LocalDateTime.now();
		//TODO set actual timeouts?
		//Set timeouts here
		time = time.plusHours(12);
//		time = time.plusSeconds(30);
		Date date = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
		String token = JWT.create().withIssuer(issuer).withSubject(username).withClaim("admin", admin).withExpiresAt(date).sign(Algorithm.HMAC256(secret));
//		System.out.println(token);
		return token;
	}
	private static String getUserName(String token) {
		return JWT.decode(token).getSubject();
	}
	public static boolean isAdmin(String token) {
		return JWT.decode(token).getClaim("admin").asBoolean();
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
	/*public static void main(String[] args) {
		try {
			System.out.println(TokenManager.lagToken("admin", true));
			System.out.println(TokenManager.lagToken("ikkeadmin", false));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}