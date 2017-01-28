package com.example.rest_controllers;

import com.example.database_classes.Bruker;
import com.example.database_classes.Melding;
import com.example.security.TokenManager;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;

/**
 * Created by Knut on 24.01.2017.
 */
@RestController
public class MeldingController {
	private SqlQueries query = new SqlQueries();
	@RequestMapping(value = "/melding/add", method = RequestMethod.POST)
	public boolean addMelding(@RequestBody Melding melding, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.insertMelding(melding);
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping(value = "/melding/{id}")
	public Melding getMelding(@PathVariable("id") int id, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token) && TokenManager.isAdmin(token)) {
			return query.selectMelding(id);
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping(value = "/melding/get", method = RequestMethod.POST)
	public Melding[] getMeldinger(@RequestBody Bruker bruker, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.selectMeldingerToBruker(bruker.getBrukerId());
		}
		else {
			throw new AuthException("Token not authenticated");
		}
//		System.out.println(bruker);
	}

	@RequestMapping(value = "/melding/get/ulest/ant", method = RequestMethod.POST)
	public int getAntMeldinger(@RequestBody Bruker bruker, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			Melding[] ret = query.selectUlestMeldingerToBruker(bruker.getBrukerId());
			if (ret != null) {
				return ret.length;
			}
			else {
				return 0;
			}
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping(value = "/melding/get/ulest", method = RequestMethod.POST)
	public Melding[] getUsettMeldinger(@RequestBody Bruker bruker, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.selectUlestMeldingerToBruker(bruker.getBrukerId());
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping(value = "/melding/sett/{id}", method = RequestMethod.POST)
	public boolean setSett(@PathVariable("id") int id, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.setMeldingSett(id);
		} else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping(value="/melding/delete", method= RequestMethod.POST)
	public boolean deleteMelding(@RequestBody Melding melding) {
		return query.deleteMelding(melding);
	}
}
