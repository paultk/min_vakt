package com.example.rest_controllers;

import com.example.database_classes.BrukerVakt;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jens on 12-Jan-17.
 */
@RestController
public class BrukerVaktController {
	SqlQueries query = new SqlQueries();
	@RequestMapping(value="/brukervakt/addvakt", method= RequestMethod.POST)
	public boolean addBrukerVakt(@RequestBody BrukerVakt brukervakt) {
		return query.insertVaktBruker(brukervakt.getBrukerId(), brukervakt.getVaktId());
	}

	@RequestMapping(value="/brukervakt/deletevakt", method=RequestMethod.POST)
	public boolean deleteBrukerVakt(@RequestBody BrukerVakt brukervakt) {
		return query.deleteVaktBruker(brukervakt.getBrukerId(), brukervakt.getVaktId());
	}
}
