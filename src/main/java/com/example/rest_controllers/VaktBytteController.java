package com.example.rest_controllers;

import com.example.database_classes.VaktBytte;
import com.example.security.TokenManager;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;

/**
 * Created by Knut on 28.01.2017.
 */
@RestController
public class VaktBytteController {
    private SqlQueries query = new SqlQueries();

    @RequestMapping(value="/vaktbytte/get/alle", method = RequestMethod.POST)
    public VaktBytte[] getVaktBytter(@RequestHeader(value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.getVaktBytter();
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/vaktbytte/bytt", method= RequestMethod.POST)
    public boolean updateVaktBytte(@RequestBody VaktBytte bytte, @RequestHeader(value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.byttVakt(bytte.getBrukerId1(), bytte.getVaktId(), bytte.getBrukerId2());
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

}
