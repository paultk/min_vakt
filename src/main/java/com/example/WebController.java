package com.example;

import com.example.database_classes.Bruker;
import com.example.security.Authentication;
import com.example.security.AuthenticationProvider;
import com.example.security.TokenManager;
import com.example.sql_folder.SqlQueries;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

	/*@RequestMapping(value="/notifications", method = RequestMethod.GET)
	public ModelAndView navigation() {
		System.out.println("notifications yo");
		return new ModelAndView("/app/notification/notification.component.ts");
	}*/

	/*@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login2() {
		return new ModelAndView("/loginpage.html");
	}*/

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping(value="/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestBody Authentication auth) throws Exception {
		System.out.println("POST til login!");
		SqlQueries query = new SqlQueries();
		Bruker brk = query.selectBruker(auth.getUsername());
		AuthenticationProvider cust = new AuthenticationProvider();
		if (brk != null && cust.authenticate(auth)) {
			System.out.println("Autentisert");
			return TokenManager.lagToken(auth.getUsername(), brk.isAdmin());
		}
		throw new IllegalArgumentException("Wrong user name or password");
	}
}
