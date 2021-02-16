package com.example.myapplication.tools;

//Creo l'oggetto campionato che userò per la visualizzazione
public class ObjectChampionship {
    public  Integer ID;
    public  String NAME;
    public  String LOGO;
    public  String FLAGS;
    public  String FUEL_CONSUMPTION;
    public  String TIRES_CONSUMPTION;
    public  String HELP;
    public  String CAR_LIST;


    public  Integer getID() {
        return ID;
    }

    public  void setID(Integer ID) {
        this.ID = ID;
    }

    public  String getNAME() {
        return NAME;
    }

    public  void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public  String getLOGO() {
        return LOGO;
    }

    public  void setLOGO(String LOGO) {
        this.LOGO = LOGO;
    }

    public  String getFLAGS() {
        return FLAGS;
    }

    public  void setFLAGS(String FLAGS) {
        this.FLAGS = FLAGS;
    }

    public  String getFuelConsumption() {
        return FUEL_CONSUMPTION;
    }

    public  void setFuelConsumption(String fuelConsumption) {
        FUEL_CONSUMPTION = fuelConsumption;
    }

    public  String getTiresConsumption() {
        return TIRES_CONSUMPTION;
    }

    public  void setTiresConsumption(String tiresConsumption) {
        TIRES_CONSUMPTION = tiresConsumption;
    }

    public  String getHELP() {
        return HELP;
    }

    public  void setHELP(String HELP) {
        this.HELP = HELP;
    }

    public  String getCarList() {
        return CAR_LIST;
    }

    public  void setCarList(String carList) {
        CAR_LIST = carList;
    }




    public ObjectChampionship(Integer _id, String _name, String _logo, String _flags, String _fuel_consumption, String _tires_consumption,
                              String _help, String _car_list) {
        ID = _id;
        NAME = _name;
        LOGO = _logo;
        FLAGS = _flags;
        FUEL_CONSUMPTION = _fuel_consumption;
        TIRES_CONSUMPTION = _tires_consumption;
        HELP = _help;
        CAR_LIST = _car_list;


    }


}
