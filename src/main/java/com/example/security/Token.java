package com.example.security;

import com.example.security.TokenManager;

/**
 * Created by Jens on 19-Jan-17.
 */
class Token {
	private String token;

	public Token(){}

	public Token(String username) throws Exception {
		token = TokenManager.lagToken(username);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
