package com.example.database_classes;

//*
// * Created by Jens on 27-Jan-17.
//

public class FravaerMedBrukerOgVakt {
	private Fravaer fravær;
	private int brukerId;
	private int vaktId;

	public FravaerMedBrukerOgVakt() {
	}

	public FravaerMedBrukerOgVakt(Fravaer fravær, int brukerId, int vaktId) {
		this.fravær = fravær;
		this.brukerId = brukerId;
		this.vaktId = vaktId;
	}

	public Fravaer getFravær() {
		return fravær;
	}

	public void setFravær(Fravaer fravær) {
		this.fravær = fravær;
	}

	public int getBrukerId() {
		return brukerId;
	}

	public void setBrukerId(int brukerId) {
		this.brukerId = brukerId;
	}

	public int getVaktId() {
		return vaktId;
	}

	public void setVaktId(int vaktId) {
		this.vaktId = vaktId;
	}
}
