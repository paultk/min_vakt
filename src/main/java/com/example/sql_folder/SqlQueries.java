package com.example.sql_folder;
import com.example.database_classes.*;
import oracle.jvm.hotspot.jfr.StackTrace;

/**
 * Created by axelkvistad on 10/01/17.
 */

import java.sql.*;

public class SqlQueries {

    PreparedStatement selectQuery;
    PreparedStatement insertQuery;
    PreparedStatement updateQuery;

    Connection connection;

    public SqlQueries() {
        DBConnection.connect();
        connection = DBConnection.conn;
    }

    public Avdeling getAvdeling(int avdelingId) {

        try {
            String selectSql = "SELECT avd_navn FROM avdeling WHERE avdeling_id = ?";
            selectQuery = connection.prepareStatement(selectSql);
            selectQuery.setInt(1, avdelingId);
            ResultSet res = selectQuery.executeQuery();

            if (!res.next()) return null;

            String avdelingNavn = res.getString(1);

            return new Avdeling(avdelingId, avdelingNavn);

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return null;

    }

    public void addAvdeling(Avdeling avdeling) {

    }

    public void getBruker(int brukerId) {

    }

    public void addBruker(Bruker bruker) {

    }

    public void getVakt(int vaktId) {

    }

    public void addVakt() {

    }

    public void setVakt() {

    }








}
