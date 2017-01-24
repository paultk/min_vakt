package com.example.database_classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Created by Knut on 24.01.2017.
 */
public class Melding {
    private int meldingId;
    private int tilBrukerId;
    private int fraBrukerId;
    private  String overskrift;
    private String melding;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime tid_sendt;
    private boolean sett;

    public Melding(int meldingId, int tilBrukerId, int fraBrukerId, String overskrift, String melding, LocalDateTime tid_sendt, boolean sett) {
        this. meldingId = meldingId;
        this.tilBrukerId = tilBrukerId;
        this.fraBrukerId = fraBrukerId;
        this.overskrift = overskrift;
        this.melding = melding;
        this.tid_sendt = tid_sendt;
        this.sett = sett;
    }

    public Melding(){}

    public int getMeldingId() {
        return  meldingId;
    }

    public void setMeldingId(int meldingId) {
        this. meldingId = meldingId;
    }

    public int getTilBrukerId() {
        return tilBrukerId;
    }

    public void setTilBrukerId(int tilBrukerId) {
        this.tilBrukerId = tilBrukerId;
    }

    public int getFraBrukerId() {
        return fraBrukerId;
    }

    public void setFraBrukerId(int fraBrukerId) {
        this.fraBrukerId = fraBrukerId;
    }

    public String getOverskrift() {
        return overskrift;
    }

    public void setOverskrift(String overskrift) {
        this.overskrift = overskrift;
    }

    public String getMelding() {
        return melding;
    }

    public void setMelding(String melding) {
        this.melding = melding;
    }

    public LocalDateTime getTid_sendt() {
        return tid_sendt;
    }

    public void setTid_sendt(LocalDateTime tid_sendt) {
        this.tid_sendt = tid_sendt;
    }

    public boolean isSett() {
        return sett;
    }

    public void setSett(boolean sett) {
        this.sett = sett;
    }

	@Override
	public String toString() {
		return "Melding{" +
				"varselId=" +  meldingId +
				", tilBrukerId=" + tilBrukerId +
				", fraBrukerId=" + fraBrukerId +
				", overskrift='" + overskrift + '\'' +
				", melding='" + melding + '\'' +
				", tid_sendt=" + tid_sendt +
				", sett=" + sett +
				'}';
	}
}
