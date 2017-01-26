package com.example.rest_controllers;

import com.example.database_classes.Stilling;
import com.example.security.TokenManager;
import com.example.sql_folder.SqlQueries;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.websocket.server.PathParam;

/**
 * Created by Håkon on 11.01.2017.
 */

@RestController
public class StillingController {
    SqlQueries query = new SqlQueries();
    @RequestMapping("/stilling/{beskrivelse}")
    public Stilling getStilling(@PathVariable("beskrivelse") String beskrivelse, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.selectStilling(beskrivelse);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/stilling/delete", method=RequestMethod.POST)
    public boolean deleteStilling(@RequestBody Stilling stilling, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.deleteStilling(stilling);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/stilling/add", method=RequestMethod.POST)
    public boolean insertStilling(@RequestBody Stilling stilling, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.insertStilling(stilling);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/stilling/update", method=RequestMethod.POST)
    public boolean updateStilling(@RequestBody Stilling stilling, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.updateStilling(stilling);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }
}



