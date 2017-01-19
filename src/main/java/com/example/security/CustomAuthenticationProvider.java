package com.example.security;

import com.example.database_classes.Authentication;
import com.example.database_classes.Bruker;
import com.example.database_classes.Passord;
import com.example.sql_folder.SqlQueries;

/**
 * Created by Jens on 17-Jan-17.
 */
public class CustomAuthenticationProvider {
	public boolean auth(Authentication auth) {
		SqlQueries query = new SqlQueries();
		String username = auth.getUsername();
		String pass = auth.getPassword();
		System.out.println(username + " " + pass);
		Bruker bruker = query.selectBruker(username);
		Passord passord = query.selectPassord(bruker);
		if (PasswordSystemManager.checkPasswordMatch(pass, passord)) {
			return true;
		}
		System.out.println("Not authenticated");
		return false;
	}
}
