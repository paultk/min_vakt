package com.example.rest_controllers;

import com.example.database_classes.Tilgjengelighet;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

/**
 * Created by Håkon on 13.01.2017.
 */
public class TilgjengelighetControllerTest {

    private TilgjengelighetController tilgjengelighetController = new TilgjengelighetController();
    DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ofPattern("y M d H m s");
    LocalDateTime m = LocalDateTime.parse("2010 12 18 14 30 40", aDateTimeFormatter);

    Tilgjengelighet tilgjengelighet = new Tilgjengelighet(2, m, m);
    Tilgjengelighet updateTilgjengelighet = new Tilgjengelighet(1, m, m);

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

    @Before
    public void first() {
        DBConnection.beforeTest();
    }
    @After
    public void after() {
        DBConnection.afterTest();
    }
}