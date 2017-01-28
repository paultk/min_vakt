package com.example.rest_controllers;

import com.example.database_classes.Bruker;
import com.example.database_classes.Fravaer;
import com.example.database_classes.Overtid;
import com.example.database_classes.Vakt;
import com.example.security.TokenManager;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by Jens on 11-Jan-17.
 */
//@CrossOrigin
@RestController
public class BrukerController {
    private SqlQueries query = new SqlQueries();

    @RequestMapping(value="/bruker/{id}", method = RequestMethod.GET)
	public Bruker getBruker(@RequestHeader (value = "token") String token, @PathVariable("id") Integer id) throws AuthException {
    	if (TokenManager.verifiser(token)) {
			return query.selectBruker(id);
		}
		else {
    		throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping("/bruker/alle")
	public Bruker[] getBrukere(@RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.selectBrukere();
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	/*
	@RequestMapping("/bruker/alletokentest")
	public Bruker[] getBrukereTokenTest(@RequestHeader (value = "token") String token) {
    	if (TokenManager.verifiser(token)) {
			return query.selectBrukere();
		}
		else {
    		return null;
		}
	}*/

	@RequestMapping(value="/bruker/delete", method=RequestMethod.POST)
	public boolean deleteBrukerId(@RequestHeader (value = "token") String token, @RequestBody Bruker bruker) throws AuthException {
		System.out.println("yo");
		if (TokenManager.verifiser(token)) {
			return query.deleteBruker(bruker.getBrukerId());
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping(value="/bruker/add", method=RequestMethod.POST)
	public boolean addBruker(@RequestHeader (value = "token") String token, @RequestBody Bruker bruker) throws AuthException {
		if (TokenManager.verifiser(token)) {
			try {
				return query.insertBruker(bruker);
			} catch (IllegalArgumentException e) {
				System.out.println("hei");
				e.printStackTrace();
			}
			return false;
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping(value="/bruker/update", method=RequestMethod.POST)
	public boolean updateBruker(@RequestHeader (value = "token") String token, @RequestBody Bruker bruker) throws AuthException {
		if (TokenManager.verifiser(token)) {
			return query.updateBruker(bruker);
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	//Gets hours worked in month, rounded up
	@RequestMapping("/bruker/timer/{year}/{month}")
	public int getTimerBruker(@RequestHeader (value = "token") String token, @RequestBody Bruker bruker,
							  @PathVariable("year") int year, @PathVariable("month") int month) throws AuthException {
		if (TokenManager.verifiser(token)) {
			Vakt[] vakter = query.selectMånedVakterBruker(bruker.getBrukerId(), year, month);
			double antTimer = 0;
			for (Vakt v : vakter) {
				antTimer += Math.floor(ChronoUnit.HOURS.between(v.getFraTid(), v.getTilTid()) + 1);
				Fravaer fravær = query.selectFravaerFromVaktBruker(bruker.getBrukerId(), v.getVaktId());
				antTimer -= (Math.floor(ChronoUnit.HOURS.between(fravær.getFraTid(), fravær.getTilTid()) + 1));
				Overtid[] overtider = query.selectOvertiderBrukerVakt(bruker.getBrukerId(), v.getVaktId());
				for (Overtid o : overtider) {
					antTimer += o.getAntTimer();
				}
			}
			System.out.println("Timer fra vakter: " + antTimer);
			return (int)antTimer;
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping("/bruker/stillingstimer/{year}/{month}")
	public int getStillingstimerBruker(@RequestHeader (value = "token") String token, @RequestBody Bruker bruker,
									   @PathVariable("year") int year, @PathVariable("month") int month) throws AuthException {
		if (TokenManager.verifiser(token)) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(year + "-" + month + "-01", dtf);
			return bruker.getTotalMonthHours(date);
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping("/bruker/epost/{epost}")
	public Bruker getBrukerByEpost(@PathVariable("epost") String epost, @RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			epost = epost.replaceAll("&at", "@");
			epost = epost.replaceAll("&dot", ".");
			return query.selectBruker(epost);
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	@RequestMapping(value="/bruker/addtid/{tilTid}/{avdId}", method = RequestMethod.POST)
	public boolean addBrukerTid(@RequestBody Bruker bruker, @PathVariable ("tilTid") String tid, @PathVariable("avdId") int avdId,
								@RequestHeader (value = "token") String token) throws AuthException {
		if (TokenManager.verifiser(token)) {
			DateTimeFormatter aDateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
			LocalDateTime tidInn = LocalDateTime.parse(tid, aDateTimeFormatter);
			return query.insertVaktTid(bruker, tidInn, avdId);
		}
		else {
			throw new AuthException("Token not authenticated");
		}
	}

	/*public static void main(String[] args) {
		BrukerController controller = new BrukerController();
		System.out.println(controller.addBruker(new Bruker(1, 0, "Sykepleier", 1, 12345678, 100, 100, true, "admin", "admin", "admin", "Admin@@@")));
	}*/

}
