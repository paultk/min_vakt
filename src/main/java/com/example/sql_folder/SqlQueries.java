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

	private PreparedStatement selectQuery;
	private PreparedStatement insertQuery;
	private PreparedStatement updateQuery;
	private PreparedStatement deleteQuery;

	private Connection connection;

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
	public boolean insertVaktTid(Bruker bruker, LocalDateTime tid, int avdelingId) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM vakt WHERE til_tid = ? AND avdeling_id = ?");
			selectQuery.setTimestamp(1, Timestamp.valueOf(tid));
			selectQuery.setInt(2, avdelingId);
			ResultSet res = selectQuery.executeQuery();
			if (res.next()) {
				System.out.println("Funnet vaktid: "+res.getInt("vakt_id"));
				return insertBrukerVakt(bruker.getBrukerId(), res.getInt("vakt_id"));
			}
			System.out.println("Ikke funnet");
			LocalDateTime fraTid = tid.minusHours(8);
			return insertVakt(new Vakt(bruker.getBrukerId(), avdelingId, fraTid, tid, 5));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

    public Bruker selectBruker(String epost) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM bruker WHERE epost = ?");
			selectQuery.setString(1, epost);
			ResultSet res = selectQuery.executeQuery();
			if (res.next()) {
				return new Bruker(
						res.getInt("bruker_id"),
						res.getInt("passord_id"),
						res.getString("stilling_beskrivelse"),
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
                        res.getString("stilling_beskrivelse"),
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
						res.getString("stilling_beskrivelse"),
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

	public boolean resetBrukerPassord(Bruker bruker) {

		try {
			PasswordEmail passwordEmail = new PasswordEmail();
			passwordEmail.setNewPassword();
			bruker.setPlaintextPassord(passwordEmail.getNewPassword());
			Passord newPassord = new Passord(bruker.getSalt(), bruker.getHash());
			insertPassord(newPassord);
			int passId = selectPassordId(bruker.getHash(), bruker.getSalt());
			bruker.setPassordId(passId);
			passwordEmail.sendMail(bruker.getEpost());
			return updateBruker(bruker);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBruker(Bruker bruker) {
		try {
			updateQuery = connection.prepareStatement("UPDATE  bruker SET \n" +
					"stilling_beskrivelse =  ?,\n" +
					"avdeling_id =  ?,\n" +
					"fornavn =  ?,\n" +
					"etternavn =  ?,\n" +
					"timelonn =  ?,\n" +
					"telefonnr =  ?,\n" +
					"epost = ?,\n" +
					"stillingsprosent =  ?,\n" +
					"admin =  ?\n" +
                    " WHERE bruker_id = ?;");
			updateQuery.setString(1, bruker.getStillingsBeskrivelse());
			updateQuery.setInt(2, bruker.getAvdelingId());
			updateQuery.setString(3, bruker.getFornavn());
			updateQuery.setString(4, bruker.getEtternavn());
			updateQuery.setDouble(5, bruker.getTimelonn());
			updateQuery.setInt(6, bruker.getTelefonNr());
			updateQuery.setString(7, bruker.getEpost());
			updateQuery.setInt(8, bruker.getStillingsProsent());
			updateQuery.setBoolean(9, bruker.isAdmin());
			updateQuery.setInt(10, bruker.getBrukerId());
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
					"stilling_beskrivelse, avdeling_id, fornavn, etternavn, timelonn, telefonnr, " +
					"epost, stillingsprosent, admin) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			insertQuery.setInt(1, bruker.getPassordId());
			insertQuery.setString(2, bruker.getStillingsBeskrivelse());
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
		}
		return false;
    }
	public boolean deleteBruker(int id) {

		try {
			//slette fravær
			deleteFravaer(id);
			//slette overtid
			deleteOvertid(id);
			//slette brukervakter
			deleteBrukerVakt(id);
			//slette meldinger
			deleteMeldingTilOgFra(id);
			//sette vaktansvarlig
			updateQuery = connection.prepareStatement("UPDATE vakt SET vaktansvarlig_id = NULL WHERE" +
					" vaktansvarlig_id = ?");
			updateQuery.setInt(1, id);
			updateQuery.executeUpdate();
			//slette tilgjengelighet
			deleteTilgjengelighet(id);
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
	public boolean deleteBruker(Bruker bruker) {
		try {
			String deleteSql = "DELETE FROM bruker WHERE bruker_id = ?";
			deleteQuery = connection.prepareStatement(deleteSql);
			deleteQuery.setInt(1, bruker.getBrukerId());

			if (deleteQuery.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public Bruker selectVaktAnsvarlig(int vaktId) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM bruker WHERE bruker_id IN (" +
					"SELECT vaktansvarlig_id FROM vakt WHERE vakt_id = ?)");
			selectQuery.setInt(1, vaktId);
			ResultSet res = selectQuery.executeQuery();
			if (res.next()) {
				return new Bruker(
						res.getInt("bruker_id"),
						res.getInt("passord_id"),
						res.getString("stilling_beskrivelse"),
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

	/*
    *
    * BRUKERVAKT
    *
    */

	public boolean checkExistsBrukerVakt(int brukerId, int vaktId) {
	    try {
	        String selectSql = "SELECT * FROM bruker_vakt WHERE bruker_id = ? AND vakt_id = ?";
	        selectQuery = connection.prepareStatement(selectSql);
	        selectQuery.setInt(1, brukerId);
	        selectQuery.setInt(2, vaktId);
	        ResultSet res = selectQuery.executeQuery();

	        return res.next();

        } catch (SQLException e) {
	        e.printStackTrace();
        }
        return false;
    }

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

	public boolean deleteBrukerVakt(int brukerId) {
		try {
			deleteQuery = connection.prepareStatement("DELETE FROM bruker_vakt WHERE bruker_vakt.bruker_id = ?");
			deleteQuery.setInt(1, brukerId);
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
				BrukerVakt brk = new BrukerVakt(res.getInt("bruker_vakt_id"), res.getInt("bruker_id"), res.getInt("vakt_id"));
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

	public BrukerVakt[] selectBrukerVakter(int brukerId) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM bruker_vakt WHERE bruker_id = ?");
			selectQuery.setInt(1, brukerId);
			ResultSet res = selectQuery.executeQuery();
			ArrayList<BrukerVakt> brukerVakter = new ArrayList<>();
			while (res.next()) {
				BrukerVakt brk = new BrukerVakt(res.getInt("bruker_vakt_id"), res.getInt("bruker_id"), res.getInt("vakt_id"));
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
						res.getString("stilling_beskrivelse"),
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
						res.getString("stilling_beskrivelse"),
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
		int fullEmploymentHours = (int) Math.round(37.5 * localDate.lengthOfMonth() / 7);
		System.out.println("fullEmploymentHours: " + fullEmploymentHours);
		ValueRange range = localDate.range(ChronoField.DAY_OF_MONTH);
		Long max = range.getMaximum();
		LocalDateTime startOfMonth = localDate.withDayOfMonth(1).atStartOfDay();
		LocalDateTime endOfMonth = localDate.withDayOfMonth(max.intValue()).atTime(23, 59, 59);

		ArrayList<Vakt> vaktList = new ArrayList<>();
		ResultSet res = null;

		try {
			String selectSql =
					"SELECT v.*, b.timelonn, b.stillingsprosent, o.ant_timer " +
							"FROM bruker b " +
								"JOIN bruker_vakt bv ON b.bruker_id = bv.bruker_id " +
								"JOIN vakt v ON v.vakt_id = bv.vakt_id " +
								"LEFT JOIN overtid o ON bv.vakt_id = o.vakt_id AND bv.bruker_id = o.bruker_id " +
							"WHERE b.bruker_id = ? AND v.fra_tid >= ? AND v.til_tid <= ?;";
			selectQuery = connection.prepareStatement(selectSql);

			selectQuery.setInt(1, brukerId);
			selectQuery.setTimestamp(2, Timestamp.valueOf(startOfMonth));
			selectQuery.setTimestamp(3, Timestamp.valueOf(endOfMonth));

			res = selectQuery.executeQuery();

			int employmentPercentage = 0;
			double hourlyWage = 0;
			double overTime = 0;

			while (res.next()) {
				vaktList.add(new Vakt(
						res.getInt("vakt_id"),
						res.getInt("vaktansvarlig_id"),
						res.getInt("avdeling_id"),
						res.getTimestamp("fra_tid").toLocalDateTime(),
						res.getTimestamp("til_tid").toLocalDateTime(),
						res.getInt("ant_pers")));

				employmentPercentage = res.getInt("stillingsprosent");
				hourlyWage = res.getDouble("timelonn");
				overTime += res.getDouble("ant_timer");
			}
			System.out.println("overtime: " + overTime);

			double employmentHours = (fullEmploymentHours * employmentPercentage) / 100;

            System.out.println("employmentHours: " + employmentHours);

			double hoursWorkedThisMonth = 0;
			for (Vakt vakt : vaktList) {
				System.out.println(vakt);
				Long hours = vakt.getFraTid().until(vakt.getTilTid(), ChronoUnit.HOURS);
                System.out.println("hours: " + hours);
                hoursWorkedThisMonth += hours.doubleValue();
			}
            System.out.println("hoursWorkedThisMonth: " + hoursWorkedThisMonth);

			if (hoursWorkedThisMonth > employmentHours) {
                System.out.println("hoursWorkedThisMonth > employmentHours");
                overTime += (hoursWorkedThisMonth - employmentHours);
                return (employmentHours * hourlyWage) + (overTime * hourlyWage * 1.5);
			}

            System.out.println("Regular pay: " + (hoursWorkedThisMonth * hourlyWage));
            System.out.println("Overtime pay: " + (overTime * hourlyWage * 1.5));

			return (hoursWorkedThisMonth * hourlyWage) + (overTime * hourlyWage * 1.5);


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlCleanup.closeResSet(res);
			SqlCleanup.closeStatement(selectQuery);
//			SqlCleanup.closeEverything(res, selectQuery, connection);
		}

		return -1;
	}

	public Bruker getBrukerFromBrukerVakt(int id) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM bruker WHERE bruker_id IN " +
					"(SELECT bruker_id FROM bruker_vakt WHERE bruker_vakt_id = ?)");
			selectQuery.setInt(1, id);
			ResultSet res = selectQuery.executeQuery();
			if (res.next()) {
				return new Bruker(
						res.getInt("bruker_id"),
						res.getInt("passord_id"),
						res.getString("stilling_beskrivelse"),
						res.getInt("avdeling_id"),
						res.getInt("telefonnr"),
						res.getInt("stillingsprosent"),
						res.getDouble("timelonn"),
						res.getBoolean("admin"),
						res.getString("fornavn"),
						res.getString("etternavn"),
						res.getString("epost")
				);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean byttVaktAdmin(int brukerId1, int vaktId, int brukerId2) {
		try {
			String insertSql = "UPDATE bruker_vakt SET bruker_id = ? WHERE bruker_id = ? AND vakt_id = ? ";
			insertQuery = connection.prepareStatement(insertSql);
			insertQuery.setInt(1, brukerId2);
			insertQuery.setInt(2, brukerId1);
			insertQuery.setInt(3, vaktId);
			insertQuery.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
        	SqlCleanup.closeResSet(res);
        	SqlCleanup.closeStatement(selectQuery);
//            SqlCleanup.closeEverything(res, selectQuery, connection);
        }
        return vakter.toArray(new Vakt[vakter.size()]);
    }

    public Vakt[] selectAllVakterDate(LocalDateTime ldt) {
		LocalDateTime startTime = ldt.withHour(0).withMinute(0).withSecond(0);
		LocalDateTime endTime = ldt.plusYears(2); // TODO: 23/01/17 (Axel): bedre løsning her
		return selectAllVakterDate(startTime, endTime);
	}

    public Vakt[] selectAllVakterDate(LocalDateTime fratid, LocalDateTime tiltid) {
        ResultSet res = null;
        ArrayList<Vakt> vakter = new ArrayList<>();

    try {
        String selectSql = "SELECT * FROM vakt WHERE fra_tid BETWEEN ? AND ? AND til_tid <= ?";

        selectQuery = connection.prepareStatement(selectSql);
        selectQuery.setTimestamp(1, Timestamp.valueOf(fratid));
        selectQuery.setTimestamp(2, Timestamp.valueOf(fratid.withHour(23).withMinute(59).withSecond(59)));
        selectQuery.setTimestamp(3, Timestamp.valueOf(tiltid));

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
		SqlCleanup.closeResSet(res);
		SqlCleanup.closeStatement(selectQuery);
//        SqlCleanup.closeEverything(res, selectQuery, connection);
    }
        return vakter.toArray(new Vakt[vakter.size()]);
}

	public VaktMedBruker[] selectAllVakterMonth(LocalDateTime tiltid, int avdId) {
		ResultSet res = null;
		ArrayList<VaktMedBruker> vakter = new ArrayList<>();


		try {
			String selectSql = "SELECT * FROM vakt LEFT OUTER JOIN bruker_vakt ON vakt.vakt_id = bruker_vakt.vakt_id " +
					"WHERE YEAR(til_tid) = ? AND MONTH(til_tid) = ? AND avdeling_id = ?";
			selectQuery = connection.prepareStatement(selectSql);
			selectQuery.setInt(1, tiltid.getYear());
			selectQuery.setInt(2, tiltid.getMonthValue());
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
			SqlCleanup.closeResSet(res);
			SqlCleanup.closeStatement(selectQuery);
//			SqlCleanup.closeEverything(res, selectQuery, connection);
		}
		return vakter.toArray(new VaktMedBruker[vakter.size()]);
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
						res.getInt("vaktansvarlig_id"),
						res.getInt("avdeling_id"),
						res.getTimestamp("fra_tid").toLocalDateTime(),
						res.getTimestamp("til_tid").toLocalDateTime(),
						res.getInt("ant_pers")
						));
			}
			return allVakter.toArray(new Vakt[allVakter.size()]);
    	} catch (SQLException e) {
    		e.printStackTrace();
		} finally {
			SqlCleanup.closeResSet(res);
			SqlCleanup.closeStatement(selectQuery);
//			SqlCleanup.closeEverything(res, selectQuery, connection);
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
        	int id = vakt.getVaktId();
        	deleteQuery = connection.prepareStatement("DELETE FROM bruker_vakt WHERE vakt_id = ?");
	        deleteQuery.setInt(1, id);
	        deleteQuery.executeUpdate();
	        deleteQuery = connection.prepareStatement("DELETE FROM vakt_bytte WHERE vakt_id = ?");
	        deleteQuery.setInt(1, id);
	        deleteQuery.executeUpdate();
            String deleteSql = "DELETE FROM vakt WHERE vakt_id = ?";
            deleteQuery = connection.prepareStatement(deleteSql);
            deleteQuery.setInt(1, id);

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

    public Stilling selectStilling(String sBeskrivelse){

        try{
            String selectSql = "SELECT beskrivelse FROM stilling WHERE beskrivelse = ?";
            selectQuery = connection.prepareStatement(selectSql);
            selectQuery.setString(1, sBeskrivelse);

            ResultSet res = selectQuery.executeQuery();

            if (!res.next()) return null;

            return new Stilling(res.getString("beskrivelse"));

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        return null;
    }


    public boolean updatePassord(int brukerId, Passord passord) {
    	try {
    		String sql = "UPDATE passord SET hash = ?, salt = ? WHERE passord_id IN " +
					"(SELECT passord_id FROM bruker WHERE bruker_id = ?)";
    		updateQuery = connection.prepareStatement(sql);
    		updateQuery.setString(1, passord.getHash());
    		updateQuery.setString(2, passord.getSalt());
    		updateQuery.setInt(3, brukerId);

    		if (updateQuery.executeUpdate() == 1) {
    			return true;
			}
		} catch (SQLException e) {
    		e.printStackTrace();
		}
		return false;
	}

    public boolean updateStilling(Stilling stilling){

        try {
            String sql = "UPDATE stilling SET beskrivelse = ? WHERE beskrivelse = ?";
            PreparedStatement updateQuery = connection.prepareStatement(sql);
            updateQuery.setString(1, stilling.getBeskrivelse());
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
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean deleteStilling(Stilling stilling){
        try {

            String sql = "DELETE FROM stilling WHERE beskrivelse = ?";
            PreparedStatement deleteQuery = connection.prepareStatement(sql);

            deleteQuery.setString(1, stilling.getBeskrivelse());
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

    private int selectPassordId(String hash, String salt) {
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
				return new Passord(res.getInt("passord_id"), res.getString("salt"), res.getString("hash"));
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
				return new Passord(res.getInt("passord_id"), res.getString("salt"), res.getString("hash"));
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
            String selectSql = "SELECT * FROM fravaer WHERE bruker_vakt_id IN (SELECT bruker_vakt_id FROM bruker_vakt WHERE bruker_id = ?)";
            selectQuery = connection.prepareStatement(selectSql);
            selectQuery.setInt(1, brukerId);
            ResultSet res = selectQuery.executeQuery();

            if (!res.next()) return null;
            int brukerVaktId = res.getInt("bruker_vakt_id");
            LocalDateTime fraTid = res.getTimestamp("fra_tid").toLocalDateTime();
            LocalDateTime tilTid = res.getTimestamp("til_tid").toLocalDateTime();
            String kommentar = res.getString("kommentar");

            return new Fravaer(brukerVaktId, fraTid, tilTid, kommentar);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	public Fravaer selectFravaerFromVaktBruker(int brukerId, int vaktID) {

		try {
			String selectSql = "SELECT * FROM fravaer WHERE bruker_vakt_id IN (SELECT bruker_vakt_id FROM bruker_vakt WHERE bruker_id = ? AND vakt_id = ?)";
			selectQuery = connection.prepareStatement(selectSql);
			selectQuery.setInt(1, brukerId);
			selectQuery.setInt(2, vaktID);
			ResultSet res = selectQuery.executeQuery();

			if (!res.next()) return null;
			int vaktId = res.getInt("bruker_vakt_id");
			LocalDateTime fraTid = res.getTimestamp("fra_tid").toLocalDateTime();
			LocalDateTime tilTid = res.getTimestamp("til_tid").toLocalDateTime();
			String kommentar = res.getString("kommentar");

			return new Fravaer(brukerId, fraTid, tilTid, kommentar);


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

    public boolean insertFravaer(Fravaer newFravaer) {
        try {
            String insertSql = "INSERT INTO fravaer(bruker_vakt_id, fra_tid, til_tid, kommentar) VALUES(?,?,?,?)";
            insertQuery = connection.prepareStatement(insertSql);

            // Oversetter LocalDateTime til Timestamp:
            Timestamp fraTid = Timestamp.valueOf(newFravaer.getFraTid());
            Timestamp tilTid = Timestamp.valueOf(newFravaer.getTilTid());

            insertQuery.setInt(1,newFravaer.getBrukerVaktId());
            insertQuery.setTimestamp(2, fraTid);
            insertQuery.setTimestamp(3, tilTid);
            insertQuery.setString(4, newFravaer.getKommentar());
            insertQuery.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateFravaer(Fravaer fravaer) {
        try {
            String updateSql = "UPDATE fravaer SET fra_tid =  ?,til_tid =  ?,kommentar =  ? WHERE  bruker_vakt_id = ?" +
					" AND fra_tid = ? AND til_tid = ?";

            updateQuery = connection.prepareStatement(updateSql);

            // Oversetter LocalDateTime til Timestamp:
            Timestamp fraTid = Timestamp.valueOf(fravaer.getFraTid());
            Timestamp tilTid = Timestamp.valueOf(fravaer.getTilTid());

            updateQuery.setTimestamp(1, fraTid);
            updateQuery.setTimestamp(2, tilTid);
            updateQuery.setString(3, fravaer.getKommentar());
            updateQuery.setInt(4,fravaer.getBrukerVaktId());
			updateQuery.setTimestamp(5, Timestamp.valueOf(fravaer.getFraTid()));
			updateQuery.setTimestamp(6, Timestamp.valueOf(fravaer.getTilTid()));

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
            String deleteSql = "DELETE FROM fravaer WHERE bruker_vakt_id = ? AND fra_tid = ? AND til_tid = ?";
            deleteQuery = connection.prepareStatement(deleteSql);
            deleteQuery.setInt(1, fravaer.getBrukerVaktId());
            deleteQuery.setTimestamp(2, Timestamp.valueOf(fravaer.getFraTid()));
            deleteQuery.setTimestamp(3, Timestamp.valueOf(fravaer.getTilTid()));
            deleteQuery.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

	public boolean deleteFravaer(int brukerId) {
		try {
			String deleteSql = "DELETE FROM fravaer WHERE bruker_vakt_id IN (" +
					"SELECT bruker_vakt_id FROM bruker_vakt WHERE bruker_id = ?)";
			deleteQuery = connection.prepareStatement(deleteSql);
			deleteQuery.setInt(1, brukerId);
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
                            res.getInt("bruker_vakt_id"),
                            res.getTimestamp("fra_tid").toLocalDateTime(),
                            res.getTimestamp("til_tid").toLocalDateTime(),
                            res.getString("kommentar")
                    ));
                }
                return allFravaer.toArray(new Fravaer[allFravaer.size()]);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
				SqlCleanup.closeResSet(res);
				SqlCleanup.closeStatement(selectQuery);
//                SqlCleanup.closeEverything(res, selectQuery, connection);
            }
            return null;
        }

	public Fravaer[] selectFravaerFromVaktId(int vaktId) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM fravaer WHERE bruker_vakt_id IN " +
					"(SELECT bruker_vakt_id FROM bruker_vakt WHERE vakt_id = ?)");
			selectQuery.setInt(1, vaktId);
			ResultSet res = selectQuery.executeQuery();
			ArrayList<Fravaer> fravaer = new ArrayList<>();
			while (res.next()) {
				Fravaer frv = new Fravaer(
						res.getInt("bruker_vakt_id"),
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
            selectQuery = connection.prepareStatement("SELECT * FROM fravaer WHERE bruker_vakt_id IN " +
                    "(SELECT bruker_vakt_id FROM bruker_vakt WHERE bruker_id = ?)");
            selectQuery.setInt(1, brukerId);
            ResultSet res = selectQuery.executeQuery();
            ArrayList<Fravaer> fravaer = new ArrayList<>();
            while (res.next()) {
                Fravaer frv = new Fravaer(
                        res.getInt("bruker_vakt_id"),
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

    public FravaerMedBrukerOgVakt[] selectFravaersMedBrukerOgVakt() {
    	try {
    		selectQuery = connection.prepareStatement("SELECT fravaer.bruker_vakt_id, fravaer.fra_tid, fravaer.til_tid, " +
					"fravaer.kommentar, bruker_vakt.bruker_id, bruker_vakt.vakt_id FROM fravaer" +
					" JOIN bruker_vakt ON fravaer.bruker_vakt_id = bruker_vakt.bruker_vakt_id");
    		ResultSet res = selectQuery.executeQuery();
    		ArrayList<FravaerMedBrukerOgVakt> fravs = new ArrayList<>();
    		while (res.next()) {
    			fravs.add(new FravaerMedBrukerOgVakt(
						res.getInt("bruker_vakt_id"),
						res.getTimestamp("fra_tid").toLocalDateTime(),
						res.getTimestamp("til_tid").toLocalDateTime(),
						res.getString("kommentar"),
						res.getInt("bruker_id"),
						res.getInt("vakt_id")
				));
			}
			return fravs.toArray(new FravaerMedBrukerOgVakt[fravs.size()]);

		}
		catch (SQLException e) {
    		e.printStackTrace();
		}
		return null;
	}

    /*
    *
    * OVERTID
    *
    */

    public Overtid[] selectOvertiderBrukerVakt(int brukerId, int vaktId) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM overtid WHERE bruker_vakt_id IN (" +
					"SELECT bruker_vakt_id FROM bruker_vakt WHERE bruker_id = ? AND vakt_id = ?)");
			selectQuery.setInt(1, brukerId);
			selectQuery.setInt(2, vaktId);
			ResultSet res = selectQuery.executeQuery();
			ArrayList<Overtid> overtider = new ArrayList<>();
			while (res.next()) {
				Overtid ny = new Overtid(
						res.getInt("overtid_id"),
						res.getInt("bruker_vakt_id"),
						res.getDouble("ant_timer"),
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
            String selectSql = "SELECT * FROM overtid WHERE overtid_id = ?";
            selectQuery = connection.prepareStatement(selectSql);
			selectQuery.setInt(1, overtidId);
            ResultSet res = selectQuery.executeQuery();

            if (!res.next()) return null;

            int brukerVaktId = res.getInt("bruker_vakt_id");
            double antTimer = res.getDouble("ant_timer");
            String kommentar = res.getString("kommentar");

            return new Overtid(overtidId, brukerVaktId, antTimer, kommentar);


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
						res.getInt("bruker_vakt_id"),
						res.getDouble("ant_timer"),
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
            String insertSql = "INSERT INTO overtid(bruker_vakt_id, ant_timer, kommentar) VALUES(?,?,?)";
            insertQuery = connection.prepareStatement(insertSql);
            insertQuery.setInt(1, newOvertid.getBrukerVaktId());
            insertQuery.setDouble(2, newOvertid.getAntTimer());
            insertQuery.setString(3, newOvertid.getKommentar());

            insertQuery.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateOvertid(Overtid overtid) {
        try {
            String updateSql = "UPDATE overtid SET ant_timer = ?, kommentar = ? WHERE overtid_id = ? ";
            updateQuery = connection.prepareStatement(updateSql);


            updateQuery.setDouble(1, overtid.getAntTimer());
            updateQuery.setString(2, overtid.getKommentar());
			updateQuery.setInt(3, overtid.getOvertidId());

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

	public boolean deleteOvertid(int brukerId) {
		try {
			String deleteSql = "DELETE FROM overtid WHERE bruker_vakt_id IN (" +
					"SELECT bruker_vakt_id FROM bruker_vakt WHERE bruker_id = ?)";
			deleteQuery = connection.prepareStatement(deleteSql);
			deleteQuery.setInt(1, brukerId);

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

	public boolean deleteTilgjengelighet(int brukerId) {
		try {
			String deleteSql = "DELETE FROM tilgjengelighet WHERE bruker_id = ?";
			deleteQuery = connection.prepareStatement(deleteSql);
			deleteQuery.setInt(1, brukerId);

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
			SqlCleanup.closeResSet(res);
			SqlCleanup.closeStatement(selectQuery);
//			SqlCleanup.closeEverything(res, selectQuery, connection);
		}
		return tilgjengelighet.toArray(new Tilgjengelighet[tilgjengelighet.size()]);
	}


	/*
    *
    * MELDING
    *
    */

	public boolean insertMelding(Melding melding) {
		try {
			insertQuery = connection.prepareStatement("INSERT INTO `g_scrum06`.`melding` (`fra_bruker_id`, " +
					"`til_bruker_id`, `tid_sendt`, `overskrift`, `melding`) " +
					"VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?);");
			insertQuery.setInt(1, melding.getFraBrukerId());
			insertQuery.setInt(2, melding.getTilBrukerId());
			insertQuery.setString(3, melding.getOverskrift());
			insertQuery.setString(4, melding.getMelding());
			insertQuery.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Melding selectMelding(int meldingId) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM melding WHERE melding_id = ?");
			selectQuery.setInt(1, meldingId);
			ResultSet res = selectQuery.executeQuery();
			if (!res.next()) {
				return null;
			}
			return new Melding(
					res.getInt("melding_id"),
					res.getInt("til_bruker_id"),
					res.getInt("fra_bruker_id"),
					res.getString("overskrift"),
					res.getString("melding"),
					res.getTimestamp("tid_sendt").toLocalDateTime(),
					res.getBoolean("sett")
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Melding[] selectMeldingerToBruker(int brukerId) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM melding WHERE til_bruker_id = ? ORDER BY tid_sendt DESC");
			selectQuery.setInt(1, brukerId);
			ResultSet res = selectQuery.executeQuery();
			ArrayList<Melding> meldinger = new ArrayList<>();
			while (res.next()) {
				meldinger.add(new Melding(
						res.getInt("melding_id"),
						res.getInt("til_bruker_id"),
						res.getInt("fra_bruker_id"),
						res.getString("overskrift"),
						res.getString("melding"),
						res.getTimestamp("tid_sendt").toLocalDateTime(),
						res.getBoolean("sett")
				));
			}
			return meldinger.toArray(new Melding[meldinger.size()]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Melding[] selectUlestMeldingerToBruker(int brukerId) {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM melding WHERE til_bruker_id = ? AND sett = FALSE");
			selectQuery.setInt(1, brukerId);
			ResultSet res = selectQuery.executeQuery();
			ArrayList<Melding> meldinger = new ArrayList<>();
			while (res.next()) {
				meldinger.add(new Melding(
						res.getInt("melding_id"),
						res.getInt("til_bruker_id"),
						res.getInt("fra_bruker_id"),
						res.getString("overskrift"),
						res.getString("melding"),
						res.getTimestamp("tid_sendt").toLocalDateTime(),
						res.getBoolean("sett")
				));
			}
			return meldinger.toArray(new Melding[meldinger.size()]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean setMeldingSett(int id) {
		try {
			updateQuery = connection.prepareStatement("UPDATE  `g_scrum06`.`melding` SET  `sett` =  1 " +
					"WHERE  `melding`.`melding_id` = ?");
			updateQuery.setInt(1, id);
			updateQuery.executeUpdate();
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteMeldingTilOgFra(int tilBrukerId) {
		try {
			deleteQuery = connection.prepareStatement("DELETE FROM melding WHERE til_bruker_id = ? OR fra_bruker_id = ?");
			deleteQuery.setInt(1, tilBrukerId);
			deleteQuery.setInt(2, tilBrukerId);
			deleteQuery.executeUpdate();
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteMelding(Melding melding) {
		try {
			String deleteSql = "DELETE FROM melding WHERE melding_id = ?";
			deleteQuery = connection.prepareStatement(deleteSql);
			deleteQuery.setInt(1, melding.getMeldingId());

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
    * VAKTBYTTE
    *
    */

	public boolean byttVakt(int brukerId1, int vaktId, int brukerId2) {
		try {
			String insertSql = "INSERT INTO vakt_bytte(bruker_id1, vakt_id, bruker_id2) VALUES(?,?,?)";
			insertQuery = connection.prepareStatement(insertSql);
			insertQuery.setInt(1, brukerId1);
			insertQuery.setInt(2, vaktId);
			insertQuery.setInt(3, brukerId2);
			insertQuery.executeUpdate();
			Bruker adminBruker = selectVaktAnsvarlig(vaktId);
			Bruker fraBruker = selectBruker(brukerId1);
			Bruker tilBruker = selectBruker(brukerId2);
			String advarselMelding = "Dette er en automatisk varsel: \n\nBruker " + fraBruker.getFornavn() + " " +
					fraBruker.getEtternavn() + " har sendt inn ønske om å bytte vakt med bruker " + tilBruker.getFornavn() +
					" " + tilBruker.getEtternavn() + ". Du er registrert som vaktansvarlig for denne vakten.";
			insertMelding(new Melding(0,adminBruker.getBrukerId(), fraBruker.getBrukerId(), "Varsel om vaktbytte",
					advarselMelding, null, false));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public VaktBytte[] getVaktBytter() {
		try {
			selectQuery = connection.prepareStatement("SELECT * FROM vakt_bytte");
			ResultSet res = selectQuery.executeQuery();
			ArrayList<VaktBytte> vktbt = new ArrayList<>();
			while (res.next()) {
				vktbt.add(new VaktBytte(
						res.getInt("vaktBytteId"),
						res.getInt("bruker_Id1"),
						res.getInt("vakt_id"),
						res.getInt("bruker_id2")
				));
			}
			return vktbt.toArray(new VaktBytte[vktbt.size()]);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteVaktBytte(int id) {
		try {
			deleteQuery = connection.prepareStatement("DELETE FROM vakt_bytte WHERE vaktBytteId = ?");
			deleteQuery.setInt(1, id);
			deleteQuery.executeUpdate();
			return true;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		SqlQueries query = new SqlQueries();
//		System.out.println();
//		System.out.println(Arrays.toString(query.selectVakterAvdeling(1)));
		/*LocalDateTime fra1 = LocalDateTime.now();
		LocalDateTime til1 = fra1.plusHours(8);
		LocalDateTime fra2 = fra1.plusDays(1);
		LocalDateTime til2 = fra2.plusHours(8);
		LocalDateTime fra3 = fra2.plusDays(1);
		LocalDateTime til3 = fra3.plusHours(8);
		LocalDateTime fra4 = fra3.plusDays(1);
		LocalDateTime til4 = fra4.plusHours(8);

		Vakt vakt1 = new Vakt(16, 16, 2,  fra1, til1, 10);
		Vakt vakt2 = new Vakt(16, 16, 2, fra2, til2, 20);
		Vakt vakt3 = new Vakt(16, 16, 2, fra3, til3, 30);
		Vakt vakt4 = new Vakt(16, 16, 2, fra4, til4, 40);
		query.insertVakt(vakt1);
		query.insertVakt(vakt2);
		query.insertVakt(vakt3);
		query.insertVakt(vakt4);*/
		/*query.insertBrukerVakt(16, 102);
		query.insertBrukerVakt(16, 103);
		query.insertBrukerVakt(16, 104);
		query.insertBrukerVakt(16, 105);*/
		/*Overtid overtid1 = new Overtid(4, 16, 4, 102, "overtid test1");
		Overtid overtid2 = new Overtid(5, 16, 10, 103, "overtid test2");
		query.insertOvertid(overtid1);
		query.insertOvertid(overtid2);*/

		//System.out.println(query.calculateMonthlyWage(16, LocalDate.now()));
		//System.out.println(Arrays.toString(query.selectAllVakterMonth(LocalDateTime.parse("2017-02-01T12:30:00"),1)));

		Bruker bruker = new Bruker("Sykepleier", 3, 90133787, 100, 250,
		true, "Axel", "Kvistad", "axel.b.kvistad@gmail.com", "abcDEF!#");
		query.insertBruker(bruker);
    }
}
