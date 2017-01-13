package com.example.rest_controllers;

import com.example.database_classes.Passord;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jens on 13-Jan-17.
 */
@RestController
public class PassordController {
	private SqlQueries query = new SqlQueries();
	@RequestMapping("/passord/{id}")
	public Passord getPassord(@PathVariable ("id") int id) {
		return query.selectPassord(id);
	}
	@RequestMapping("passord/alle")
	public Passord[] getPassordene() {
		return query.selectPassordene();
	}
	@RequestMapping(value="/passord/add")
	public boolean addPassord(@RequestBody Passord passord) {
		return query.insertPassord(passord);
	}
	@RequestMapping(value="/passord/delete")
	public boolean deletePassord(@RequestBody Passord passord) {
		return query.deletePassord(passord.getId());
	}
}
