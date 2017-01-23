package com.example.rest_controllers;

import com.example.database_classes.Tilgjengelighet;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Håkon on 12.01.2017.g_scrum06@mysql.stud.iie.ntnu.no
 */


@RestController
public class TilgjengelighetController {
    SqlQueries query = new SqlQueries();
    @RequestMapping("/tilgjengelighet/{id}")
    public Tilgjengelighet getTilgjengelighet(@PathVariable("id") Integer id) {
        Tilgjengelighet ret = query.selectTilgjengelighet(id);
        return query.selectTilgjengelighet(id);
    }

    @RequestMapping("/tilgjengelighet/all/{fratid}/{tiltid}")
    public Tilgjengelighet[] getAllTilgjengelighetDate(@PathVariable("fratid") String fratid, @PathVariable("tiltid") String tiltid) {

        DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime fra = LocalDateTime.parse(fratid, aDateTimeFormatter);
        LocalDateTime til = LocalDateTime.parse(tiltid, aDateTimeFormatter);


        return query.selectAllTilgjengelighetDate(fra,til);
    }




    @RequestMapping(value="/tilgjengelighet/delete", method= RequestMethod.DELETE)
    public boolean deleteTilgjengelighet(@RequestBody Tilgjengelighet tilgjengelighet) {
        return query.deleteTilgjengelighet(tilgjengelighet);
    }

    @RequestMapping(value="/tilgjengelighet/add", method=RequestMethod.POST)
    public boolean insertTilgjengelighet(@RequestBody Tilgjengelighet tilgjengelighet) {
        return query.insertTilgjengelighet(tilgjengelighet);
    }

    @RequestMapping(value="/tilgjengelighet/update", method=RequestMethod.PUT)
    public boolean updateTilgjengelighet(@RequestBody Tilgjengelighet tilgjengelighet){
        return query.updateTilgjengelighet(tilgjengelighet);
    }
}
