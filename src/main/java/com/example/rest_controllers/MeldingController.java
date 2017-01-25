package com.example.rest_controllers;

import com.example.database_classes.Bruker;
import com.example.database_classes.Melding;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Knut on 24.01.2017.
 */
@RestController
public class MeldingController {
	private SqlQueries query = new SqlQueries();
	@RequestMapping(value = "/melding/add", method = RequestMethod.POST)
	public boolean addMelding(@RequestBody Melding melding) {
		return query.insertMelding(melding);
	}

	@RequestMapping(value = "/melding/{id}")
	public Melding getMelding(@PathVariable("id") int id) {
		return query.selectMelding(id);
	}

	@RequestMapping(value = "/melding/get", method = RequestMethod.POST)
	public Melding[] getMeldinger(@RequestBody Bruker bruker) {
//		System.out.println(bruker);
		return query.selectMeldingerToBruker(bruker.getBrukerId());
	}

	@RequestMapping(value = "/melding/get/ulest/ant", method = RequestMethod.POST)
	public int getAntMeldinger(@RequestBody Bruker bruker) {
		Melding[] ret = query.selectUlestMeldingerToBruker(bruker.getBrukerId());
		if (ret != null) {
			return ret.length;
		}
		else {
			return 0;
		}
	}

	@RequestMapping(value = "/melding/get/ulest", method = RequestMethod.POST)
	public Melding[] getUsettMeldinger(@RequestBody Bruker bruker) {
		return query.selectUlestMeldingerToBruker(bruker.getBrukerId());
	}

	@RequestMapping(value = "/melding/sett/{id}", method = RequestMethod.POST)
	public boolean setSett(@PathVariable("id") int id) {
		return query.setMeldingSett(id);
	}
}
