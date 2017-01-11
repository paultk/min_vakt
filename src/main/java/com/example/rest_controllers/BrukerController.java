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
	@RequestMapping("/bruker/{id}")
	public Bruker getBruker(@PathVariable("id") Integer id) {
		SqlQueries query = new SqlQueries();
		Bruker ret = query.getBruker(id);
		System.out.println(ret);
		return query.getBruker(id);
	}
}
