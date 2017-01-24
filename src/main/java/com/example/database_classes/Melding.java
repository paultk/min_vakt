package com.example.database_classes;

import java.time.LocalDateTime;

/**
 * Created by Knut on 24.01.2017.
 */
public class Melding {
    int varselId;
    int tilBrukerId;
    int fraBrukerId;
    String overskrift;
    String melding;
    LocalDateTime dato;

    public Melding(int varselId, int tilBrukerId, int fraBrukerId, String overskrift, String melding, LocalDateTime dato) {
        this.varselId = varselId;
        this.tilBrukerId = tilBrukerId;
        this.fraBrukerId = fraBrukerId;
        this.overskrift = overskrift;
        this.melding = melding;
        this.dato = dato;
    }

    public Melding(int varselId) {}

    public int getVarselId() {
        return varselId;
    }

    public void setVarselId(int varselId) {
        this.varselId = varselId;
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

    public LocalDateTime getDato() {
        return dato;
    }

    public void setDato(LocalDateTime dato) {
        this.dato = dato;
    }
}
