package com.example.rest_controllers;


import com.example.database_classes.Avdeling;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Håkon on 12.01.2017.
 */

@RestController
public class AvdelingController {
    SqlQueries query = new SqlQueries();
    @RequestMapping("/avdeling/{id}")
    public Avdeling getAvdeling(@PathVariable("id") Integer id) {
        Avdeling ret = query.selectAvdeling(id);
        return query.selectAvdeling(id);
    }

    @RequestMapping("/avdeling/all")
    public Avdeling[] getAvdelinger() {
        return query.selectAllAvdelinger();
    }

    @RequestMapping(value="/avdeling/delete", method= RequestMethod.POST)
    public boolean deleteAvdeling(@RequestBody Avdeling avdeling) {
        return query.deleteAvdeling(avdeling);
    }

    @RequestMapping(value="/avdeling/add", method=RequestMethod.POST)
    public boolean insertAvdeling(@RequestBody Avdeling avdeling) {
        return query.insertAvdeling(avdeling);
    }

    @RequestMapping(value="/avdeling/update", method=RequestMethod.PUT)
    public boolean updateAvdeling(@RequestBody Avdeling avdeling){
        return query.updateAvdeling(avdeling);
    }
}

