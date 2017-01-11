package com.example.sql_folder;
import com.example.database_classes.*;

import java.sql.PreparedStatement;

/**
 * Created by axelkvistad on 10/01/17.
 */

import java.sql.*;
import java.time.LocalDateTime;

public class SqlQueries extends DBConnection {

    PreparedStatement selectQuery;
    PreparedStatement insertQuery;
    PreparedStatement updateQuery;
    PreparedStatement deleteQuery;

    Connection connection;

    public SqlQueries() {
        // tom inntil videre pga inheritance av DBConnection
    }

    /*
    *
    * AVDELING
    *
    */

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

    /*
    *
    * BRUKER
    *
    */

    public Bruker getBruker(int brukerId) {
        DBConnection conn = new DBConnection();
        try {
            PreparedStatement prep = DBConnection.conn.prepareStatement("SELECT * FROM bruker WHERE bruker_id = ?");
            prep.setInt(1, brukerId);
            ResultSet res = prep.executeQuery();
            if (res.next()) {
                return new Bruker(
                        res.getInt("bruker_id"),
                        res.getInt("passord_id"),
                        res.getInt("stilling_id"),
                        res.getInt("avdeling_id"),
                        res.getInt("telefonnr"),
                        res.getInt("stillingsprosent"),
                        res.getDouble("timelonn"),
                        res.getBoolean("admin"),
                        res.getString("fornavn"),
                        res.getString("etternavn"),
                        res.getString("epost"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addBruker(Bruker bruker) {

    }

    /*
    *
    * VAKT
    *
    */

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

    public boolean insertVakt(Vakt newVakt) {

        try {
            String insertSql = "INSERT INTO vakt(vaktansvarlig_id, avdeling_id, fra_tid, til_tid, ant_pers) VALUES(?,?,?,?,?)";
            insertQuery = connection.prepareStatement(insertSql);

            insertQuery.setInt(1, newVakt.getVaktansvarligId());
            insertQuery.setInt(2, newVakt.getAvdelingId());
            Timestamp fraTid = Timestamp.valueOf(newVakt.getFraTid()); // oversetter LocalDateTime til Timestamp
            insertQuery.setTimestamp(3, fraTid);
            Timestamp tilTid = Timestamp.valueOf(newVakt.getTilTid()); // oversetter LDT til Timestamp
            insertQuery.setTimestamp(4, tilTid);
            insertQuery.setInt(5, newVakt.getAntPers());

            insertQuery.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    *
    * FRAVAER
    *
    */

    public Fravaer selectFravaer(int brukerId) {

        try {
            String selectSql = "SELECT vakt_id, ant_timer, kommentar FROM fravaer WHERE bruker_id = ?";
            selectQuery = connection.prepareStatement(selectSql);
            selectQuery.setInt(1, brukerId);
            ResultSet res = selectQuery.executeQuery();

            if (!res.next()) return null;

            int vaktId = res.getInt("vakt_id");
            int antTimer = res.getInt("ant_timer");
            String kommentar = res.getString("kommentar");


            return new Fravaer(brukerId, vaktId, antTimer, kommentar);

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }

        return null;
    }



    public static void main(String[] args) {
        SqlQueries query = new SqlQueries();
        System.out.println(query.getBruker(0));
    }
}
