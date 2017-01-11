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
    @RequestMapping("/stilling/{id}")
    public Stilling getStilling(@PathVariable("id") Integer id) {
        Stilling ret = query.selectStilling(id);
        System.out.println(ret);
        return query.selectStilling(id);
    }

    /*@RequestMapping(value="/stilling/delete", method=RequestMethod.POST)
    public void deleteStilling(@RequestBody Stilling stilling) {
        query.deleteStilling(stilling.getStillingId());
    }*/

    @RequestMapping(value="/stilling/add", method=RequestMethod.POST)
    public void insertStilling(@RequestBody Stilling stilling) {
        query.insertStilling(stilling);
    }
}



