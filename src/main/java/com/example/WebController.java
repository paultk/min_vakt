package com.example;

import com.example.database_classes.Authentication;
import com.example.database_classes.Token;
import com.example.security.CustomAuthenticationProvider;
import com.example.security.TokenManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

/**
 * Created by Jens on 18-Jan-17.
 */
@Controller
public class WebController {
	/*@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(@RequestHeader(value = "token", defaultValue = "null", required = false) String token) {
		System.out.println(token);
		if (token.equals("null")) {
			System.out.println("ingen token");
			return new ModelAndView("/loginpage.html");
		}
		else if (TokenManager.verifiser(token)) {
			System.out.println("Token verifisert");
			return new ModelAndView("/indexpage.html");
		}
		else {
			return new ModelAndView("/loginpage.html");
		}
	}*/

	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView home() {
		return new ModelAndView("/index.html");
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login2() {
		return new ModelAndView("/loginpage.html");
	}

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping(value="/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestBody Authentication auth) throws Exception {
		System.out.println("POST til login!");
		CustomAuthenticationProvider cust = new CustomAuthenticationProvider();
		if (cust.auth(auth)) {
			System.out.println("Autentisert");
			return TokenManager.lagToken(auth.getUsername());
		}
		throw new IllegalArgumentException("Wrong user name or password");
	}
}
