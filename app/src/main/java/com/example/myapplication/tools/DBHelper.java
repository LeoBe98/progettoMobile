package com.example.myapplication.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "app_db";
    public static final String TABLE_USERS = "Users";
    public static final String TABLE_STATUS = "Status";
    public static final String TABLE_CHAMPIONSHIP = "Championship";
    public static final String TABLE_CALENDAR = "Calendar";
    public static final String TABLE_INSCRIPTION = "Iscription";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String user = "CREATE TABLE Users (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, lastname TEXT, birthdate TEXT," +
                "fullAddress TEXT, city TEXT, postalCode TEXT," +
                " email TEXT UNIQUE, password TEXT, raceNumber TEXT, lovedCircuit TEXT, hatedCircuit TEXT, lovedCar TEXT, profilePhoto TEXT)";
        db.execSQL(user);

        //Riempio db con dati forniti
        String championship = "CREATE TABLE Championship (id INTEGER PRIMARY KEY, " +
                "name TEXT, logo TEXT, flags TEXT, fuel_consumption TEXT, tires_consumption TEXT, help TEXT, car_list TEXT) ";
        db.execSQL(championship);
        populationChampionship(db);
        String calendar = "CREATE TABLE Calendar (id INTEGER PRIMARY KEY AUTOINCREMENT, idCamp INTEGER, circuit TEXT, data TEXT, FOREIGN KEY (idCamp) REFERENCES Championship(id))";
        db.execSQL(calendar);
        populationCalendar(db);


        //Creo tabella iscrizioni
        String iscription = "CREATE TABLE Iscription (idIscription INTEGER PRIMARY KEY AUTOINCREMENT, idUser INTEGER, idCamp INTEGER, FOREIGN KEY (idCamp) REFERENCES Championship(id), FOREIGN KEY (idUser) REFERENCES Users(id) ) ";
        db.execSQL(iscription);

      /*  String pilots = "CREATE TABLE Pilots (idPilot INTEGER PRIMARY KEY AUTOINCREMENT, idcamp INTEGER, name TEXT, team TEXT, car TEXT, FOREIGN KEY (idCamp) REFERENCES Championship(id))";
        db.execSQL(pilots);



        String rankPilot = "CREATE TABLE Rank_Pilot (idCamp INTEGER, idPilot INTEGER, pointS INTEGER, car STRING)";
        db.execSQL(rankPilot);
        String rankTeam = "CREATE TABLE Rank_Pilot (idCamp INTEGER, idPilot INTEGER, pointS INTEGER, car STRING)";
        db.execSQL(rankTeam);*/

        String status = "CREATE TABLE Status (status INTEGER, userId INTEGER)";
        String insertStatus = "INSERT INTO status (Status, userId) VALUES (0, -1)";
        db.execSQL(status);
        db.execSQL(insertStatus);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAMPIONSHIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALENDAR);

        onCreate(db);
    }

    //USERS
    public long addUser(String name, String lastname, String birthdate, String fullAddress, String city, String postalCode, String email, String password, String raceNumber, String lovedCircuit, String hatedCircuit, String lovedCar, String profilePhoto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("lastname", lastname);
        cv.put("birthdate", birthdate);
        cv.put("fullAddress", fullAddress);
        cv.put("city", city);
        cv.put("postalCode", postalCode);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("raceNumber", raceNumber);
        cv.put("lovedCircuit", lovedCircuit);
        cv.put("hatedCircuit", hatedCircuit);
        cv.put("lovedCar", lovedCar);
        cv.put("profilePhoto", profilePhoto);
        long res = db.insert(TABLE_USERS, null, cv);
        Log.e("debug db", ""+res);
        db.close();
        return res;
    }

    public Cursor getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE users.email = " + email;
        String[] columns = {"id", "name", "lastname", "birthdate", "fullAddress", "city", "postalCode", "email", "password", "raceNumber", "lovedCircuit", "hatedCircuit", "lovedCar", "profilePhoto"};
        String selection = "email" + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USERS, null, "users.email = '" +email+"'", null, null, null, null);
        String[] columsArray = cursor.getColumnNames();
        Log.e("DBHelper.getUser", columsArray.toString());
        return cursor;
    }

    public Cursor getUserFromId(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, "users.id = '" +id+"'", null, null, null, null);
        String[] columsArray = cursor.getColumnNames();
        Log.e("DBHelper.getUserFromId", columsArray.toString());
        return cursor;

    }

    //STATUS
    public Cursor getStatus() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"status, userId"};
        Cursor cursor = db.query(TABLE_STATUS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int updateStatus(int status, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);
        contentValues.put("userId", userId);
        int columnsAffected = db.update(TABLE_STATUS, contentValues, null, null);
        return columnsAffected;
    }



    //metodi di popula
    public void populationChampionship(SQLiteDatabase db){
        String insertChampionship1 = "INSERT INTO Championship (id, name, logo, flags, fuel_consumption, tires_consumption, help, car_list) VALUES (0, 'Leon Supercopa', 'logo', 'Solo nere', 'Normale', 'Normale', 'Frizione automatica', 'Seat Leon' )";
        String insertChampionship2 = "INSERT INTO Championship (id, name, logo, flags, fuel_consumption, tires_consumption, help, car_list) VALUES (1, 'International TCT', 'logo', 'Nere e blu', 'Normale', 'Normale', 'Frizione automatica, ABS', 'VW Golf, Honda Civic, Peugeot 308, Hyundai i30n, Alfa Romeo Giulietta' )" ;
        db.execSQL(insertChampionship1);
        db.execSQL(insertChampionship2);

    }

    public void populationCalendar(SQLiteDatabase db){
        String insertCalendar1 = "INSERT INTO Calendar (idCamp, circuit, data) VALUES (0, 'Jerez', '04/07/2019')";
        String insertCalendar2 = "INSERT INTO Calendar (idCamp, circuit, data) VALUES (0, 'Barcellona', '11/07/2019')";
        String insertCalendar3 = "INSERT INTO Calendar (idCamp, circuit, data) VALUES (0, 'Valencia', '18/07/2019')";
        String insertCalendar4 = "INSERT INTO Calendar (idCamp, circuit, data) VALUES (0, 'Portimao', '25/07/2019')";

        String insertCalendar5 = "INSERT INTO Calendar (idCamp, circuit, data) VALUES (1, 'Sepang', '30/01/2020')";
        String insertCalendar6 = "INSERT INTO Calendar (idCamp, circuit, data) VALUES (1, 'Buriram', '13/02/2020')";
        String insertCalendar7 = "INSERT INTO Calendar (idCamp, circuit, data) VALUES (1, 'Suzuka', '27/02/2020')";
        String insertCalendar8 = "INSERT INTO Calendar (idCamp, circuit, data) VALUES (1, 'Zhejiang', '12/03/2020')";
        String insertCalendar9 = "INSERT INTO Calendar (idCamp, circuit, data) VALUES (1, 'Zandvoort', '26/03/2020')";
        String insertCalendar10 = "INSERT INTO Calendar (idCamp, circuit, data) VALUES (1, 'Hungaroring', '09/04/2020')";
        String insertCalendar11 = "INSERT INTO Calendar (idCamp, circuit, data) VALUES (1, 'Adria', '23/05/2020')";

        db.execSQL(insertCalendar1);
        db.execSQL(insertCalendar2);
        db.execSQL(insertCalendar3);
        db.execSQL(insertCalendar4);
        db.execSQL(insertCalendar5);
        db.execSQL(insertCalendar6);
        db.execSQL(insertCalendar7);
        db.execSQL(insertCalendar8);
        db.execSQL(insertCalendar9);
        db.execSQL(insertCalendar10);
        db.execSQL(insertCalendar11);


    }
}
