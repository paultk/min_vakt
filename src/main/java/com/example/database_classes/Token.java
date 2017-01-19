package com.example.database_classes;

import com.example.security.TokenManager;

/**
 * Created by Jens on 19-Jan-17.
 */
public class Token {
	private String token;

	public Token() {
		try {
			token = TokenManager.lagToken();
		}
		catch (Exception e) {}
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
