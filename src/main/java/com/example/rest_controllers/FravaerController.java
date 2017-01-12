package com.example.rest_controllers;

import com.example.database_classes.Fravaer;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Knut on 12.01.2017.
 */
public class FravaerController {
    SqlQueries query = new SqlQueries();
    @RequestMapping("/fravaer/{id}")
    public Fravaer getFravaer(@PathVariable("id") Integer id) {
        return query.selectFravaer(id);
    }

    @RequestMapping(value="/fravaer/delete", method= RequestMethod.POST)
    public boolean deleteFravaer(@RequestBody Fravaer fravaer) {
        return query.deleteFravaer(fravaer.getBrukerId());
    }

    /*@RequestMapping(value="/fravaer/addfravaer", method=RequestMethod.POST)
    public boolean addFravaerToBruker(@RequestBody Fravaer fravaer, Bruker bruker) {
        return query.in(fravaer.getBrukerId(), bruker.getBrukerId());
    }*/
}
