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

            // Oversetter LocalDateTime til Timestamp:
            Timestamp fraTid = Timestamp.valueOf(newVakt.getFraTid());
            Timestamp tilTid = Timestamp.valueOf(newVakt.getTilTid());


            insertQuery.setInt(1, newVakt.getVaktansvarligId());
            insertQuery.setInt(2, newVakt.getAvdelingId());
            insertQuery.setTimestamp(3, fraTid);
            insertQuery.setTimestamp(4, tilTid);
            insertQuery.setInt(5, newVakt.getAntPers());

            insertQuery.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateVakt(Vakt vakt) {
        try {
            String updateSql = "UPDATE vakt SET vaktansvarlig_id = ?, avdeling_id = ?, fra_tid = ?, til_tid = ?, ant_pers = ? WHERE vakt_id = ?";
            updateQuery = connection.prepareStatement(updateSql);

            // Oversetter LocalDateTime til Timestamp:
            Timestamp fraTid = Timestamp.valueOf(vakt.getFraTid());
            Timestamp tilTid = Timestamp.valueOf(vakt.getTilTid());

            updateQuery.setInt(1, vakt.getVaktansvarligId());
            updateQuery.setInt(2, vakt.getAvdelingId());
            updateQuery.setTimestamp(3, fraTid);
            updateQuery.setTimestamp(4, tilTid);
            updateQuery.setInt(5, vakt.getAntPers());
            updateQuery.setInt(6, vakt.getVaktId());

            if (updateQuery.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    *
    * STILLING
    *
    */

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

    /*
    *
    * PASSORD
    *
    */


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
    public boolean insertFravaer(Fravaer newFravaer) {
        try {
            String insertSql = "INSERT INTO vakt(ant_timer, kommentar) VALUES(?,?)";
            insertQuery = connection.prepareStatement(insertSql);

            insertQuery.setDouble(1, newFravaer.getAntTimer());
            insertQuery.setString(2, newFravaer.getKommentar());
            insertQuery.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean deleteFravaer(Fravaer fravaer) {
        try {
            String deleteSql = "DELETE FROM fravaer WHERE bruker_id = ?";
            deleteQuery = connection.prepareStatement(deleteSql);
            deleteQuery.setInt(1, fravaer.getBrukerId());

            if (deleteQuery.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void main(String[] args) {
        SqlQueries query = new SqlQueries();
        System.out.println(query.getBruker(0));
    }
}
