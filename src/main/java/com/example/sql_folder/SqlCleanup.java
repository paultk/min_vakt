 package com.example.sql_folder;
/**
 *
 * Opprydder.java  - "Programmering i Java", 4.utgave - 2009-07-01
 * Metoder for å rydde opp etter databasebruk
 */

 import java.sql.*;

public class SqlCleanup {

    public static void closeResSet(ResultSet res) {
        try {
            if (res != null) {
                res.close();
            }
        } catch (SQLException e) {
            writeMessage(e, "closeResSet()");
        }
    }

    public static void closeStatement(Statement stm) {
        try {
            if (stm != null) {
                stm.close();
            }
        } catch (SQLException e) {
            writeMessage(e, "closeStatement()");
        }
    }

    public static void closeConnection(Connection forbindelse) {
        try {
            if (forbindelse != null) {
                forbindelse.close();
            }
        } catch (SQLException e) {
            writeMessage(e, "closeConnection()");
        }
    }

    public static void rollback(Connection forbindelse) {
        try {
            if (forbindelse != null && !forbindelse.getAutoCommit()) {
                forbindelse.rollback();
            }
        } catch (SQLException e) {
            writeMessage(e, "rollback()");
        }
    }

    public static void setAutoCommit(Connection forbindelse) {
        try {
            if (forbindelse != null && !forbindelse.getAutoCommit()) {
                forbindelse.setAutoCommit(true);
            }
        } catch (SQLException e) {
            writeMessage(e, "setAutoCommit()");
        }
    }

    public static void closeEverything(ResultSet res, PreparedStatement query, Connection con) {
        if (!(res == null)) {
            SqlCleanup.closeResSet(res);
        }
        if (!(query == null)) {
            SqlCleanup.closeStatement(query);
        }
        if (!(con == null)) {
            SqlCleanup.setAutoCommit(con);
        }
    }

    public static void writeMessage(Exception e, String message) {
        System.err.println("*** Feil oppstått: " + message + ". ***");
        e.printStackTrace(System.err);
    }

}