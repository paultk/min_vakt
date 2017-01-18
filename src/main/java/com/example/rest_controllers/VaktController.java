package com.example.rest_controllers;

import com.example.database_classes.Avdeling;
import com.example.database_classes.Bruker;
import com.example.database_classes.Vakt;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @RequestMapping("/vakt/all/{fratid}/{tiltid}")
    public Vakt[] getAllVaktDato(@PathVariable("fratid") String fratid, @PathVariable("tiltid") String tiltid) {

        DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime fra = LocalDateTime.parse(fratid, aDateTimeFormatter);
        LocalDateTime til = LocalDateTime.parse(tiltid, aDateTimeFormatter);

        System.out.println("asdf" + fra.toString());

        return query.selectAllVakterDate(fra, til);
    }

    @RequestMapping(value="/vakt/avdeling", method = RequestMethod.POST)
    public Vakt[]getVakterAvdeling(@RequestBody Avdeling avdeling) {
        return query.selectVakterAvdeling(avdeling.getAvdelingId());
    }

    @RequestMapping(value="/vakt/bruker", method = RequestMethod.POST)    // TODO: 12/01/17 (Axel): fiks denne pathen
    public Vakt[] getVakter(@RequestBody Bruker bruker) {
        return query.selectVakter(bruker.getBrukerId());
    }

    @RequestMapping("/vakt/all")
    public Vakt[] getAllVakter() {
        return query.selectAllVakter();
    }

    @RequestMapping(value="/vakt/delete", method= RequestMethod.POST)
    public boolean deleteVakt(@RequestBody Vakt vakt) {
        return query.deleteVakt(vakt);
    }

    @RequestMapping(value="/vakt/add", method=RequestMethod.POST)
    public boolean insertVakt(@RequestBody Vakt vakt) {
        return query.insertVakt(vakt);
    }

    @RequestMapping(value="/vakt/update", method=RequestMethod.POST)
    public boolean updateVakt(@RequestBody Vakt vakt){
        return query.updateVakt(vakt);
    }
}

