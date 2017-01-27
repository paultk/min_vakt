package com.example.security;

/**
 * Created by Jens on 19-Jan-17.
 */
public class Authentication {
	private String username, password;

	public Authentication() {
	}

	public Authentication(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
