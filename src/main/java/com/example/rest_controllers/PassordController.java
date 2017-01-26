package com.example.rest_controllers;

import com.example.database_classes.Passord;
import com.example.security.TokenManager;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;

/**
 * Created by Jens on 13-Jan-17.
 */
@RestController
public class PassordController {
	private SqlQueries query = new SqlQueries();
	@RequestMapping("/passord/{id}")
	public Passord getPassord(@PathVariable ("id") int id, @RequestHeader(value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.selectPassord(id);

		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}
	@RequestMapping("passord/alle")
	public Passord[] getPassordene(@RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.selectPassordene();
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}
	@RequestMapping(value="/passord/add")
	public boolean addPassord(@RequestBody Passord passord, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.insertPassord(passord);
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}
	@RequestMapping(value="/passord/delete")
	public boolean deletePassord(@RequestBody Passord passord, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.deletePassord(passord.getId());
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}
}
