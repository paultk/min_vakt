package com.example.rest_controllers;

import com.example.database_classes.Tilgjengelighet;
import com.example.security.TokenManager;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Håkon on 12.01.2017.g_scrum06@mysql.stud.iie.ntnu.no
 */


@RestController
public class TilgjengelighetController {
    private SqlQueries query = new SqlQueries();
    @RequestMapping("/tilgjengelighet/{id}")
    public Tilgjengelighet getTilgjengelighet(@PathVariable("id") Integer id, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.selectTilgjengelighet(id);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping("/tilgjengelighet/all/{fratid}/{tiltid}")
    public Tilgjengelighet[] getAllTilgjengelighetDate(@PathVariable("fratid") String fratid, @PathVariable("tiltid") String tiltid, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime fra = LocalDateTime.parse(fratid, aDateTimeFormatter);
            LocalDateTime til = LocalDateTime.parse(tiltid, aDateTimeFormatter);


            return query.selectAllTilgjengelighetDate(fra,til);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/tilgjengelighet/delete", method= RequestMethod.DELETE)
    public boolean deleteTilgjengelighet(@RequestBody Tilgjengelighet tilgjengelighet, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.deleteTilgjengelighet(tilgjengelighet);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/tilgjengelighet/add", method=RequestMethod.POST)
    public boolean insertTilgjengelighet(@RequestBody Tilgjengelighet tilgjengelighet, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.insertTilgjengelighet(tilgjengelighet);

        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }

    @RequestMapping(value="/tilgjengelighet/update", method=RequestMethod.PUT)
    public boolean updateTilgjengelighet(@RequestBody Tilgjengelighet tilgjengelighet, @RequestHeader (value = "token") String token) throws AuthException {
        if (TokenManager.verifiser(token)) {
            return query.updateTilgjengelighet(tilgjengelighet);
        }
        else {
            throw new AuthException("Token not authenticated");
        }
    }
}
