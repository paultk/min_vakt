package com.example.security;

import com.example.database_classes.Authentication;
import com.example.database_classes.Bruker;
import com.example.database_classes.Passord;
import com.example.sql_folder.SqlQueries;
/*import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;*/

import java.util.ArrayList;

/**
 * Created by Jens on 17-Jan-17.
 */
/*public class CustomAuthenticationProvider implements AuthenticationProvider {
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("Authenticating");
		SqlQueries query = new SqlQueries();
		String username = authentication.getName();
		String pass = (String)authentication.getCredentials();
		System.out.println(username + " " + pass);
		Bruker bruker = query.selectBruker(username);
		Passord passord = query.selectPassord(bruker);
		if (PasswordEncoderGenerator.checkPasswordMatch(pass, passord)) {
			if (bruker.isAdmin()) {
				SimpleGrantedAuthority auth = new SimpleGrantedAuthority("ADMIN");
				ArrayList<SimpleGrantedAuthority> authority = new ArrayList<>();
				authority.add(auth);
				System.out.println("Authenticated!");
				return new UsernamePasswordAuthenticationToken(username, pass, authority);
			}
			else if (!bruker.isAdmin()) {
				SimpleGrantedAuthority auth = new SimpleGrantedAuthority("USER");
				ArrayList<SimpleGrantedAuthority> authority = new ArrayList<>();
				authority.add(auth);
				System.out.println("Authenticated!");
				return new UsernamePasswordAuthenticationToken(username, pass, authority);
			}
		}
		System.out.println("Not authenticated");
		throw new BadCredentialsException("Invalid user name or password");
	}
	public boolean supports(Class<?> arg0) {
		return true;
	}
}*/
public class CustomAuthenticationProvider {
	public boolean auth(Authentication auth) {
		SqlQueries query = new SqlQueries();
		String username = auth.getUsername();
		String pass = auth.getPassword();
		System.out.println(username + " " + pass);
		Bruker bruker = query.selectBruker(username);
		Passord passord = query.selectPassord(bruker);
		if (PasswordEncoderGenerator.checkPasswordMatch(pass, passord)) {
			return true;
		}
		System.out.println("Not authenticated");
		return false;
	}
}
