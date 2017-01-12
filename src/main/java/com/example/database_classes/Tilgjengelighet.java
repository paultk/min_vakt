package com.example.database_classes;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by paul on 10.01.17.
 */
public class Tilgjengelighet {

    private int userId;
    LocalDateTime fraTid, tilTid;


    public Tilgjengelighet(int userId, LocalDateTime fraTid, LocalDateTime tilTid) {
        this.userId = userId;
        this.fraTid = fraTid;
        this.tilTid = tilTid;
    }

    public Tilgjengelighet(LocalDateTime fraTid, LocalDateTime tilTid){
        this.fraTid = fraTid;
        this.tilTid= tilTid;
    }

    public Tilgjengelighet(){}

    public int getUserId(){
        return userId;
    }

    public LocalDateTime getFraTid(){
        return fraTid;
    }

    public LocalDateTime getTilTid(){
        return tilTid;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setFraTid(LocalDateTime fraTid){
        this.fraTid = fraTid;
    }

    public void setTilTid(LocalDateTime tilTid){
        this.tilTid = tilTid;
    }
}
