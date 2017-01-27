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
public class Tilgjengelighet {

    private int userId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
	private
	LocalDateTime fraTid;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime tilTid;


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
