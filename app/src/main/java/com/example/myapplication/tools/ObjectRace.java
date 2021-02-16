package com.example.myapplication.tools;

//Creo l'oggetto gara che userò per la visualizzazione
public class ObjectRace {

    public  Integer ID;
    public  Integer ID_CHAMP;
    public  String CIRCUIT;
    public  String DATA;


    public ObjectRace(Integer _id, Integer _id_Champ, String _circuit, String _data) {
        this.ID = _id;
        this.ID_CHAMP = _id_Champ;
        this.CIRCUIT = _circuit;
        this.DATA = _data;
    }



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

    public String getCIRCUIT() {
        return CIRCUIT;
    }

    public void setCIRCUIT(String CIRCUIT) {
        this.CIRCUIT = CIRCUIT;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }


}
