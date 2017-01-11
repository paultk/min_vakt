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

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return null;

    }

    public void addAvdeling(Avdeling avdeling) {

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

    public Stilling selectStilling(int stilling_id){

        try{
            String selectSql = "SELECT beskrivelse FROM stilling WHERE stilling_id = ?";
            selectQuery = connection.prepareStatement(selectSql);
            selectQuery.setInt(1, stilling_id);

            ResultSet res = selectQuery.executeQuery();

            if (!res.next()) return null;

            String beskrivelse = res.getString(1);

            return new Stilling(stilling_id, beskrivelse);

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return null;

    }

    public boolean updateStilling(Stilling stilling){

        try {
            String sql = "UPDATE stilling SET beskrivelse = ? WHERE stilling_id = ?";
            PreparedStatement updateQuery = connection.prepareStatement(sql);
            updateQuery.setString(1, stilling.getBeskrivelse());
            updateQuery.setInt(2, stilling.getStillingId());
            if (updateQuery.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertStilling(Stilling newStilling){
        try {
            String sql = "INSERT INTO stilling(beskrivelse) VALUES(?);";
            insertQuery = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setString(1, newStilling.getBeskrivelse());
            insertQuery.execute();
            ResultSet res = insertQuery.getGeneratedKeys();
            res.next();
            newStilling.setStillingId(res.getInt(1));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean deleteStilling(Stilling stilling){
        try {

            String sql = "DELETE FROM stilling WHERE stilling_id = ?";
            PreparedStatement deleteQuery = connection.prepareStatement(sql);
            deleteQuery.setInt(1, stilling.getStillingId());
            if (deleteQuery.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

   /* public Passord getPassrod(int passord_id){

        try{
            String selectSql = "SELECT avd_navn FROM avdeling WHERE avdeling_id = ?";
            selectQuery = connection.prepareStatement(selectSql);
            selectQuery.setInt(1, passord_id);
            ResultSet res = selectQuery.executeQuery();

            if (!res.next()) return null;

            String hash = res.getString(1);
            String salt =

            return new Passord(passord_id, );

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return null;

    }*/




}
