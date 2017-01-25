package com.example.rest_controllers;

import com.example.database_classes.Overtid;
import com.example.security.TokenManager;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;

/**
 * Created by Jens on 12-Jan-17.
 */
@RestController
public class OvertidController {
	SqlQueries query = new SqlQueries();

	@RequestMapping("overtid/{id}")
	public Overtid getOvertid(@PathVariable("id") int id, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.selectOvertid(id);
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping("overtid/alle")
	public Overtid[] getOvertider(@RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.selectOvertider();

		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping(value="/overtid/add", method= RequestMethod.POST)
	public boolean addOvertid(@RequestBody Overtid overtid, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.insertOvertid(overtid);
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping(value="/overtid/delete", method= RequestMethod.POST)
	public boolean deleteOvertid(@RequestBody Overtid overtid, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.deleteOvertid(overtid);
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping(value="/overtid/update", method=RequestMethod.POST)
	public boolean updateOvertid(@RequestBody Overtid overtid, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.updateOvertid(overtid);

		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}
}
