package com.example.rest_controllers;

import com.example.database_classes.Bruker;
import com.example.database_classes.Fravaer;
import com.example.database_classes.FravaerMedBrukerOgVakt;
import com.example.database_classes.Vakt;
import com.example.security.TokenManager;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;

/**
 * Created by Knut on 12.01.2017.
 */
/*, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {

        }
        else {
        throw new AuthException("Token not authenticated");
        }*/
@RestController
public class FravaerController {
    private SqlQueries query = new SqlQueries();

    @RequestMapping("/fravaer/{id}")
    public Fravaer getFravaer(@PathVariable("id") Integer id, @RequestHeader(value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.selectFravaer(id);
        } else {
            throw new AuthException("Token not authenticated");
        }
    }


    @RequestMapping(value = "/fravaer/delete", method = RequestMethod.POST)
    public boolean deleteFravaer(@RequestBody Fravaer fravaer, @RequestHeader(value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.deleteFravaer(fravaer);
        } else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value = "/fravaer/add", method = RequestMethod.POST)
    public boolean insertFravaer(@RequestBody Fravaer fravaer, @RequestHeader(value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.insertFravaer(fravaer);
        } else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value = "/fravaer/update", method = RequestMethod.POST)
    public boolean updateFravaer(@RequestBody Fravaer fravaer, @RequestHeader(value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.updateFravaer(fravaer);
        } else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping("/fravaer/all")
    public Fravaer[] getAllFravaer(@RequestHeader(value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.selectAllFravaer();
        } else {
            throw new AuthException("Token not authenticated");
        }
    }


    @RequestMapping(value = "/fravaer/bruker/{Id}")  // ikke laget test for
    public Fravaer[] getFravaerFromBrukerId(@PathVariable("Id") Integer Id, @RequestHeader(value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.selectFravaerFromBrukerId(Id);
        } else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value = "/fravaer/vakt/{Id}")
    public Fravaer[] getFravaerFromVaktId(@PathVariable("Id") Integer vId, @RequestHeader(value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.selectFravaerFromVaktId(vId);
        } else {
            throw new AuthException("Token not authenticated");
        }
    }
    @RequestMapping(value = "/fravaer/medbrukerogvakt")
    public FravaerMedBrukerOgVakt[] getFravaerMedBrukerOgVakt(@RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.selectFravaersMedBrukerOgVakt();
        } else {
            throw new AuthException("Token not authenticated");
        }
    }

   /* @RequestMapping(value = "/fravaer/get/allmedid")
    public FravaerMedBrukerOgVakt[] getFravaersMedBrukerOgVakt() {

    }*/
}





