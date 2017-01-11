package com.example.sql_folder;
import com.example.database_classes.*;
import com.sun.corba.se.impl.orb.PrefixParserData;

import java.sql.PreparedStatement;

/**
 * Created by axelkvistad on 10/01/17.
 */

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class SqlQueries extends DBConnection {

    PreparedStatement selectQuery;
    PreparedStatement insertQuery;
    PreparedStatement updateQuery;
    PreparedStatement deleteQuery;

    Connection connection;

    public SqlQueries() {
		DBConnection.connect();
		connection = DBConnection.conn;
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
            insertQuery = connection.prepareStatement(insertSql);
            insertQuery.setString(1, newAvdeling.getNavn());
            insertQuery.execute();
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

    public Bruker selectBruker(int brukerId) {
        try {
            PreparedStatement prep = connection.prepareStatement("SELECT * FROM bruker WHERE bruker_id = ?");
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

    public boolean addBruker(Bruker bruker) {
		try {
			PreparedStatement prep = connection.prepareStatement("INSERT INTO bruker (bruker_id, passord_id, " +
					"stilling_id, avdeling_id, fornavn, etternavn, timelonn, telefonnr, " +
					"epost, stillingsprosent, admin) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			prep.setInt(1, bruker.getBrukerId());
			prep.setInt(2, bruker.getPassordId());
			prep.setInt(3, bruker.getStillingsId());
			prep.setInt(4, bruker.getAvdelingId());
			prep.setString(5, bruker.getFornavn());
			prep.setString(6, bruker.getEtternavn());
			prep.setDouble(7, bruker.getTimelonn());
			prep.setInt(8, bruker.getTelefonNr());
			prep.setString(9, bruker.getEpost());
			prep.setInt(10, bruker.getStillingsProsent());
			prep.setBoolean(11, bruker.isAdmin());
			prep.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
    }
	public boolean deleteBruker(int id) {

		try {
			PreparedStatement prep = connection.prepareStatement("DELETE FROM bruker WHERE bruker.bruker_id = ?");
			prep.setInt(1, id);
			prep.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertVaktBruker(int brukerId, int vaktId) {
		try {
			PreparedStatement prep = connection.prepareStatement("INSERT INTO bruker_vakt (bruker_id, vakt_id) VALUES (?, ?)");
			prep.setInt(1, brukerId);
			prep.setInt(2, vaktId);
			prep.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteVaktBruker(int brukerId, int vaktId) {
		try {
			PreparedStatement prep = connection.prepareStatement("DELETE FROM bruker_vakt WHERE bruker_vakt.bruker_id = ?" +
					" AND bruker_vakt.vakt_id = ?");
			prep.setInt(1, brukerId);
			prep.setInt(2, vaktId);
			prep.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public Bruker[] selectBrukerFromVaktId(int vaktId) {
		try {
			PreparedStatement prep = connection.prepareStatement("SELECT * FROM bruker WHERE bruker_id IN " +
					"(SELECT bruker_id FROM bruker_vakt WHERE vakt_id = ?)");
			prep.setInt(1, vaktId);
			ResultSet res = prep.executeQuery();
			ArrayList<Bruker> brukere = new ArrayList<>();
			while (res.next()) {
				Bruker brk = new Bruker(
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
				brukere.add(brk);
			}
			Bruker[] ret = new Bruker[brukere.size()];
			for (int i = 0; i < ret.length; i++) {
				ret[i] = brukere.get(i);
			}
			return ret;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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

    public Vakt[] selectVakter(Bruker bruker) {
        ResultSet res = null;
        ArrayList<Vakt> vakter = new ArrayList<>();

        try {
            String selectVaktId = "SELECT vakt_id FROM bruker_vakt WHERE bruker_id = ?";
            selectQuery = connection.prepareStatement(selectVaktId);
            selectQuery.setInt(1, bruker.getBrukerId());
            res = selectQuery.executeQuery();

            ResultSet res2 = null;
            while (res.next()) {
                try {
                    int vaktId = res.getInt("vakt_id");
                    String selectVaktStr = "SELECT vaktansvarlig_id, avdeling_id, fra_tid, til_tid, ant_pers FROM vakt WHERE vakt_id = " + vaktId;
                    res2 = connection.createStatement().executeQuery(selectVaktStr);
                    vakter.add(new Vakt(vaktId,
                            res2.getInt("vaktansvarlig_id"),
                            res2.getInt("avdeling_id"),
                            res2.getTimestamp("fra_tid").toLocalDateTime(),
                            res2.getTimestamp("til_tid").toLocalDateTime(),
                            res2.getInt("ant_pers")));
                } finally {
                    SqlCleanup.closeResSet(res2);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SqlCleanup.closeEverything(res, selectQuery, connection);
        }
        return (Vakt[])vakter.toArray();
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

    public boolean deleteVakt(Vakt vakt) {
        try {
            String deleteSql = "DELETE FROM vakt WHERE vakt_id = ?";
            deleteQuery = connection.prepareStatement(deleteSql);
            deleteQuery.setInt(1, vakt.getVaktId());

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
            String insertSql = "INSERT INTO fravaer(vakt_id,ant_timer, kommentar) VALUES(?,?,?)";
            insertQuery = connection.prepareStatement(insertSql);

            insertQuery.setDouble(1, newFravaer.getVaktId());
            insertQuery.setDouble(2, newFravaer.getAntTimer());
            insertQuery.setString(3, newFravaer.getKommentar());
            insertQuery.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }
    public boolean updateFravaer(Fravaer fravaer) {
        try {
            String updateSql = "UPDATE fravaer SET ant_timer = ?, kommentar = ? WHERE bruker_id = ?";
            updateQuery = connection.prepareStatement(updateSql);

            updateQuery.setDouble(1, fravaer.getAntTimer());
            updateQuery.setString(2, fravaer.getKommentar());

            if (updateQuery.executeUpdate() == 1) {
                return true;
            }
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

    public boolean insertTilgjengelighet(Tilgjengelighet newTilgjengelighet){

        try {
            String sql = "INSERT INTO tilgjegnelighet(fra_tid, til_tid) VALUES(?,?);";
            insertQuery = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setTimestamp(1, Timestamp.valueOf(newTilgjengelighet.getFraTid()));
            insertQuery.setTimestamp(2, Timestamp.valueOf(newTilgjengelighet.getTilTid()));
            insertQuery.execute();

            ResultSet res = insertQuery.getGeneratedKeys();
            res.next();
            newTilgjengelighet.setUserId(res.getInt(1));
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public Tilgjengelighet selectTilgjengelighet(int userId) {

        try {
            String selectSql = "SELECT fra_tid, til_tid FROM tilgjengelighet WHERE bruker_id = ?";
            selectQuery = connection.prepareStatement(selectSql);
            ResultSet res = selectQuery.executeQuery();

            if (!res.next()) return null;

            LocalDateTime fraTid = res.getTimestamp("fra_tid").toLocalDateTime();
            LocalDateTime tilTid = res.getTimestamp("til_tid").toLocalDateTime();


            return new Tilgjengelighet(userId, fraTid, tilTid);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteTilgjengelighet(Tilgjengelighet tilgjengelighet) {
        try {
            String deleteSql = "DELETE FROM tilgjengelighet WHERE bruker_id = ?";
            deleteQuery = connection.prepareStatement(deleteSql);
            deleteQuery.setInt(1, tilgjengelighet.getUserId());

            if (deleteQuery.executeUpdate() == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTilgjengelighet(Tilgjengelighet tilgjengelighet) {
        try {
            String updateSql = "UPDATE tilgjengelighet SET fra_tid = ?, til_tid = ? WHERE bruker_id = ?";
            updateQuery = connection.prepareStatement(updateSql);

            // Oversetter LocalDateTime til Timestamp:
            Timestamp fraTid = Timestamp.valueOf(tilgjengelighet.getFraTid());
            Timestamp tilTid = Timestamp.valueOf(tilgjengelighet.getTilTid());


            updateQuery.setTimestamp(1, fraTid);
            updateQuery.setTimestamp(2, tilTid);
            updateQuery.setInt(3, tilgjengelighet.getUserId());

            if (updateQuery.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }






    public static void main(String[] args) {
        SqlQueries query = new SqlQueries();
//		query.deleteBruker(1);
//		Bruker bruker = new Bruker(2, 0, 0, 0, 12345678, 50, 100, true, "Ken", "Bob", "bobby");
//		query.addBruker(bruker);
//        System.out.println(query.selectBruker(1));
		System.out.println(Arrays.toString(query.selectBrukerFromVaktId(0)));
	}
}
