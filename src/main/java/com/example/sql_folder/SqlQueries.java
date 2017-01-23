package com.example.sql_folder;
import com.example.database_classes.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.ValueRange;
import java.util.ArrayList;

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

    public Avdeling[] selectAllAvdelinger() {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM avdeling");
			ResultSet res = selectQuery.executeQuery();
			ArrayList<Avdeling> avdelinger = new ArrayList<>();
			while (res.next()) {
				Avdeling avd = new Avdeling(
						res.getInt("avdeling_id"),
						res.getString("avd_navn"));
				avdelinger.add(avd);
			}
			SqlCleanup.closeResSet(res);
			Avdeling[] ret = new Avdeling[avdelinger.size()];
			return avdelinger.toArray(ret);
		}
		catch (Exception e) {
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

    public Bruker selectBruker(String epost) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM bruker WHERE epost = ?");
			selectQuery.setString(1, epost);
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
					"admin =  ?\n" +
                    " WHERE bruker_id = ?;");
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
		    bruker.hashPassord();    // TODO: 13/01/17 (Axel): this line will be removed eventually, only useful for testing within the class right now
            String hash = bruker.getHash();
		    String salt = bruker.getSalt();

		    Passord passord = new Passord(salt, hash);
		    if (insertPassord(passord)) {
                int passordId = selectPassordId(hash, salt);
                if (passordId != -1) {
                    bruker.setPassordId(passordId);
                } else {
                    System.out.println("passordId = -1, error in selectPassordId()");
                    return false;
                }
            } else {
                return false;
            }

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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
		    throw e;
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
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Bruker[] selectBrukereFromAvdelingId(int avdelingId) {
	    try {
	        String selectSql = "SELECT * FROM bruker WHERE avdeling_id = ?";
	        selectQuery = connection.prepareStatement(selectSql);
	        selectQuery.setInt(1, avdelingId);
	        ResultSet res = selectQuery.executeQuery();
	        ArrayList<Bruker> brukere = new ArrayList<>();
	        while (res.next()) {
	            brukere.add(new Bruker(
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
                        res.getString("hash")));
            }
            SqlCleanup.closeResSet(res);
	        return brukere.toArray(new Bruker[brukere.size()]);
        } catch (SQLException e) {
	        e.printStackTrace();
        }
        return null;
    }

    public double calculateMonthlyWage(int brukerId, LocalDate localDate) {
		int fullEmploymentHours = 165;
		ValueRange range = localDate.range(ChronoField.DAY_OF_MONTH);
		Long max = range.getMaximum();
		LocalDateTime startOfMonth = localDate.withDayOfMonth(1).atStartOfDay();
		LocalDateTime endOfMonth = localDate.withDayOfMonth(max.intValue()).atTime(23, 59, 59);

		ArrayList<Vakt> vaktList = new ArrayList<>();

		try {
			String selectSql =
					"SELECT v.*, b.timelonn, b.stillingsprosent, Sum(o.ant_timer) AS o_tid " +
							"FROM bruker b " +
								"JOIN bruker_vakt bv ON b.bruker_id = bv.bruker_id " +
								"JOIN vakt v ON v.vakt_id = bv.vakt_id " +
								"JOIN overtid o ON bv.vakt_id = o.vakt_id AND bv.bruker_id = o.bruker_id " +
							"WHERE b.bruker_id = ? AND v.fra_tid >= ? AND v.til_tid <= ?;";
			selectQuery = connection.prepareStatement(selectSql);

			selectQuery.setInt(1, brukerId);
			selectQuery.setTimestamp(2, Timestamp.valueOf(startOfMonth));
			selectQuery.setTimestamp(3, Timestamp.valueOf(endOfMonth));

			ResultSet res = selectQuery.executeQuery();

			int employmentPercentage = res.getInt("stillingsprosent");
			double hourlyWage = res.getDouble("timelonn");
			double overTime = res.getDouble("o_tid");
			while (res.next()) {
				vaktList.add(new Vakt(
						res.getInt("vakt_id"),
						res.getInt("vaktansvarlig_id"),
						res.getInt("avdeling_id"),
						res.getTimestamp("fra_tid").toLocalDateTime(),
						res.getTimestamp("til_tid").toLocalDateTime(),
						res.getInt("ant_pers")));
			}

			double employmentHours = (fullEmploymentHours * employmentPercentage) / 100;

			double hoursWorkedThisMonth = 0;
			for (Vakt vakt : vaktList) {
				Long hours = vakt.getFraTid().until(vakt.getTilTid(), ChronoUnit.HOURS);
				hoursWorkedThisMonth += hours.doubleValue();
			}

			if (hoursWorkedThisMonth > employmentHours) {
				overTime += (hoursWorkedThisMonth - employmentHours);
			}

			double monthlyWage = (employmentHours * hourlyWage) + (overTime * hourlyWage * 1.5);

			return monthlyWage;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
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

    public Vakt[] selectAllVakterDate(LocalDateTime ldt) {
		LocalDateTime startTime = ldt.withHour(0).withMinute(0).withSecond(0);
		LocalDateTime endTime = ldt.plusYears(2);
		return selectAllVakterDate(startTime, endTime);
	}

    public Vakt[] selectAllVakterDate(LocalDateTime fratid, LocalDateTime tiltid) {
        ResultSet res = null;
        ArrayList<Vakt> vakter = new ArrayList<>();

    try {
        String selectSql = "SELECT * FROM vakt WHERE fra_tid > ? AND til_tid < ?";
        selectQuery = connection.prepareStatement(selectSql);
        selectQuery.setTimestamp(1, Timestamp.valueOf(fratid));
        selectQuery.setTimestamp(2, Timestamp.valueOf(tiltid));

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

	public VaktMedBruker[] selectAllVakterMonth(LocalDateTime fratid, int avdId) {
		ResultSet res = null;
		ArrayList<VaktMedBruker> vakter = new ArrayList<>();


		try {
			String selectSql = "SELECT vakt.vakt_id,bruker_vakt.vakt_id vaktansvarlig_id, avdeling_id, fra_tid, til_tid, ant_pers, bruker_vakt.bruker_id " +
					"FROM vakt, bruker_vakt WHERE MONTH(fra_tid) = ? AND YEAR(fra_tid) = ? AND avdeling_id = ?";
			selectQuery = connection.prepareStatement(selectSql);
			selectQuery.setInt(1, fratid.getMonthValue());
			selectQuery.setInt(2, fratid.getYear());
			selectQuery.setInt(3, avdId);

			res = selectQuery.executeQuery();
			while (res.next()) {
				Vakt vakt = new Vakt(res.getInt("vakt_id"), res.getInt("vaktansvarlig_id"),
						res.getInt("avdeling_id"), res.getTimestamp("fra_tid").toLocalDateTime(),
						res.getTimestamp("til_tid").toLocalDateTime(),res.getInt("ant_pers"));
				vakter.add(new VaktMedBruker(vakt, res.getInt("bruker_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlCleanup.closeEverything(res, selectQuery, connection);
		}
		VaktMedBruker[] ret = vakter.toArray(new VaktMedBruker[vakter.size()]);
		return ret;
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

    public Vakt[] selectVakterAvdeling(int avdelingId) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM vakt WHERE avdeling_id = ?");
			selectQuery.setInt(1, avdelingId);
			ResultSet res = selectQuery.executeQuery();
			ArrayList<Vakt> vakter = new ArrayList<>();
			while (res.next()) {
				Vakt vakt = new Vakt(
						res.getInt("vakt_id"),
						res.getInt("vaktansvarlig_id"),
						res.getInt("avdeling_id"),
						res.getTimestamp("fra_tid").toLocalDateTime(),
						res.getTimestamp("til_tid").toLocalDateTime(),
						res.getInt("ant_pers")
				);
				vakter.add(vakt);
			}
			SqlCleanup.closeResSet(res);
			return vakter.toArray(new Vakt[vakter.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Vakt[] selectMånedVakterBruker(int brukerId, int year, int month) {
    	try {
    		selectQuery = connection.prepareStatement("SELECT * FROM vakt WHERE vakt_id IN " +
					"(SELECT vakt_id FROM bruker_vakt WHERE bruker_id = ?) " +
					"AND MONTH(fra_tid) = ? AND YEAR(fra_tid) = ?");
    		selectQuery.setInt(1, brukerId);
    		selectQuery.setInt(2, month);
    		selectQuery.setInt(3, year);
    		ArrayList<Vakt> vakter = new ArrayList<>();
    		ResultSet res = selectQuery.executeQuery();
    		while (res.next()) {
    			vakter.add(new Vakt(
    					res.getInt("vakt_id"),
						res.getInt("vaktansvarlig_id"),
						res.getInt("avdeling_id"),
						res.getTimestamp("fra_tid").toLocalDateTime(),
						res.getTimestamp("til_tid").toLocalDateTime(),
						res.getInt("ant_pers")
				));
			}
			return vakter.toArray(new Vakt[vakter.size()]);
		}
		catch (SQLException e) {
    		e.printStackTrace();
		}
		return null;
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

            deleteQuery.setInt(1,stilling.getStillingId());
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

    public int selectPassordId(String hash, String salt) {
        try {
            String selectSql = "SELECT passord_id FROM passord WHERE hash = ? AND salt = ?";
            selectQuery = connection.prepareStatement(selectSql);
            selectQuery.setString(1, hash);
            selectQuery.setString(2, salt);
            ResultSet res = selectQuery.executeQuery();
            if (res.next()) {
                return res.getInt("passord_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

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

	public Passord selectPassord(Bruker bruker) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM passord WHERE passord_id = ?");
			selectQuery.setInt(1, bruker.getPassordId());
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


	public Passord[] selectPassordene() {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM passord");
			ResultSet res = selectQuery.executeQuery();
			ArrayList<Passord> passord = new ArrayList<>();
			while (res.next()) {
				Passord pass = new Passord(
						res.getInt("passord_id"),
						res.getString("salt"),
						res.getString("hash"));
				passord.add(pass);
			}
			SqlCleanup.closeResSet(res);
			Passord[] ret = new Passord[passord.size()];
			return passord.toArray(ret);
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

    public Fravaer[] selectAllFravaer() {
            ResultSet res = null;
            try {
                ArrayList<Fravaer> allFravaer = new ArrayList<>();
                String selectSql = "SELECT * FROM fravaer";
                selectQuery = connection.prepareStatement(selectSql);
                res = selectQuery.executeQuery();

                while (res.next()) {
                    allFravaer.add(new Fravaer(
                            res.getInt("bruker_id"),
                            res.getInt("vakt_id"),
                            res.getTimestamp("fra_tid").toLocalDateTime(),
                            res.getTimestamp("til_tid").toLocalDateTime(),
                            res.getString("kommentar")
                    ));
                }
                return allFravaer.toArray(new Fravaer[allFravaer.size()]);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                SqlCleanup.closeEverything(res, selectQuery, connection);
            }
            return null;
        }

	public Fravaer[] selectFravaerFromVaktId(int vaktId) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM fravaer WHERE vakt_id IN " +
					"(SELECT vakt_id FROM vakt WHERE vakt_id = ?)");
			selectQuery.setInt(1, vaktId);
			ResultSet res = selectQuery.executeQuery();
			ArrayList<Fravaer> fravaer = new ArrayList<>();
			while (res.next()) {
				Fravaer frv = new Fravaer(
						res.getInt("bruker_id"),
						res.getInt("vakt_id"),
						res.getTimestamp("fra_tid").toLocalDateTime(),
						res.getTimestamp("til_tid").toLocalDateTime(),
						res.getString("kommentar"));
				fravaer.add(frv);
			}
			SqlCleanup.closeResSet(res);
			return fravaer.toArray(new Fravaer[fravaer.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public Fravaer[] selectFravaerFromBrukerId(int brukerId) {
        try {
            selectQuery = connection.prepareStatement("SELECT * FROM fravaer WHERE bruker_id IN " +
                    "(SELECT bruker_id FROM bruker WHERE bruker_id = ?)");
            selectQuery.setInt(1, brukerId);
            ResultSet res = selectQuery.executeQuery();
            ArrayList<Fravaer> fravaer = new ArrayList<>();
            while (res.next()) {
                Fravaer frv = new Fravaer(
                        res.getInt("bruker_id"),
                        res.getInt("vakt_id"),
                        res.getTimestamp("fra_tid").toLocalDateTime(),
                        res.getTimestamp("til_tid").toLocalDateTime(),
                        res.getString("kommentar"));
                fravaer.add(frv);
            }
            SqlCleanup.closeResSet(res);
            return fravaer.toArray(new Fravaer[fravaer.size()]);
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

    public Overtid[] selectOvertiderBruker(int brukerId) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM overtid WHERE bruker_id = ?");
			selectQuery.setInt(1, brukerId);
			ResultSet res = selectQuery.executeQuery();
			ArrayList<Overtid> overtider = new ArrayList<>();
			while (res.next()) {
				Overtid ny = new Overtid(
						res.getInt("overtid_id"),
						res.getInt("bruker_id"),
						res.getDouble("ant_timer"),
						res.getInt("vakt_id"),
						res.getString("kommentar"));
				overtider.add(ny);
			}
			return overtider.toArray(new Overtid[overtider.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public Overtid selectOvertid(int overtidId) {
        try {
            String selectSql = "SELECT bruker_id, ant_timer, vakt_id, kommentar FROM overtid WHERE overtid_id = ?";
            selectQuery = connection.prepareStatement(selectSql);
			selectQuery.setInt(1, overtidId);
            ResultSet res = selectQuery.executeQuery();

            if (!res.next()) return null;

            int brukerId = res.getInt("bruker_id");
            double antTimer = res.getDouble("ant_timer");
            int vaktId = res.getInt("vakt_id");
            String kommentar = res.getString("kommentar");

            return new Overtid(overtidId, brukerId, antTimer, vaktId, kommentar);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Overtid[] selectOvertider() {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM overtid");
			ResultSet res = selectQuery.executeQuery();
			ArrayList<Overtid> overtider = new ArrayList<>();
			while (res.next()) {
				Overtid ny = new Overtid(
						res.getInt("overtid_id"),
						res.getInt("bruker_id"),
						res.getDouble("ant_timer"),
						res.getInt("vakt_id"),
						res.getString("kommentar"));
				overtider.add(ny);
			}
			return overtider.toArray(new Overtid[overtider.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public boolean insertOvertid(Overtid newOvertid) {
        try {
            String insertSql = "INSERT INTO overtid(bruker_id, ant_timer, vakt_id, kommentar) VALUES(?,?,?,?)";
            insertQuery = connection.prepareStatement(insertSql);
            insertQuery.setInt(1, newOvertid.getBrukerId());
            insertQuery.setDouble(2, newOvertid.getAntTimer());
            insertQuery.setInt(3, newOvertid.getVaktId());
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
            String updateSql = "UPDATE overtid SET bruker_id = ?, ant_timer = ?, vakt_id = ?, kommentar = ? WHERE overtid_id = ?";
            updateQuery = connection.prepareStatement(updateSql);


            updateQuery.setInt(1, overtid.getBrukerId());
            updateQuery.setDouble(2, overtid.getAntTimer());
            updateQuery.setInt(3, overtid.getVaktId());
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
            String sql = "INSERT INTO tilgjengelighet(bruker_id, fra_tid, til_tid) VALUES(?,?,?);";
            insertQuery = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            insertQuery.setInt(1, newTilgjengelighet.getUserId());
            insertQuery.setTimestamp(2, Timestamp.valueOf(newTilgjengelighet.getFraTid()));
            insertQuery.setTimestamp(3, Timestamp.valueOf(newTilgjengelighet.getTilTid()));
            insertQuery.execute();

            ResultSet res = insertQuery.getGeneratedKeys();
            res.next();
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

	public Tilgjengelighet[] selectAllTilgjengelighetDate(LocalDateTime fratid, LocalDateTime tiltid) {
		ResultSet res = null;
		ArrayList<Tilgjengelighet> tilgjengelighet = new ArrayList<>();


		try {
			String selectSql = "SELECT * FROM tilgjengelighet WHERE fra_tid > ? AND til_tid < ?";
			selectQuery = connection.prepareStatement(selectSql);
			selectQuery.setTimestamp(1, Timestamp.valueOf(fratid));
			selectQuery.setTimestamp(2, Timestamp.valueOf(tiltid));
			System.out.println(Timestamp.valueOf(fratid));
			System.out.println(Timestamp.valueOf(tiltid));

			res = selectQuery.executeQuery();
			while (res.next()) {
				tilgjengelighet.add(new Tilgjengelighet(
						res.getInt("bruker_id"),
						res.getTimestamp("fra_tid").toLocalDateTime(),
						res.getTimestamp("til_tid").toLocalDateTime()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlCleanup.closeEverything(res, selectQuery, connection);
		}
		return tilgjengelighet.toArray(new Tilgjengelighet[tilgjengelighet.size()]);
	}






    public static void main(String[] args) {
		SqlQueries query = new SqlQueries();
//		System.out.println();
//		System.out.println(Arrays.toString(query.selectVakterAvdeling(1)));
	}
}
