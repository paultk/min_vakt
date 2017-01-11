package com.example.sql_folder;
import com.example.database_classes.*;

import java.sql.PreparedStatement;

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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public boolean addAvdeling(Avdeling newAvdeling) {
        try {
            String insertSql = "INSERT INTO avdeling(avd_navn) VALUES(?)";
            insertQuery = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setString(1, newAvdeling.getNavn());
            insertQuery.execute();
            ResultSet res = insertQuery.getGeneratedKeys();
            res.next();
            newAvdeling.setAvdelingId(res.getInt(1));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*public void getBruker(int brukerId) {
        DBConnection.connect();
        PreparedStatement prep = DBConnection.conn.prepareStatement("SELECT * FROM bruker WHERE bruker_id = ?");

    }*/

    public void addBruker(Bruker bruker) {

    }

    public void getVakt(int vaktId) {
    }

    public void addVakt() {

    }

    public void setVakt() {

    }








}
