package com.example.myapplication.tools;

//Creo l'oggetto pilota che userò per la visualizzazione
public class ObjectPilot implements Comparable<ObjectPilot> {


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

    public String getTEAM() {
        return TEAM;
    }

    public void setTEAM(String TEAM) {
        this.TEAM = TEAM;
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
    public String TEAM;
    public  String CAR;
    public Integer POINTS;


    public ObjectPilot(Integer ID_CHAMP, String NAME, String TEAM, String CAR, Integer POINTS) {
        this.ID_CHAMP = ID_CHAMP;
        this.NAME = NAME;
        this.TEAM = TEAM;
        this.CAR = CAR;
        this.POINTS = POINTS;
    }

    @Override
    public int compareTo(ObjectPilot u) {
        if (getPOINTS() == null || u.getPOINTS() == null) {
            return 0;
        }
        return getPOINTS().compareTo(u.getPOINTS());
    }


}
