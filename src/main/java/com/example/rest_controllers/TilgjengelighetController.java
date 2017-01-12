package com.example.rest_controllers;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;
import com.example.database_classes.Tilgjengelighet;

/**
 * Created by Håkon on 12.01.2017.
 */


@RestController
public class TilgjengelighetController {
    SqlQueries query = new SqlQueries();
    @RequestMapping("/tilgjengelighet/{id}")
    public Tilgjengelighet getTilgjengelighet(@PathVariable("id") Integer id) {
        Tilgjengelighet ret = query.selectTilgjengelighet(id);
        return query.selectTilgjengelighet(id);
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
