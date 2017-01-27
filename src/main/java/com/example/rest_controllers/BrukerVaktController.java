package com.example.rest_controllers;

import com.example.database_classes.BrukerVakt;
import com.example.security.TokenManager;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;

/**
 * Created by Jens on 12-Jan-17.
 */

@RestController
public class BrukerVaktController {
	private SqlQueries query = new SqlQueries();

	@RequestMapping(value="/brukervakt/alle")
	public BrukerVakt[] getBrukerVakter(@RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.selectBrukerVakter();
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping(value="/brukervakt/add", method= RequestMethod.POST)
	public boolean addBrukerVakt(@RequestBody BrukerVakt brukervakt, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.insertBrukerVakt(brukervakt.getBrukerId(), brukervakt.getVaktId());
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping(value="/brukervakt/delete", method=RequestMethod.POST)
	public boolean deleteBrukerVakt(@RequestBody BrukerVakt brukervakt, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.deleteBrukerVakt(brukervakt.getBrukerId(), brukervakt.getVaktId());
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}
}
