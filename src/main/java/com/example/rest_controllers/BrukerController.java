package com.example.rest_controllers;

import com.example.database_classes.*;
import com.example.security.TokenManager;
import com.example.sql_folder.SqlQueries;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by Jens on 11-Jan-17.
 */
@CrossOrigin
@RestController
public class BrukerController {
    SqlQueries query = new SqlQueries();

    @RequestMapping("/bruker/{id}")
	public Bruker getBruker(@PathVariable("id") Integer id) {
		return query.selectBruker(id);
	}

	@RequestMapping("/bruker/alle")
	public Bruker[] getBrukere() {
		return query.selectBrukere();
	}

	//TODO implement this for every request
	@RequestMapping("/bruker/alletokentest")
	public Bruker[] getBrukereTokenTest(@RequestHeader (value = "token") String token) {
    	if (TokenManager.verifiser(token)) {
			return query.selectBrukere();
		}
		else {
    		return null;
		}
	}

	@RequestMapping(value="/bruker/delete", method=RequestMethod.POST)
	public boolean deleteBruker(@RequestBody Bruker bruker) {
		return query.deleteBruker(bruker.getBrukerId());
	}

	@RequestMapping(value="/bruker/add", method=RequestMethod.POST)
	public boolean addBruker(@RequestBody Bruker bruker) {
		try {
            return query.insertBruker(bruker);
        } catch (IllegalArgumentException e) {
			System.out.println("hei");
			e.printStackTrace();
        }
        return false;
	}

	@RequestMapping(value="/bruker/update", method=RequestMethod.POST)
	public boolean updateBruker(@RequestBody Bruker bruker) {
		return query.updateBruker(bruker);
	}

	//Gets hours worked in month, rounded up
	@RequestMapping("/bruker/timer/{year}/{month}")
	public int getTimerBruker(@RequestBody Bruker bruker, @PathVariable("year") int year, @PathVariable("month") int month) {
    	Vakt[] vakter = query.selectMånedVakterBruker(bruker.getBrukerId(), year, month);
    	double antTimer = 0;
    	for (Vakt v : vakter) {
    		antTimer += Math.ceil(ChronoUnit.HOURS.between(v.getFraTid(), v.getTilTid())) + 1;
		}
		System.out.println("Timer fra vakter: " + antTimer);
		Overtid[] overtider = query.selectOvertiderBruker(bruker.getBrukerId());
    	for (Overtid o : overtider) {
    		antTimer += o.getAntTimer();
		}
		return (int)antTimer;
	}

	public static void main(String[] args) {
		BrukerController controller = new BrukerController();
		System.out.println(controller.addBruker(new Bruker(1, 0, 1, 1, 12345678, 100, 100, true, "admin", "admin", "admin", "Admin@@@")));
	}

}
