package com.example.database_classes;

import java.time.LocalDateTime;

/**
 * Created by axelkvistad on 10/01/17.
 */
public class Vakt {
    private int vaktId, vaktansvarligId, avdelingId, antPers;
    private LocalDateTime fraTid;
    private LocalDateTime tilTid;

    public Vakt(int vaktId, int vaktansvarligId, int avdelingId) {
        this.vaktId = vaktId;
        this.vaktansvarligId = vaktansvarligId;
        this.avdelingId = avdelingId;
    }


    public Vakt(int vaktId, int vaktansvarligId, int avdelingId, LocalDateTime fraTid, LocalDateTime tilTid, int antPers) {
        this.vaktId = vaktId;
        this.vaktansvarligId = vaktansvarligId;
        this.avdelingId = avdelingId;
        this.fraTid = fraTid;
        this.tilTid = tilTid;
        this.antPers = antPers;
    }

    /**
     * Konstruktør for å skape et Vaktobjekt hentet fra databasen
     *
     * @param vaktansvarligId
     * @param avdelingId
     * @param fraTid
     * @param tilTid
     * @param antPers
     */

    public Vakt(int vaktansvarligId, int avdelingId, LocalDateTime fraTid, LocalDateTime tilTid, int antPers) {
        this.vaktansvarligId = vaktansvarligId;
        this.avdelingId = avdelingId;
        this.fraTid = fraTid;
        this.tilTid = tilTid;
        this.antPers = antPers;
    }

    public int getVaktId() {
        return vaktId;
    }

    public void setVaktId(int vaktId) {
        this.vaktId = vaktId;
    }

    public int getVaktansvarligId() {
        return vaktansvarligId;
    }

    public void setVaktansvarligId(int vaktansvarligId) {
        this.vaktansvarligId = vaktansvarligId;
    }

    public int getAvdelingId() {
        return avdelingId;
    }

    public void setAvdelingId(int avdelingId) {
        this.avdelingId = avdelingId;
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

    public int getAntPers() {
        return antPers;
    }

    public void setAntPers(int antPers) {
        this.antPers = antPers;
    }
}
