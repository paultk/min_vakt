package com.example.rest_controllers;

import com.example.database_classes.Bruker;
import com.example.database_classes.Vakt;
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

	@RequestMapping(value="/bruker/delete", method=RequestMethod.POST)
	public boolean deleteBruker(@RequestBody Bruker bruker) {
		return query.deleteBruker(bruker.getBrukerId());
	}

	@RequestMapping(value="/bruker/add", method=RequestMethod.POST)
	public boolean addBruker(@RequestBody Bruker bruker) {
		return query.insertBruker(bruker);
	}


	//TODO: teste disse når man kan hente vakter som JSON
	@RequestMapping(value="/bruker/addvakt", method=RequestMethod.POST)
	public boolean addBrukerVakt(@RequestBody Bruker bruker, @RequestBody Vakt vakt) {
		return query.insertVaktBruker(bruker.getBrukerId(), vakt.getVaktId());
	}

	@RequestMapping(value="/bruker/deletevakt", method=RequestMethod.POST)
	public boolean deleteBrukerVakt(@RequestBody Bruker bruker, @RequestBody Vakt vakt) {
		return query.deleteVaktBruker(bruker.getBrukerId(), vakt.getVaktId());
	}
}
