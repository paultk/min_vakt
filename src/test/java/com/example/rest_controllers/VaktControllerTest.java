/*
package com.example.rest_controllers;

import com.example.database_classes.Avdeling;
import com.example.database_classes.Bruker;
import com.example.database_classes.Vakt;
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

public class VaktControllerTest {

    VaktController controller = new VaktController();
    DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    LocalDateTime m = LocalDateTime.parse("2010-12-18T14:30:40", aDateTimeFormatter);
    Vakt vakt = new Vakt(200, 1, 1 ,m , m, 350);
    Vakt nyVakt = new Vakt(1, 1, 1 ,m , m, 400);
    String t = "2008-12-18T14:30:40";
    String s = "2018-12-18T14:30:41";
    Avdeling avdeling = new Avdeling(1, "Test");
    Bruker bruker = new Bruker(7, 11, "Assistent", 1, 1, 1, 1, true, "test", "test", "test", "Admin@@@");



    @Test
    public void vaktControllerTest() throws Exception {
        Assert.assertTrue("Can't add vakt to database", controller.insertVakt(vakt));
        Vakt[] vakter = controller.getAllVakter();
        Assert.assertNotNull("Can't get vakter from database", vakter);
        Assert.assertNotNull("Can't get vakter on brukerid from database", controller.getVakter(bruker));
        Assert.assertNotNull("Can't get vakter on a date from DB", controller.getVakterAvdeling(avdeling));
        Assert.assertNotNull("Can't get vakter on a user with month/year from DB", controller.getVakterBrukerCurMonth(1,12,2010));
        Assert.assertNotNull("Can't get vakter on a month and avdid from DB", controller.getAllVaktMonth(t,1));
        Assert.assertNotNull("Can't get vakter on a date from DB", controller.getAllVaktDate(t,s));
        Assert.assertTrue("Can't update vakt from DB", controller.updateVakt(nyVakt));
        //System.out.println(Arrays.toString(controller.getAllVaktDate(t,s)));
        Assert.assertTrue("Can't delete vakt from DB", controller.deleteVakt(vakter[vakter.length - 1]));
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
