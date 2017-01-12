package com.example.rest_controllers;

import com.example.database_classes.Bruker;
import com.example.database_classes.BrukerVakt;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jens on 12-Jan-17.
 */
@RestController
public class BrukerVaktController {
	SqlQueries query = new SqlQueries();

	@RequestMapping(value="/brukervakt/alle")
	public BrukerVakt[] getBrukerVakter() {
		return query.selectBrukerVakter();
	}

	@RequestMapping(value="/brukervakt/add", method= RequestMethod.POST)
	public boolean addBrukerVakt(@RequestBody BrukerVakt brukervakt) {
		return query.insertBrukerVakt(brukervakt.getBrukerId(), brukervakt.getVaktId());
	}

	@RequestMapping(value="/brukervakt/delete", method=RequestMethod.POST)
	public boolean deleteBrukerVakt(@RequestBody BrukerVakt brukervakt) {
		return query.deleteBrukerVakt(brukervakt.getBrukerId(), brukervakt.getVaktId());
	}
}
