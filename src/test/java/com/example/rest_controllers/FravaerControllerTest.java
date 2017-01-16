package com.example.rest_controllers;

import com.example.database_classes.Fravaer;
import com.example.sql_folder.DBConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Knut on 13.01.2017.
 */
public class FravaerControllerTest {

    FravaerController controller = new FravaerController();
    DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ofPattern("y M d H m s");
    LocalDateTime m = LocalDateTime.parse("2010 12 18 14 30 40", aDateTimeFormatter);
    Fravaer fravaer = new Fravaer(4, 3, m, m, "Bom");
    Fravaer updateFravaer = new Fravaer(4, 3, m, m, "Kvarfir");


    @Test
    public void fravaerControllerTest() throws Exception {
        Assert.assertTrue("Can't add fravaer to database", controller.insertFravaer(fravaer));
        Assert.assertNotNull("Can't update fravaer from database", controller.updateFravaer(updateFravaer));
        Assert.assertNotNull("Can't get single fravaer from DB", controller.getFravaer(4));
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
    /*
    @Test
    public void insertFravaer() throws Exception {
        Assert.assertTrue(controller.insertFravaer(fravaer));
    }

    @Test
    public void updateFravaer() throws Exception {
        Assert.assertTrue(controller.updateFravaer(updateFravaer));
    }

    @Test
    public void getFravaer() throws Exception {
        Assert.assertNotNull(controller.getFravaer(3)); // We must chose a object already registered in the database

    }

    @Test
    public void deleteFravaer() throws Exception {
        Assert.assertTrue(controller.deleteFravaer(controller.getFravaer(3)));
    }*/





}