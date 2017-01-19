package com.example.rest_controllers;

import com.example.database_classes.Avdeling;
import com.example.database_classes.Bruker;
import com.example.database_classes.Vakt;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Håkon on 12.01.2017.
 */

@RestController
public class VaktController {
    SqlQueries query = new SqlQueries();

    /**
     * Returns Vakt object by ID
     * @param id
     * @return Vakt
     */
    @RequestMapping("/vakt/{id}")
    public Vakt getVakt(@PathVariable("id") Integer id) {
        return query.selectVakt(id);
    }

    /**
     * Returns all Vakt objects that start at a given date
     * @param date
     * @return Vakt[]
     */
    @RequestMapping("/vakt/all/{date}")
    public Vakt[] getAllVaktDate(@PathVariable("date") String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate test = LocalDate.parse(date, dtf);
        //LocalDateTime ldt = LocalDateTime.parse(date, dtf);
        LocalDateTime ldt = test.atStartOfDay();

        return query.selectAllVakterDate(ldt);
    }

    /**
     * Returns all Vakt objects that start/end at a given date/time
     * @param fratid
     * @param tiltid
     * @return Vakt[]
     */
    @RequestMapping("/vakt/all/{fratid}/{tiltid}")
    public Vakt[] getAllVaktDate(@PathVariable("fratid") String fratid, @PathVariable("tiltid") String tiltid) {

        DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime fra = LocalDateTime.parse(fratid, aDateTimeFormatter);
        LocalDateTime til = LocalDateTime.parse(tiltid, aDateTimeFormatter);

        return query.selectAllVakterDate(fra, til);
    }

    /**
     * Returns all Vakt objects at a given department
     * @param avdeling
     * @return Vakt[]
     */
    @RequestMapping(value="/vakt/avdeling", method = RequestMethod.POST)
    public Vakt[]getVakterAvdeling(@RequestBody Avdeling avdeling) {
        return query.selectVakterAvdeling(avdeling.getAvdelingId());
    }

    @RequestMapping(value="/vakt/bruker", method = RequestMethod.POST)
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

