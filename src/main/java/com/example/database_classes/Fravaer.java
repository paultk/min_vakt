package com.example.database_classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Created by paul on 10.01.17.
 */
public class Fravaer {

    private int brukerId, vaktId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime fraTid;
    private LocalDateTime tilTid;

    private String kommentar;

    public Fravaer(int brukerId, int vaktId, LocalDateTime fraTid, LocalDateTime tilTid, String kommentar) {
        this.brukerId = brukerId;
        this.vaktId = vaktId;
        this.fraTid = fraTid;
        this.tilTid = tilTid;
        this.kommentar = kommentar;


    }

    public Fravaer() {}

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

    @Override
    public String toString() {
        return "Fravaer{" +
                "brukerId=" + brukerId +
                ", vaktId=" + vaktId +
                ", fraTid=" + fraTid +
                ", tilTid=" + tilTid +
                ", kommentar='" + kommentar + '\'' +
                '}';
    }
}
