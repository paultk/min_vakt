package com.example.sql_folder;
import com.example.database_classes.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

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
                        res.getString("epost"));
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
						res.getString("epost"));
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

	public boolean updateBrukerPassord(int brukerId, int id) {
		try {
			updateQuery = connection.prepareStatement("UPDATE  bruker SET  passord_id = ? WHERE  bruker_id = ?");
			updateQuery.setInt(1, id);
			updateQuery.setInt(2, brukerId);
			updateQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBrukerStilling(int brukerId, int id) {
		try {
			updateQuery = connection.prepareStatement("UPDATE  bruker SET  stilling_id = ? WHERE  bruker_id = ?");
			updateQuery.setInt(1, id);
			updateQuery.setInt(2, brukerId);
			updateQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBrukerAvdeling(int brukerId, int id) {
		try {
			updateQuery = connection.prepareStatement("UPDATE  bruker SET  avdeling_id = ? WHERE  bruker_id = ?");
			updateQuery.setInt(1, id);
			updateQuery.setInt(2, brukerId);
			updateQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBrukerTelefon(int brukerId, int tlf) {
		try {
			updateQuery = connection.prepareStatement("UPDATE  bruker SET  telefonnr = ? WHERE  bruker_id = ?");
			updateQuery.setInt(1, tlf);
			updateQuery.setInt(2, brukerId);
			updateQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBrukerStillingPros(int brukerId, int pros) {
		try {
			updateQuery = connection.prepareStatement("UPDATE  bruker SET  stillingsprosent = ? WHERE  bruker_id = ?");
			updateQuery.setInt(1, pros);
			updateQuery.setInt(2, brukerId);
			updateQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBrukerTimelonn(int brukerId, double lonn) {
		try {
			updateQuery = connection.prepareStatement("UPDATE  bruker SET  timelonn = ? WHERE  bruker_id = ?");
			updateQuery.setDouble(1, lonn);
			updateQuery.setInt(2, brukerId);
			updateQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBrukerAdmin(int brukerId, boolean admin) {
		try {
			updateQuery = connection.prepareStatement("UPDATE  bruker SET  admin = ? WHERE  bruker_id = ?");
			updateQuery.setBoolean(1, admin);
			updateQuery.setInt(2, brukerId);
			updateQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBrukerFornavn(int brukerId, String fornavn) {
		try {
			updateQuery = connection.prepareStatement("UPDATE  bruker SET  fornavn = ? WHERE  bruker_id = ?");
			updateQuery.setString(1, fornavn);
			updateQuery.setInt(2, brukerId);
			updateQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBrukerEtternavn(int brukerId, String etternavn) {
		try {
			updateQuery = connection.prepareStatement("UPDATE  bruker SET etternavn = ? WHERE  bruker_id = ?");
			updateQuery.setString(1, etternavn);
			updateQuery.setInt(2, brukerId);
			updateQuery.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBrukerEpost(int brukerId, String epost) {
		try {
			updateQuery = connection.prepareStatement("UPDATE  bruker SET  epost = ? WHERE  bruker_id = ?");
			updateQuery.setString(1, epost);
			updateQuery.setInt(2, brukerId);
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
			insertQuery = connection.prepareStatement("INSERT INTO bruker (bruker_id, passord_id, " +
					"stilling_id, avdeling_id, fornavn, etternavn, timelonn, telefonnr, " +
					"epost, stillingsprosent, admin) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			insertQuery.setInt(1, bruker.getBrukerId());
			insertQuery.setInt(2, bruker.getPassordId());
			insertQuery.setInt(3, bruker.getStillingsId());
			insertQuery.setInt(4, bruker.getAvdelingId());
			insertQuery.setString(5, bruker.getFornavn());
			insertQuery.setString(6, bruker.getEtternavn());
			insertQuery.setDouble(7, bruker.getTimelonn());
			insertQuery.setInt(8, bruker.getTelefonNr());
			insertQuery.setString(9, bruker.getEpost());
			insertQuery.setInt(10, bruker.getStillingsProsent());
			insertQuery.setBoolean(11, bruker.isAdmin());
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

	public boolean insertVaktBruker(int brukerId, int vaktId) {
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

	public boolean deleteVaktBruker(int brukerId, int vaktId) {
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
						res.getString("epost"));
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
        return vakter.toArray(new Vakt[vakter.size()]);
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
            double antTimer = res.getDouble("ant_timer");
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

            insertQuery.setInt(1, newFravaer.getVaktId());
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

    public boolean deleteFravaer(int brukerId) {
        try {
            String deleteSql = "DELETE FROM fravaer WHERE bruker_id = ?";
            deleteQuery = connection.prepareStatement(deleteSql);
            deleteQuery.setInt(1, brukerId);
            deleteQuery.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    *
    * OVERTID
    *
    */

    public Overtid selectOvertid(int overtidId) {
        try {
            String selectSql = "SELECT bruker_id, ant_timer, dato, kommentar FROM overtid WHERE overtidId = ?";
            selectQuery = connection.prepareStatement(selectSql);
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
//		query.updateBrukerAdmin()

		//Testing av update
		/*query.deleteBruker(19);
		Bruker test = new Bruker(19, 0, 0, 0, 0, 0, 0, false, "null", "null", "null");
		query.insertBruker(test);
		query.updateBrukerAdmin(19, true);
		query.updateBrukerEpost(19, "Walla");
		query.updateBrukerEtternavn(19, "Test");
		query.updateBrukerFornavn(19, "Testern");
		query.updateBrukerPassord(19, 1);
		query.updateBrukerStilling(19, 1);
		query.updateBrukerStillingPros(19, 100);
		query.updateBrukerTelefon(19, 87654321);
		query.updateBrukerTimelonn(19, 140);
		query.updateBrukerAvdeling(19, 1);*/

		System.out.println(query.selectBruker(19));



//		System.out.println(Arrays.toString(query.selectBrukereFromVaktId(0)));
	}
}
