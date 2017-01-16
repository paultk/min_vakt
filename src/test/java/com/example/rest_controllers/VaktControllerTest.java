package com.example.rest_controllers;

import com.example.database_classes.Vakt;
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
public class VaktControllerTest {

    VaktController controller = new VaktController();
    DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ofPattern("y M d H m s");
    LocalDateTime m = LocalDateTime.parse("2010 12 18 14 30 40", aDateTimeFormatter);
    Vakt vakt = new Vakt(1, 1, 1 ,m , m, 350);
    Vakt nyVakt = new Vakt(1, 2, 1 ,m , m, 365);



    @Test
    public void vaktControllerTest() throws Exception {
        Assert.assertTrue("Can't add vakt to database", controller.insertVakt(vakt));
        Vakt[] vakter = controller.getAllVakter();
        Assert.assertNotNull("Can't get vakter from database", vakter);
        Assert.assertTrue("Can't update vakt from DB", controller.updateVakt(nyVakt));
        Assert.assertNotNull("Can't get single vakt from DB", controller.getVakt(vakter[1].getVaktId()));
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



}