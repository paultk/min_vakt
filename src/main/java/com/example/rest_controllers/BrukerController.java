package com.example.rest_controllers;

import com.example.database_classes.*;
import com.example.sql_folder.SqlQueries;
import org.springframework.util.comparator.BooleanComparator;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jens on 11-Jan-17.
 */
@RestController
public class BrukerController {
	SqlQueries query = new SqlQueries();
	@RequestMapping("/bruker/{id}")
	public Bruker getBruker(@PathVariable("id") Integer id) {
		return query.selectBruker(id);
	}

	@RequestMapping("/bruker/alle")
	public Bruker[] getBrukere() {
		return query.selectBrukere();
	}

	@RequestMapping(value="/bruker/delete", method=RequestMethod.POST)
	public boolean deleteBruker(@RequestBody Bruker bruker) {
		return query.deleteBruker(bruker.getBrukerId());
	}

	@RequestMapping(value="/bruker/add", method=RequestMethod.POST)
	public boolean addBruker(@RequestBody Bruker bruker) {
		return query.insertBruker(bruker);
	}

	@RequestMapping(value="/bruker/update", method=RequestMethod.POST)
	public boolean updateBruker(@RequestBody Bruker bruker) {
		return query.updateBruker(bruker);
	}

}
