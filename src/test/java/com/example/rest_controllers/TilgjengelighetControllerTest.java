package com.example.rest_controllers;

import com.example.database_classes.Tilgjengelighet;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Håkon on 13.01.2017.
 */
public class TilgjengelighetControllerTest {

    private TilgjengelighetController tilgjengelighetController = new TilgjengelighetController();
    DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    LocalDateTime m = LocalDateTime.parse("2008-12-18T14:30:40", aDateTimeFormatter);
    LocalDateTime n = LocalDateTime.parse("2018-12-18T14:30:40", aDateTimeFormatter);
    String t = "2008-12-18T14:30:40";
    String s = "2018-12-18T14:30:40";

    Tilgjengelighet tilgjengelighet = new Tilgjengelighet(2, m, n);
    Tilgjengelighet updateTilgjengelighet = new Tilgjengelighet(1, m, n);


    @Test
    public void testGetTilgjengelighet() throws Exception {
        Assert.assertNotNull("Can't get tilgjengeighet from DB", tilgjengelighetController.getTilgjengelighet(1));
    }

    @Test
    public void testDeleteTilgjengelighet() throws Exception {
        Assert.assertTrue("Can't delete tilgjengelighet from DB", tilgjengelighetController.deleteTilgjengelighet(tilgjengelighetController.getTilgjengelighet(1)));
    }

    @Test
    public void testInsertTilgjengelighet() throws Exception {
        Assert.assertTrue("Can't add tilgjengelighet to DB", tilgjengelighetController.insertTilgjengelighet(tilgjengelighet));
    }

    @Test
    public void testUpdateTilgjengelighet() throws Exception {
        Assert.assertTrue("Can't edite tilgjengelighet in DB", tilgjengelighetController.updateTilgjengelighet(updateTilgjengelighet));
    }

    @Test
    public void testGetAllTilgjengelighetDate() throws Exception { //gir en MySQLSyntaxErrorException: SAVEPOINT
        Assert.assertNotNull("Can't get tilgjengeighetDate from DB", tilgjengelighetController.getAllTilgjengelighetDate(t,s));
    }


    @Before
    public void first() {
        DBConnection.beforeTest();
    }
    @After
    public void after() {
        DBConnection.afterTest();
    }
}