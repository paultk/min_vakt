package com.example.database_classes;

//*
// * Created by Jens on 27-Jan-17.
//

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class FravaerMedBrukerOgVakt {
	private int brukerVaktId;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime fraTid, tilTid;
	private String kommentar;
	private int brukerId;
	private int vaktId;

	public FravaerMedBrukerOgVakt(int brukerVaktId, LocalDateTime fraTid, LocalDateTime tilTid, String kommentar, int brukerId, int vaktId) {
		this.brukerVaktId = brukerVaktId;
		this.fraTid = fraTid;
		this.tilTid = tilTid;
		this.kommentar = kommentar;
		this.brukerId = brukerId;
		this.vaktId = vaktId;
	}

	public FravaerMedBrukerOgVakt() {
	}

	public int getBrukerVaktId() {
		return brukerVaktId;
	}

	public void setBrukerVaktId(int brukerVaktId) {
		this.brukerVaktId = brukerVaktId;
	}

	public LocalDateTime getFraTid() {
		return fraTid;
	}

	public void setFraTid(LocalDateTime fraTid) {
		this.fraTid = fraTid;
	}

	public LocalDateTime getTilTid() {
		return tilTid;
	}

	public void setTilTid(LocalDateTime tilTid) {
		this.tilTid = tilTid;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
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
