package com.example;

import com.example.security.CustomAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * Created by Jens on 18-Jan-17.
 */
@Controller
public class WebController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/loginpage.html";
	}

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping(value="/login", method = RequestMethod.POST)
	public void login(@RequestBody Authentication auth) {
		System.out.println("POST til login!");
		CustomAuthenticationProvider cust = new CustomAuthenticationProvider();
		cust.authenticate(auth);
	}
}
