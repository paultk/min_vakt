package com.example.rest_controllers;

import com.example.database_classes.Bruker;
import com.example.database_classes.Overtid;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jens on 12-Jan-17.
 */
@RestController
public class OvertidController {
	SqlQueries query = new SqlQueries();

	@RequestMapping("overtid/{id}")
	public Overtid getOvertid(@PathVariable("id") int id) {
		return query.selectOvertid(id);
	}
	@RequestMapping(value="/overtid/add", method= RequestMethod.POST)
	public boolean addOvertid(@RequestBody Overtid overtid) {
		return query.insertOvertid(overtid);
	}
	@RequestMapping(value="/overtid/delete", method= RequestMethod.POST)
	public boolean deleteOvertid(@RequestBody Overtid overtid) {
		return query.deleteOvertid(overtid);
	}
	@RequestMapping(value="/overtid/update", method=RequestMethod.POST)
	public boolean updateOvertid(@RequestBody Overtid overtid) {
		return query.updateOvertid(overtid);
	}
}
