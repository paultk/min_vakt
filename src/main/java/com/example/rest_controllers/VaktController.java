package com.example.rest_controllers;

import com.example.sql_folder.SqlQueries;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.example.database_classes.Vakt;
import com.example.database_classes.Bruker;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Håkon on 12.01.2017.
 */

@RestController
public class VaktController {
    SqlQueries query = new SqlQueries();
    @RequestMapping("/vakt/{id}")
    public Vakt getVakt(@PathVariable("id") Integer id) {
        return query.selectVakt(id);
    }

    @RequestMapping("/vakt/bruker")    // TODO: 12/01/17 (Axel): fiks denne pathen
    public Vakt[] getVakter(Bruker bruker) {
        return query.selectVakter(bruker.getBrukerId());
    }

    @RequestMapping("/vakt/all")
    public Vakt[] getAllVakter() {
        return query.selectAllVakter();
    }

    @RequestMapping(value="/vakt/delete", method= RequestMethod.DELETE)
    public boolean deleteVakt(@RequestBody Vakt vakt) {
        return query.deleteVakt(vakt);
    }

    @RequestMapping(value="/vakt/add", method=RequestMethod.POST)
    public boolean insertVakt(@RequestBody Vakt vakt) {
        return query.insertVakt(vakt);
    }

    @RequestMapping(value="/vakt/update", method=RequestMethod.PUT)
    public boolean updateVakt(@RequestBody Vakt vakt){
        return query.updateVakt(vakt);
    }
}

