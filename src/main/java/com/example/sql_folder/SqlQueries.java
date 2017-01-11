package com.example.sql_folder;
import com.example.database_classes.*;

import java.sql.PreparedStatement;

/**
 * Created by axelkvistad on 10/01/17.
 */

import java.sql.*;
import java.time.LocalDateTime;

public class SqlQueries {

    PreparedStatement selectQuery;
    PreparedStatement insertQuery;
    PreparedStatement updateQuery;
    PreparedStatement deleteQuery;

    Connection connection;

    public SqlQueries() {

        connection = DBConnection.conn;
    }

    public Avdeling selectAvdeling(int avdelingId) {

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

    public boolean insertAvdeling(Avdeling newAvdeling) {
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

    public boolean deleteAvdeling(Avdeling avdeling) {
        try {
            String deleteSql = "DELETE FROM avdeling WHERE avdeling_id = ?";
            deleteQuery = connection.prepareStatement(deleteSql);
            deleteQuery.setInt(1, avdeling.getAvdelingId());

            if (deleteQuery.executeUpdate() == 1) {
                return true;
            }
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

    public Vakt selectVakt(int vaktId) {
        try {
            String selectSql = "SELECT vaktansvarlig_id, avdeling_id, fra_tid, til_tid, ant_pers FROM vakt WHERE vakt_id = ?";
            selectQuery = connection.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();

            if (!res.next()) return null;

            int vaktansvarligId = res.getInt("vaktansvarlig_id");
            int avdelingId = res.getInt("avdeling_id");
            LocalDateTime fraTid = res.getTimestamp("fra_tid").toLocalDateTime();
            LocalDateTime tilTid = res.getTimestamp("til_tid").toLocalDateTime();
            int antPers = res.getInt("ant_pers");

            return new Vakt(vaktId, vaktansvarligId, avdelingId, fraTid, tilTid, antPers);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addVakt() {

    }

    public void setVakt() {

    }








}
