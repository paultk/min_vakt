package com.example.rest_controllers;

import com.example.database_classes.Fravaer;
import com.example.security.TokenManager;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//*
// * Created by Knut on 13.01.2017.


public class FravaerControllerTest {

    private FravaerController controller = new FravaerController();
	private DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
	private LocalDateTime m = LocalDateTime.parse("2008-12-18T14:30:40", aDateTimeFormatter);
	private Fravaer fravaer = new Fravaer(3, m, m, "Bam");
	private Fravaer updateFravaer = new Fravaer(2, m, m, "Kvart");
	private String token;


    @Test
    public void fravaerControllerTest() throws Exception {
        Assert.assertTrue("Can't add fravaer to database", controller.insertFravaer(fravaer, token));
        Assert.assertNotNull("Can't update fravaer from database", controller.updateFravaer(updateFravaer, token));
        Assert.assertNotNull("Can't get single fravaer from DB", controller.getFravaer(12, token));
        Assert.assertNotNull("Can't get all fravaer from brukerId", controller.getFravaerFromVaktId(2, token));
        Assert.assertNotNull("Can't get all fravaer from vaktId", controller.getFravaerFromBrukerId(2, token));
        Assert.assertTrue("Can't delete fravaer from DB", controller.deleteFravaer(controller.getFravaer(1, token), token));

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
