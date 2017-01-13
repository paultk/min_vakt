package com.example.rest_controllers;

import com.example.database_classes.Fravaer;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Knut on 12.01.2017.
 */

@RestController
public class FravaerController {
    SqlQueries query = new SqlQueries();

    @RequestMapping("/fravaer/{id}")
    public Fravaer getFravaer(@PathVariable("id") Integer id) {
        return query.selectFravaer(id);
    }


    @RequestMapping(value="/fravaer/delete", method= RequestMethod.POST)
    public boolean deleteFravaer(@RequestBody Fravaer fravaer) {
        return query.deleteFravaer(fravaer);
    }

    @RequestMapping(value="/fravaer/add", method=RequestMethod.POST)
    public boolean insertFravaer(@RequestBody Fravaer fravaer) {
        return query.insertFravaer(fravaer);
    }

    @RequestMapping(value="/fravaer/update", method=RequestMethod.POST)
    public boolean updateFravaer(@RequestBody Fravaer fravaer){
        return query.updateFravaer(fravaer);

    }
}



