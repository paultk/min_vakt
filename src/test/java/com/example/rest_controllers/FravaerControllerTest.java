package com.example.rest_controllers;

import com.example.database_classes.Fravaer;
import org.junit.Assert;
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
    Fravaer fravaer = new Fravaer(4, 1, m, m, "Nja");
    Fravaer updateFravaer = new Fravaer(3, 1, m, m, "Korleis");


    @Test
    public void getFravaer() throws Exception {
        Assert.assertNotNull(controller.getFravaer(1)); // We must chose a object already registered in the database

    }

    @Test
    public void deleteFravaer() throws Exception {
        Assert.assertTrue(controller.deleteFravaer(controller.getFravaer(2)));
    }

    @Test
    public void insertFravaer() throws Exception {
        Assert.assertTrue(controller.insertFravaer(fravaer));
    }

    @Test
    public void updateFravaer() throws Exception {
        Assert.assertTrue(controller.updateFravaer(updateFravaer));
    }

}