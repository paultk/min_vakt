package com.example.rest_controllers;

import com.example.database_classes.Bruker;
import com.example.sql_folder.SqlQueries;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

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

	//TODO: ikke bruke pathvariable her
	//Får ikke testa requestbody uten noen side
	@RequestMapping("/bruker/delete/{id}")
	public void deleteBruker(@PathVariable ("id") Integer id) {
		query.deleteBruker(id);
	}
}
