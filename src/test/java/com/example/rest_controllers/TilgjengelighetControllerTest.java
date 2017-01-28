package com.example.rest_controllers;

import com.example.database_classes.Tilgjengelighet;
import com.example.security.TokenManager;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//*
// * Created by Håkon on 13.01.2017.


public class TilgjengelighetControllerTest {

    private TilgjengelighetController tilgjengelighetController = new TilgjengelighetController();
	private DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
	private LocalDateTime m = LocalDateTime.parse("2008-12-18T14:30:40", aDateTimeFormatter);
	private LocalDateTime n = LocalDateTime.parse("2018-12-18T14:30:40", aDateTimeFormatter);
	private String t = "2008-12-18T14:30:40";
	private String s = "2018-12-18T14:30:40";

	private Tilgjengelighet tilgjengelighet = new Tilgjengelighet(1, m, n);
	private Tilgjengelighet updateTilgjengelighet = new Tilgjengelighet(1, m, n);

	private String token;


    @Test
    public void testGetTilgjengelighet() throws Exception {
        Assert.assertNotNull("Can't get tilgjengeighet from DB", tilgjengelighetController.getTilgjengelighet(1, token));
    }

    @Test
    public void testDeleteTilgjengelighet() throws Exception {
        Assert.assertTrue("Can't delete tilgjengelighet from DB", tilgjengelighetController.deleteTilgjengelighet(tilgjengelighetController.getTilgjengelighet(1, token), token));
    }

    @Test
    public void testInsertTilgjengelighet() throws Exception {
        Assert.assertTrue("Can't add tilgjengelighet to DB", tilgjengelighetController.insertTilgjengelighet(tilgjengelighet, token));
    }

    @Test
    public void testUpdateTilgjengelighet() throws Exception {
        Assert.assertTrue("Can't edite tilgjengelighet in DB", tilgjengelighetController.updateTilgjengelighet(updateTilgjengelighet, token));
    }

    @Test
    public void testGetAllTilgjengelighetDate() throws Exception { //gir en MySQLSyntaxErrorException: SAVEPOINT
        Assert.assertNotNull("Can't get tilgjengeighetDate from DB", tilgjengelighetController.getAllTilgjengelighetDate(t,s, token));
    }


    @Before
    public void first() throws Exception {
        DBConnection.beforeTest();
        this.token = TokenManager.lagToken("test", true);
    }
    @After
    public void after() {
        DBConnection.afterTest();
    }
}
