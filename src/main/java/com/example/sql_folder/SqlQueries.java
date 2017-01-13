package com.example.sql_folder;
import com.example.database_classes.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by axelkvistad on 10/01/17.
 */

/**
 * Created by axelkvistad on 10/01/17.
 */

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
            String insertSql = "INSERT INTO avdeling(avdeling_id, avd_navn) VALUES(?,?)";
            insertQuery = connection.prepareStatement(insertSql);
            insertQuery.setInt(1, newAvdeling.getAvdelingId());
            insertQuery.setString(2, newAvdeling.getNavn());
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

    public boolean updateAvdeling(Avdeling avdeling){

        try {
            String sql = "UPDATE avdeling SET avd_navn = ? WHERE avdeling_id = ?";
            PreparedStatement updateQuery = connection.prepareStatement(sql);
            updateQuery.setString(1, avdeling.getNavn());
            updateQuery.setInt(2, avdeling.getAvdelingId());
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
    * BRUKER
    *
    */

    public Bruker selectBruker(int brukerId) {
        try {
            selectQuery = connection.prepareStatement("SELECT * FROM bruker WHERE bruker_id = ?");
            selectQuery.setInt(1, brukerId);
            ResultSet res = selectQuery.executeQuery();
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
                        res.getString("epost"),
				        res.getString("hash"));
            }
            SqlCleanup.closeResSet(res);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bruker[] selectBrukere() {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM bruker");
			ResultSet res = selectQuery.executeQuery();
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
						res.getString("epost"),
                        res.getString("hash"));
				brukere.add(brk);
			}
			SqlCleanup.closeResSet(res);
			Bruker[] ret = new Bruker[brukere.size()];
			return brukere.toArray(ret);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateBruker(Bruker bruker) {
		try {
			updateQuery = connection.prepareStatement("UPDATE  bruker SET  passord_id =  ?,\n" +
					"stilling_id =  ?,\n" +
					"avdeling_id =  ?,\n" +
					"fornavn =  ?,\n" +
					"etternavn =  ?,\n" +
					"timelonn =  ?,\n" +
					"telefonnr =  ?,\n" +
					"epost = ?,\n" +
					"stillingsprosent =  ?,\n" +
					"admin =  ?,\n" +
                    "hash = ? WHERE bruker_id = ?;");
			updateQuery.setInt(1, bruker.getPassordId());
			updateQuery.setInt(2, bruker.getStillingsId());
			updateQuery.setInt(3, bruker.getAvdelingId());
			updateQuery.setString(4, bruker.getFornavn());
			updateQuery.setString(5, bruker.getEtternavn());
			updateQuery.setDouble(6, bruker.getTimelonn());
			updateQuery.setInt(7, bruker.getTelefonNr());
			updateQuery.setString(8, bruker.getEpost());
			updateQuery.setInt(9, bruker.getStillingsProsent());
			updateQuery.setBoolean(10, bruker.isAdmin());
			updateQuery.setInt(11, bruker.getBrukerId());
			updateQuery.setString(12, bruker.getHash());
			updateQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

    public boolean insertBruker(Bruker bruker) {
		try {
		    bruker.hashPassord();
		    String hash = bruker.getHash();
		    String salt = bruker.getSalt();

            System.out.println("hash: " + hash + "\nsalt: " + salt);
            System.out.println(hash.length());
            String passordSql = "INSERT INTO passord(hash, salt) VALUES(?,?);";
		    PreparedStatement passordQuery = connection.prepareStatement(passordSql);
		    passordQuery.setString(1, hash);
		    passordQuery.setString(2, salt);
		    passordQuery.execute();

		    String pOrdIdSql = "SELECT passord_id FROM passord WHERE hash = ? AND salt = ?";
		    PreparedStatement pOrdIdQuery = connection.prepareStatement(pOrdIdSql);
            pOrdIdQuery.setString(1, hash);
            pOrdIdQuery.setString(2, salt);
            ResultSet res = pOrdIdQuery.executeQuery();
            res.next();
            int passordId = res.getInt("passord_id");
            bruker.setPassordId(passordId);

			insertQuery = connection.prepareStatement("INSERT INTO bruker (passord_id, " +
					"stilling_id, avdeling_id, fornavn, etternavn, timelonn, telefonnr, " +
					"epost, stillingsprosent, admin) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			insertQuery.setInt(1, bruker.getPassordId());
			insertQuery.setInt(2, bruker.getStillingsId());
			insertQuery.setInt(3, bruker.getAvdelingId());
			insertQuery.setString(4, bruker.getFornavn());
			insertQuery.setString(5, bruker.getEtternavn());
			insertQuery.setDouble(6, bruker.getTimelonn());
			insertQuery.setInt(7, bruker.getTelefonNr());
			insertQuery.setString(8, bruker.getEpost());
			insertQuery.setInt(9, bruker.getStillingsProsent());
			insertQuery.setBoolean(10, bruker.isAdmin());
			insertQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
    }
	public boolean deleteBruker(int id) {

		try {
			deleteQuery = connection.prepareStatement("DELETE FROM bruker WHERE bruker.bruker_id = ?");
			deleteQuery.setInt(1, id);
			deleteQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
    *
    * BRUKERVAKT
    *
    */

	public boolean insertBrukerVakt(int brukerId, int vaktId) {
		try {
			insertQuery = connection.prepareStatement("INSERT INTO bruker_vakt (bruker_id, vakt_id) VALUES (?, ?)");
			insertQuery.setInt(1, brukerId);
			insertQuery.setInt(2, vaktId);
			insertQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteBrukerVakt(int brukerId, int vaktId) {
		try {
			deleteQuery = connection.prepareStatement("DELETE FROM bruker_vakt WHERE bruker_vakt.bruker_id = ?" +
					" AND bruker_vakt.vakt_id = ?");
			deleteQuery.setInt(1, brukerId);
			deleteQuery.setInt(2, vaktId);
			deleteQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public BrukerVakt[] selectBrukerVakter() {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM bruker_vakt");
			ResultSet res = selectQuery.executeQuery();
			ArrayList<BrukerVakt> brukerVakter = new ArrayList<>();
			while (res.next()) {
				BrukerVakt brk = new BrukerVakt(res.getInt("bruker_id"), res.getInt("vakt_id"));
				brukerVakter.add(brk);
			}
			SqlCleanup.closeResSet(res);
			BrukerVakt[] ret = new BrukerVakt[brukerVakter.size()];
			return brukerVakter.toArray(ret);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Bruker[] selectBrukereFromVaktId(int vaktId) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM bruker WHERE bruker_id IN " +
					"(SELECT bruker_id FROM bruker_vakt WHERE vakt_id = ?)");
			selectQuery.setInt(1, vaktId);
			ResultSet res = selectQuery.executeQuery();
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
						res.getString("epost"),
                        res.getString("hash"));
				brukere.add(brk);
			}
			SqlCleanup.closeResSet(res);
			return brukere.toArray(new Bruker[brukere.size()]);
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
            String selectSql = "SELECT * FROM vakt WHERE vakt_id = ?";
			selectQuery = connection.prepareStatement(selectSql);
			selectQuery.setInt(1, vaktId);
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

    public Vakt[] selectVakter(int brukerId) {
        ResultSet res = null;
        ArrayList<Vakt> vakter = new ArrayList<>();

        try {
            String selectSql = "SELECT * FROM vakt WHERE vakt_id IN (SELECT vakt_id FROM bruker_vakt WHERE bruker_id = ?)";
            selectQuery = connection.prepareStatement(selectSql);
            selectQuery.setInt(1, brukerId);
            res = selectQuery.executeQuery();
            while (res.next()) {
                vakter.add(new Vakt(
                        res.getInt("vakt_id"),
                        res.getInt("vaktansvarlig_id"),
                        res.getInt("avdeling_id"),
                        res.getTimestamp("fra_tid").toLocalDateTime(),
                        res.getTimestamp("til_tid").toLocalDateTime(),
                        res.getInt("ant_pers")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SqlCleanup.closeEverything(res, selectQuery, connection);
        }
        return vakter.toArray(new Vakt[vakter.size()]);
    }

    public Vakt[] selectAllVakter() {
    	ResultSet res = null;
    	try {
    		ArrayList<Vakt> allVakter = new ArrayList<>();
    		String selectSql = "SELECT * FROM vakt";
    		selectQuery = connection.prepareStatement(selectSql);
    		res = selectQuery.executeQuery();

			while (res.next()) {
				allVakter.add(new Vakt(
						res.getInt("vakt_id"),
						res.getInt("avdeling_id"),
						res.getInt("vaktansvarlig_id"),
						res.getTimestamp("fra_tid").toLocalDateTime(),
						res.getTimestamp("til_tid").toLocalDateTime(),
						res.getInt("ant_pers")
						));
			}
			return allVakter.toArray(new Vakt[allVakter.size()]);
    	} catch (SQLException e) {
    		e.printStackTrace();
		} finally {
			SqlCleanup.closeEverything(res, selectQuery, connection);
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

    public boolean deleteStilling(int id){
        try {

            String sql = "DELETE FROM stilling WHERE stilling_id = ?";
            PreparedStatement deleteQuery = connection.prepareStatement(sql);
            deleteQuery.setInt(1,id);
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

    public Passord selectPassord(int id) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM passord WHERE passord_id = ?");
			selectQuery.setInt(1, id);
			ResultSet res = selectQuery.executeQuery();
			if (res.next()) {
				Passord ret = new Passord(res.getInt("passord_id"), res.getString("salt"), res.getString("hash"));
				return ret;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean insertPassord(Passord passord) {
		try {
			insertQuery = connection.prepareStatement("INSERT INTO passord (hash, salt) " +
					"VALUES (?, ?);");
			insertQuery.setString(1, passord.getHash());
			insertQuery.setString(2, passord.getSalt());
			insertQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deletePassord(int id) {
		try {
			deleteQuery = connection.prepareStatement("DELETE FROM passord WHERE passord_id = ?");
			deleteQuery.setInt(1, id);
			deleteQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
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
            String selectSql = "SELECT * FROM fravaer WHERE bruker_id = ?";
            selectQuery = connection.prepareStatement(selectSql);
            selectQuery.setInt(1, brukerId);
            ResultSet res = selectQuery.executeQuery();

            if (!res.next()) return null;
            int vaktId = res.getInt("vakt_id");
            LocalDateTime fraTid = res.getTimestamp("fra_tid").toLocalDateTime();
            LocalDateTime tilTid = res.getTimestamp("til_tid").toLocalDateTime();
            String kommentar = res.getString("kommentar");

            return new Fravaer(brukerId, vaktId, fraTid, tilTid, kommentar);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertFravaer(Fravaer newFravaer) {
        try {
            String insertSql = "INSERT INTO fravaer(bruker_id, vakt_id,fra_tid,til_tid, kommentar) VALUES(?,?,?,?,?)";
            insertQuery = connection.prepareStatement(insertSql);

            // Oversetter LocalDateTime til Timestamp:
            Timestamp fraTid = Timestamp.valueOf(newFravaer.getFraTid());
            Timestamp tilTid = Timestamp.valueOf(newFravaer.getTilTid());

            insertQuery.setInt(1,newFravaer.getBrukerId());
            insertQuery.setInt(2, newFravaer.getVaktId());
            insertQuery.setTimestamp(3, fraTid);
            insertQuery.setTimestamp(4, tilTid);
            insertQuery.setString(5, newFravaer.getKommentar());
            insertQuery.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateFravaer(Fravaer fravaer) {
        try {
            String updateSql = "UPDATE fravaer SET fra_tid =  ?,til_tid =  ?,kommentar =  ? WHERE  bruker_id = ? AND  vakt_id = ?;";

            updateQuery = connection.prepareStatement(updateSql);

            // Oversetter LocalDateTime til Timestamp:
            Timestamp fraTid = Timestamp.valueOf(fravaer.getFraTid());
            Timestamp tilTid = Timestamp.valueOf(fravaer.getTilTid());

            updateQuery.setTimestamp(1, fraTid);
            updateQuery.setTimestamp(2, tilTid);
            updateQuery.setString(3, fravaer.getKommentar());
            updateQuery.setInt(4,fravaer.getBrukerId());
            updateQuery.setInt(5, fravaer.getVaktId());

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
            deleteQuery.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Fravaer[] selectFravaer() {
        try {
            selectQuery = connection.prepareStatement("SELECT * FROM fravaer");
            ResultSet res = selectQuery.executeQuery();
            ArrayList<Fravaer> fravaer = new ArrayList<>();
            while (res.next()) {
                Fravaer frv = new Fravaer(res.getInt("bruker_id"), res.getInt("vakt_id"),
                        res.getTimestamp("fra_tid").toLocalDateTime(), res.getTimestamp("til_tid").toLocalDateTime(), res.getString("kommentar"));
                fravaer.add(frv);
            }
            SqlCleanup.closeResSet(res);
            Fravaer[] ret = new Fravaer[fravaer.size()];
            return fravaer.toArray(ret);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }






    /*
    *
    * OVERTID
    *
    */

    public Overtid selectOvertid(int overtidId) {
        try {
            String selectSql = "SELECT bruker_id, ant_timer, dato, kommentar FROM overtid WHERE overtid_id = ?";
            selectQuery = connection.prepareStatement(selectSql);
			selectQuery.setInt(1, overtidId);
            ResultSet res = selectQuery.executeQuery();

            if (!res.next()) return null;

            int brukerId = res.getInt("bruker_id");
            double antTimer = res.getDouble("ant_timer");
            Date dato = res.getDate("dato");
            String kommentar = res.getString("kommentar");

            return new Overtid(overtidId, brukerId, antTimer, dato, kommentar);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertOvertid(Overtid newOvertid) {
        try {
            String insertSql = "INSERT INTO overtid(bruker_id, ant_timer, dato, kommentar) VALUES(?,?,?,?)";
            insertQuery = connection.prepareStatement(insertSql);
            insertQuery.setInt(1, newOvertid.getBrukerId());
            insertQuery.setDouble(2, newOvertid.getAntTimer());
            insertQuery.setDate(3, newOvertid.getDato());
            insertQuery.setString(4, newOvertid.getKommentar());

            insertQuery.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateOvertid(Overtid overtid) {
        try {
            String updateSql = "UPDATE overtid SET bruker_id = ?, ant_timer = ?, dato = ?, kommentar = ? WHERE overtid_id = ?";
            updateQuery = connection.prepareStatement(updateSql);


            updateQuery.setInt(1, overtid.getBrukerId());
            updateQuery.setDouble(2, overtid.getAntTimer());
            updateQuery.setDate(3, overtid.getDato());
            updateQuery.setString(4, overtid.getKommentar());
			updateQuery.setInt(5, overtid.getOvertidId());

            if (updateQuery.executeUpdate() == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteOvertid(Overtid overtid) {
        try {
            String deleteSql = "DELETE FROM overtid WHERE overtid_id = ?";
            deleteQuery = connection.prepareStatement(deleteSql);
            deleteQuery.setInt(1, overtid.getOvertidId());

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
    * TILGJENGELIGHET
    *
    */


    public boolean insertTilgjengelighet(Tilgjengelighet newTilgjengelighet){

        try {
            String sql = "INSERT INTO tilgjengelighet(fra_tid, til_tid) VALUES(?,?);";
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
            selectQuery.setInt(1, userId);
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

		Vakt vakt = query.selectVakt(1);
		vakt.setAntPers(50);
		query.updateVakt(vakt);

		vakt.setAntPers(7);
		vakt.setVaktansvarligId(2);
		query.insertVakt(vakt);

		Avdeling avdeling1 = query.selectAvdeling(2);
		Stilling stilling1 = query.selectStilling(2);

		Bruker bruker1 = new Bruker(2, 2, 90133787, 100, 300, false, "tb1Fornavn", "tb1Etternavn", "tb1@stolav.no", "abcDEF!#");
        query.insertBruker(bruker1);

	}
}
