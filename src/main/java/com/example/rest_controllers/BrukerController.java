package com.example.rest_controllers;

import com.example.database_classes.Bruker;
import com.example.database_classes.Vakt;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jens on 11-Jan-17.
 */
@RestController
public class BrukerController {
	SqlQueries query = new SqlQueries();
	@RequestMapping("/bruker/{id}")
	public Bruker getBruker(@PathVariable("id") Integer id) {
		Bruker ret = query.selectBruker(id);
		System.out.println(ret);
		return query.selectBruker(id);
	}

	//Får ikke testa requestbody uten noen side?
	@RequestMapping(value="/bruker/delete", method=RequestMethod.POST)
	public void deleteBruker(@RequestBody Bruker bruker) {
		query.deleteBruker(bruker.getBrukerId());
	}

	@RequestMapping(value="/bruker/add", method=RequestMethod.POST)
	public void addBruker(@RequestBody Bruker bruker) {
		query.insertBruker(bruker);
	}

	@RequestMapping(value="/bruker/addvakt", method=RequestMethod.POST)
	public void addVaktToBruker(@RequestBody Bruker bruker, Vakt vakt) {
		query.insertVaktBruker(bruker.getBrukerId(), vakt.getVaktId());
	}
}
