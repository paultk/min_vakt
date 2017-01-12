package com.example.rest_controllers;

import com.example.database_classes.*;
import com.example.sql_folder.SqlQueries;
import org.springframework.util.comparator.BooleanComparator;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Jens on 11-Jan-17.
 */
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

	@RequestMapping(value="/bruker/delete", method=RequestMethod.POST)
	public boolean deleteBruker(@RequestBody Bruker bruker) {
		return query.deleteBruker(bruker.getBrukerId());
	}

	@RequestMapping(value="/bruker/add", method=RequestMethod.POST)
	public boolean addBruker(@RequestBody Bruker bruker) {
		return query.insertBruker(bruker);
	}

	@RequestMapping(value="/bruker/setpassord", method=RequestMethod.POST)
	public boolean setPassord(@RequestBody Bruker bruker) {
		return query.updateBrukerPassord(bruker.getBrukerId(), bruker.getPassordId());
	}

	@RequestMapping(value="/bruker/setstilling", method=RequestMethod.POST)
	public boolean setStilling(@RequestBody Bruker bruker) {
		return query.updateBrukerStilling(bruker.getBrukerId(), bruker.getStillingsId());
	}

	@RequestMapping(value="/bruker/setavdeling", method=RequestMethod.POST)
	public boolean setAvdeling(@RequestBody Bruker bruker) {
		return query.updateBrukerAvdeling(bruker.getBrukerId(), bruker.getAvdelingId());
	}

	@RequestMapping(value="/bruker/settelefon", method=RequestMethod.POST)
	public boolean setTelefon(@RequestBody Bruker bruker) {
		return query.updateBrukerTelefon(bruker.getBrukerId(),  bruker.getTelefonNr());
	}

	@RequestMapping(value="/bruker/setstillingprosent", method=RequestMethod.POST)
	public boolean setStillingPros(@RequestBody Bruker bruker) {
		return query.updateBrukerStillingPros(bruker.getBrukerId(),  bruker.getStillingsProsent());
	}

	@RequestMapping(value="/bruker/settimelonn", method=RequestMethod.POST)
	public boolean setTimelonn(@RequestBody Bruker bruker) {
		return query.updateBrukerTimelonn(bruker.getBrukerId(),  bruker.getTimelonn());
	}

	@RequestMapping(value="/bruker/setadmin", method=RequestMethod.POST)
	public boolean setAdmin(@RequestBody Bruker bruker) {
		return query.updateBrukerAdmin(bruker.getBrukerId(),  bruker.isAdmin());
	}

	@RequestMapping(value="/bruker/setfornavn", method=RequestMethod.POST)
	public boolean setFornavn(@RequestBody Bruker bruker) {
		return query.updateBrukerFornavn(bruker.getBrukerId(),  bruker.getFornavn());
	}

	@RequestMapping(value="/bruker/setetternavn", method=RequestMethod.POST)
	public boolean setEtternavn(@RequestBody Bruker bruker) {
		return query.updateBrukerEtternavn(bruker.getBrukerId(),  bruker.getEtternavn());
	}

	@RequestMapping(value="/bruker/setepost", method=RequestMethod.POST)
	public boolean setEpost(@RequestBody Bruker bruker) {
		return query.updateBrukerEpost(bruker.getBrukerId(),  bruker.getEpost());
	}

	@RequestMapping(value="/bruker/addvakt", method=RequestMethod.POST)
	public boolean addBrukerVakt(@RequestBody Bruker bruker, @RequestBody Vakt vakt) {
		return query.insertVaktBruker(bruker.getBrukerId(), vakt.getVaktId());
	}

	@RequestMapping(value="/bruker/deletevakt", method=RequestMethod.POST)
	public boolean deleteBrukerVakt(@RequestBody Bruker bruker, @RequestBody Vakt vakt) {
		return query.deleteVaktBruker(bruker.getBrukerId(), vakt.getVaktId());
	}
}
