/*
package com.example.rest_controllers;

import com.example.database_classes.Fravaer;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

*/
/**
 * Created by Knut on 13.01.2017.
 *//*

public class FravaerControllerTest {

    FravaerController controller = new FravaerController();
    DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    LocalDateTime m = LocalDateTime.parse("2008-12-18T14:30:40", aDateTimeFormatter);
    Fravaer fravaer = new Fravaer(1, m, m, "Bam");
    Fravaer updateFravaer = new Fravaer(1, m, m, "Kvart");


    @Test
    public void fravaerControllerTest() throws Exception {
        Assert.assertTrue("Can't add fravaer to database", controller.insertFravaer(fravaer));
        Assert.assertNotNull("Can't update fravaer from database", controller.updateFravaer(updateFravaer));
        Assert.assertNotNull("Can't get single fravaer from DB", controller.getFravaer(4));
        Assert.assertNotNull("Can't get all fravaer from vaktId", controller.getFravaerFromVaktId(4));
        Assert.assertNotNull("Can't get all fravaer from vaktId", controller.getFravaerFromBrukerId(4));
        Assert.assertTrue("Can't delete fravaer from DB", controller.deleteFravaer(controller.getFravaer(4)));

    }

    @Before
    public void first() {
        DBConnection.beforeTest();
    }
    @After
    public void after() {
        DBConnection.afterTest();
    }




}*/
