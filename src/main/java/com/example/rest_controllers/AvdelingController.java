package com.example.rest_controllers;


import com.example.database_classes.Avdeling;
import com.example.security.TokenManager;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;

/**
 * Created by Håkon on 12.01.2017.
 */

@RestController
public class AvdelingController {
    private SqlQueries query = new SqlQueries();
    @RequestMapping("/avdeling/{id}")
    public Avdeling getAvdeling(@PathVariable("id") Integer id, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            Avdeling ret = query.selectAvdeling(id);
            return query.selectAvdeling(id);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping("/avdeling/all")
    public Avdeling[] getAvdelinger(@RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.selectAllAvdelinger();
        } else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/avdeling/delete", method= RequestMethod.POST)
    public boolean deleteAvdeling(@RequestBody Avdeling avdeling, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.deleteAvdeling(avdeling);
        } else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/avdeling/add", method=RequestMethod.POST)
    public boolean insertAvdeling(@RequestBody Avdeling avdeling, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.insertAvdeling(avdeling);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/avdeling/update", method=RequestMethod.PUT)
    public boolean updateAvdeling(@RequestBody Avdeling avdeling, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.updateAvdeling(avdeling);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }
}

