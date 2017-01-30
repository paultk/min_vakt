package com.example.rest_controllers;

import com.example.database_classes.Avdeling;
import com.example.database_classes.Bruker;
import com.example.database_classes.Vakt;
import com.example.database_classes.VaktMedBruker;
import com.example.security.TokenManager;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Håkon on 12.01.2017.
 */

@RestController
public class VaktController {
    private SqlQueries query = new SqlQueries();

    /**
     * Returns Vakt object by ID
     * @param id
     * @return Vakt
     */
    @RequestMapping("/vakt/{id}")
    public Vakt getVakt(@PathVariable("id") Integer id, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.selectVakt(id);

        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    /**
     * Returns all Vakt objects that start at a given date
     * @param date
     * @return Vakt[]
     */
    @RequestMapping("/vakt/all/{date}")
    public Vakt[] getAllVaktDate(@PathVariable("date") String date, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
            LocalDate test = LocalDate.parse(date, dtf);
            //LocalDateTime ldt = LocalDateTime.parse(date, dtf);
            LocalDateTime ldt = test.atStartOfDay();

            return query.selectAllVakterDate(ldt);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }



    /**
     * Returns all Vakt objects that start/end at a given date/time
     * @param fratid
     * @param tiltid
     * @return Vakt[]
     */
    @RequestMapping("/vakt/all/{fratid}/{tiltid}")
    public Vakt[] getAllVaktDate(@PathVariable("fratid") String fratid, @PathVariable("tiltid") String tiltid, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime fra = LocalDateTime.parse(fratid, aDateTimeFormatter);
            LocalDateTime til = LocalDateTime.parse(tiltid, aDateTimeFormatter);

            return query.selectAllVakterDate(fra, til);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping("vakt/bruker/month/{brukerId}/{year}/{month}")
    public Vakt[] getVakterBrukerCurMonth(@PathVariable("brukerId") int brukerId, @PathVariable("month") int month, @PathVariable("year") int year, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.selectMånedVakterBruker(brukerId, year, month);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping("/vakt/all/month/{fratid}/{avdId}")
    public VaktMedBruker[] getAllVaktMonth(@PathVariable("fratid") String fratid, @PathVariable("avdId") int avdId, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime fra = LocalDateTime.parse(fratid, aDateTimeFormatter);
            System.out.println("fra: " + fra.toString());

            return query.selectAllVakterMonth(fra,avdId);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    /**
     * Returns all Vakt objects at a given department
     * @param avdeling
     * @return Vakt[]
     */
    @RequestMapping(value="/vakt/avdeling", method = RequestMethod.POST)
    public Vakt[]getVakterAvdeling(@RequestBody Avdeling avdeling, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.selectVakterAvdeling(avdeling.getAvdelingId());

        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/vakt/bruker", method = RequestMethod.POST)
    public Vakt[] getVakter(@RequestBody Bruker bruker, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.selectVakter(bruker.getBrukerId());

        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping("/vakt/all")
    public Vakt[] getAllVakter(@RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.selectAllVakter();

        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/vakt/delete", method= RequestMethod.POST)
    public boolean deleteVakt(@RequestBody Vakt vakt, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token) && TokenManager.isAdmin(token)) {
            return query.deleteVakt(vakt);

        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/vakt/add", method=RequestMethod.POST)
    public boolean insertVakt(@RequestBody Vakt vakt, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.insertVakt(vakt);

        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/vakt/update", method=RequestMethod.POST)
    public boolean updateVakt(@RequestBody Vakt vakt, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.updateVakt(vakt);

        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }
}

