package com.example.myapplication.tools;

public class Team {

    public  Integer ID;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID_CHAMP() {
        return ID_CHAMP;
    }

    public void setID_CHAMP(Integer ID_CHAMP) {
        this.ID_CHAMP = ID_CHAMP;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCAR() {
        return CAR;
    }

    public void setCAR(String CAR) {
        this.CAR = CAR;
    }

    public Integer getPOINTS() {
        return POINTS;
    }

    public void setPOINTS(Integer POINTS) {
        this.POINTS = POINTS;
    }

    public  Integer ID_CHAMP;
    public  String NAME;
    public  String CAR;
    public Integer POINTS;

    public Team(Integer ID, Integer ID_CHAMP, String NAME, String CAR, Integer POINTS) {
        this.ID = ID;
        this.ID_CHAMP = ID_CHAMP;
        this.NAME = NAME;
        this.CAR = CAR;
        this.POINTS = POINTS;
    }


}
