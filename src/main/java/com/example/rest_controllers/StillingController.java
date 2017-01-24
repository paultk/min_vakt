package com.example.rest_controllers;

import com.example.database_classes.Stilling;
import com.example.sql_folder.SqlQueries;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * Created by Håkon on 11.01.2017.
 */

@RestController
public class StillingController {
    SqlQueries query = new SqlQueries();
    @RequestMapping("/stilling/{beskrivelse}")
    public Stilling getStilling(@PathVariable("beskrivelse") String beskrivelse) {
        Stilling ret = query.selectStilling(beskrivelse);
        return query.selectStilling(beskrivelse);
    }

    @RequestMapping(value="/stilling/delete", method=RequestMethod.POST)
    public boolean deleteStilling(@RequestBody Stilling stilling) {
        return query.deleteStilling(stilling);
    }

    @RequestMapping(value="/stilling/add", method=RequestMethod.POST)
    public boolean insertStilling(@RequestBody Stilling stilling) {
        return query.insertStilling(stilling);
    }

    @RequestMapping(value="/stilling/update", method=RequestMethod.POST)
    public boolean updateStilling(@RequestBody Stilling stilling){
        return query.updateStilling(stilling);
    }
}



