package com.example.myapplication.tools;

import androidx.annotation.NonNull;

public class User {
    private static Integer ID;
    private static String NAME;
    private static String LASTNAME;
    private static String BIRTHDATE;
    private static String FULLADDRESS;
    private static String CITY;
    private static String POSTALCODE;
    private static String EMAIL;
    private static String PASSWORD;
    private static String RACENUMBER;
    private static String LOVED_CIRCUIT;
    private static String HATED_CIRCUIT;
    private static String LOVED_CAR;
    private static String PROFILEPHOTO;


    public User (Integer _id, String _name, String _lastname, String _birthdate, String _fullAddress,  String _city,
                                  String _postalCode, String _email, String _password, String _raceNumber, String _lovedCircuit, String _hatedCircuit, String _lovedCar, String _profilephoto) {
        ID = _id;
        NAME = _name;
        LASTNAME = _lastname;
        BIRTHDATE = _birthdate;
        FULLADDRESS = _fullAddress;
        CITY = _city;
        POSTALCODE = _postalCode;
        EMAIL = _email;
        PASSWORD = _password;
        RACENUMBER = _raceNumber;
        LOVED_CIRCUIT = _lovedCircuit;
        HATED_CIRCUIT = _hatedCircuit;
        LOVED_CAR = _lovedCar;
        PROFILEPHOTO = _profilephoto;

    }
    public static Integer getID() {
        return ID;
    }

    public static void setID(Integer ID) {
        User.ID = ID;
    }

    public static String getNAME() {
        return NAME;
    }

    public static void setNAME(String NAME) {
        User.NAME = NAME;
    }

    public static String getLASTNAME() {
        return LASTNAME;
    }

    public static void setLASTNAME(String LASTNAME) {
        User.LASTNAME = LASTNAME;
    }

    public static String getBIRTHDATE() {
        return BIRTHDATE;
    }

    public static void setBIRTHDATE(String BIRTHDATE) {
        User.BIRTHDATE = BIRTHDATE;
    }

    public static String getFULLADDRESS() {
        return FULLADDRESS;
    }

    public static void setFULLADDRESS(String FULLADDRESS) {
        User.FULLADDRESS = FULLADDRESS;
    }

    public static String getCITY() {
        return CITY;
    }

    public static void setCITY(String CITY) {
        User.CITY = CITY;
    }

    public static String getPOSTALCODE() {
        return POSTALCODE;
    }

    public static void setPOSTALCODE(String POSTALCODE) {
        User.POSTALCODE = POSTALCODE;
    }

    public static String getEMAIL() {
        return EMAIL;
    }

    public static void setEMAIL(String EMAIL) {
        User.EMAIL = EMAIL;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String PASSWORD) {
        User.PASSWORD = PASSWORD;
    }

    public static String getRACENUMBER() {
        return RACENUMBER;
    }

    public static void setRACENUMBER(String RACENUMBER) {
        User.RACENUMBER = RACENUMBER;
    }

    public static String getLovedCircuit() {
        return LOVED_CIRCUIT;
    }

    public static void setLovedCircuit(String lovedCircuit) {
        User.LOVED_CIRCUIT = lovedCircuit;
    }

    public static String getHatedCircuit() {
        return HATED_CIRCUIT;
    }

    public static void setHatedCircuit(String hatedCircuit) {
        User.HATED_CIRCUIT = hatedCircuit;
    }

    public static String getLovedCar() {
        return LOVED_CAR;
    }

    public static void setLovedCar(String lovedCar) {
        User.LOVED_CAR = lovedCar;
    }

    public static String getPROFILEPHOTO() {
        return PROFILEPHOTO;
    }

    public static void setPROFILEPHOTO(String PROFILEPHOTO) {
        User.PROFILEPHOTO = PROFILEPHOTO;
    }

    @NonNull
    @Override
    public String toString() {
        return "id: " + getID() + "email: " + getEMAIL() + "password: " + getPASSWORD() ;
    }
}


