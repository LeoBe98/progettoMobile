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
    public static final String TABLE_RANK_PILOT = "RankPilot";
    public static final String TABLE_RANK_TEAM = "RankTeam";


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
        String iscription = "CREATE TABLE Iscription (idIscription INTEGER PRIMARY KEY AUTOINCREMENT, idUser INTEGER, idCamp INTEGER, team TEXT, car TEXT, FOREIGN KEY (idCamp) REFERENCES Championship(id), FOREIGN KEY (idUser) REFERENCES Users(id) ) ";
        db.execSQL(iscription);

        //Creo tabelle per le classifiche
        String pilot = "CREATE TABLE RankPilot (idPilot INTEGER PRIMARY KEY AUTOINCREMENT, idCamp INTEGER, name TEXT, team TEXT, car TEXT, points INTEGER, FOREIGN KEY (idCamp) REFERENCES Championship(id) ) ";
        db.execSQL(pilot);
        String team = "CREATE TABLE RankTeam (idTeam INTEGER PRIMARY KEY AUTOINCREMENT, idCamp INTEGER, name TEXT, car TEXT, points INTEGER, FOREIGN KEY (idCamp) REFERENCES Championship(id) ) ";
        db.execSQL(team);
        populationRank(db);

        //Creo la tabella status per mantenere la sessione
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSCRIPTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RANK_PILOT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RANK_TEAM);
        onCreate(db);
    }

    //region USER
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

    public Cursor getEmail() {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"email"};
        Cursor cursor = db.query(TABLE_USERS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int updateImage(String s, Integer user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("profilePhoto", s);
        int columnsAffected = db.update(TABLE_USERS, contentValues, "users.id = '" +user_id+"'", null);
        return columnsAffected;
    }

    public int updateLovedCircuit(String s, Integer user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lovedCircuit", s);
        int columnsAffected = db.update(TABLE_USERS, contentValues, "users.id = '" +user_id+"'", null);
        return columnsAffected;
    }

    public int updateHatedCircuit(String s, Integer user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hatedCircuit", s);
        int columnsAffected = db.update(TABLE_USERS, contentValues, "users.id = '" +user_id+"'", null);
        return columnsAffected;
    }

    public int updateLovedCar(String s, Integer user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lovedCar", s);
        int columnsAffected = db.update(TABLE_USERS, contentValues, "users.id = '" +user_id+"'", null);
        return columnsAffected;
    }

    public int updateRaceNumber(String s, Integer user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("raceNumber", s);
        int columnsAffected = db.update(TABLE_USERS, contentValues, "users.id = '" +user_id+"'", null);
        return columnsAffected;
    }

    public int updateFlag(String s, Integer champ_id){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("flags", s);
        int columnsAffected = db.update(TABLE_CHAMPIONSHIP, contentValues, "championship.id = '" +champ_id+"'", null);
        return columnsAffected;
    }



    //endregion

    //region STATUS
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

    //endregion

    //region Championship
    public Cursor getChampionship(Integer champ_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from championship where id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(champ_id)});
        String[] columsArray = cursor.getColumnNames();
        return cursor;
    }

    public Cursor getDisponibleChampionship(Integer user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select  C1.* from championship  as C1 except select C2.* from championship as C2 join iscription on C2.id = iscription.idCamp where iscription.idUser = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(user_id)});
        String[] columsArray = cursor.getColumnNames();
        Log.e("DBHelper.getDisponibleChampionship", columsArray[1]);
        return cursor;
    }

    public Cursor getCalendarChampionship(Integer champion_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from Calendar where idCamp = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(champion_id)});
        String[] columsArray = cursor.getColumnNames();
        Log.e("DBHelper.getDisponibleChampionship", columsArray[1]);
        return cursor;
    }

    public Cursor getMyChampionship(Integer user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select championship.* from championship join iscription on championship.id = iscription.idCamp where idUser = ?;";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(user_id)});
        String[] columsArray = cursor.getColumnNames();
        Log.e("DBHelper.getMyChampionship", columsArray[1]);
        return cursor;
    }

    public Cursor getPilotByChampionship(Integer cham_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select RankPilot.* from RankPilot where idCamp = ? order by RankPilot.points desc;";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cham_id)});
        String[] columsArray = cursor.getColumnNames();
        Log.e("DBHelper.getMyChampionship", columsArray[1]);
        return cursor;
    }

    public Cursor getUserRank(Integer cham_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select U.name, U.lastname, I.team, I.car from Iscription I JOIN Users U ON U.id = I.idUser WHERE I.idCamp = ?;";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cham_id)});
        String[] columsArray = cursor.getColumnNames();
        Log.e("DBHelper.getMyChampionship", columsArray[1]);
        return cursor;
    }


    public Cursor getTeamByChampionship(Integer champ_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select RankTeam.* from RankTeam where idCamp = ? order by RankTeam.points desc;";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(champ_id)});
        String[] columsArray = cursor.getColumnNames();
        Log.e("DBHelper.getMyChampionship", columsArray[1]);
        return cursor;
    }

    public Cursor getRace(Integer cal_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " select Calendar.* from Calendar where id = ?;";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(cal_id)});
        String[] columsArray = cursor.getColumnNames();
        Log.e("DBHelper.getMyChampionship", columsArray[1]);
        return cursor;
    }



    public void addIscription(Integer idUser, Integer idCamp, String team, String car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("idUser", idUser);
        cv.put("idCamp", idCamp);
        cv.put("team", team);
        cv.put("car", car);
        long res = db.insert(TABLE_INSCRIPTION, null, cv);
        Log.e("debug db", ""+res);
        db.close();


    }

    public void deleteIscription(Integer idUser, Integer idCamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        long res = db.delete(TABLE_INSCRIPTION, "iscription.idUser = '" +idUser+"' and iscription.idCamp = '"+idCamp+"'", null);
        Log.e("debug db", ""+res);
        db.close();


    }

    //endregion

    //region Popola DB
    public void populationChampionship(SQLiteDatabase db){
        String insertChampionship1 = "INSERT INTO Championship (id, name, logo, flags, fuel_consumption, tires_consumption, help, car_list) VALUES (0, 'Leon Supercopa', '/9j/4AAQSkZJRgABAQAAAQABAAD/4gIoSUNDX1BST0ZJTEUAAQEAAAIYAAAAAAIQAABtbnRyUkdC\n" +
                "    IFhZWiAAAAAAAAAAAAAAAABhY3NwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQAA9tYAAQAA\n" +
                "    AADTLQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlk\n" +
                "    ZXNjAAAA8AAAAHRyWFlaAAABZAAAABRnWFlaAAABeAAAABRiWFlaAAABjAAAABRyVFJDAAABoAAA\n" +
                "    AChnVFJDAAABoAAAAChiVFJDAAABoAAAACh3dHB0AAAByAAAABRjcHJ0AAAB3AAAADxtbHVjAAAA\n" +
                "    AAAAAAEAAAAMZW5VUwAAAFgAAAAcAHMAUgBHAEIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "    AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFhZWiAA\n" +
                "    AAAAAABvogAAOPUAAAOQWFlaIAAAAAAAAGKZAAC3hQAAGNpYWVogAAAAAAAAJKAAAA+EAAC2z3Bh\n" +
                "    cmEAAAAAAAQAAAACZmYAAPKnAAANWQAAE9AAAApbAAAAAAAAAABYWVogAAAAAAAA9tYAAQAAAADT\n" +
                "    LW1sdWMAAAAAAAAAAQAAAAxlblVTAAAAIAAAABwARwBvAG8AZwBsAGUAIABJAG4AYwAuACAAMgAw\n" +
                "    ADEANv/bAEMAAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEB\n" +
                "    AQEBAQEBAQEBAQEBAQEBAf/bAEMBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEB\n" +
                "    AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAf/AABEICdgRgAMBIgACEQEDEQH/xAAfAAAABgMB\n" +
                "    AQEAAAAAAAAAAAADBAUGBwgAAgkKAQv/xAB4EAABAgMFBAcFBAQDEA4EAhsBAhEDBCEABTFBUQYS\n" +
                "    YXEHE4GRobHwCCLB0eEJFDLxFSNCUjNi0hYXGBkkN0NTVVZygpKTlNMKNDZUV3N0dZWisrO01CU1\n" +
                "    OERjo8IaZHaDteImRYSF42XD1UakpcQnKEd3xfJnlv/EAB8BAAEFAQEBAQEBAAAAAAAAAAABAgME\n" +
                "    BQYHCQgKC//EAHMRAAECBAMFBAcEAgkNDQMBGQECEQADITEEQVEFEmFx8AaBkaEHEyKxwdHhCBQy\n" +
                "    8UJSFRYYI1VWYpTTCRkzN1RjcnWTlbO00hckNDU2Q1Nkc3SSstQlREV2gsI4V6IKJkaDlrUnZYTD\n" +
                "    GoWkxOLkR1ij8v/aAAwDAQACEQMRAD8A4abT9F8wiCpY607qHP4mwI/d5Wgea2JmhOFJhxWBNfey\n" +
                "    Yfun4W6Xzhu+floyfdJKW/CO2nd2G0Rz+ykmY6oghJdyXKA9SP4wb0KPbeSyEnfoXo55Zfn3RxB3\n" +
                "    SRuF6NTXP3D5RXbZ3o+mJpEMBMV6Yb2bfxc/QNpu2a6IptUWGoojAFSS/v65+7TD8sbTFsVs9JJi\n" +
                "    QxEQlhun8IxLY+8O3WtrPXFdd0wEQnSjAFtxIqDz7eFq8xRqUe0+Y8/j4d8PQhz7VBaoN+j1nCmz\n" +
                "    PRREhQ0FRihwMSvDdH8UaflnJ8p0bdUxK4gzxVrhVP58sZhlpm64ASAUhgn9lODd+lh499XfDS6V\n" +
                "    gM7FhXT55thaqlc/eSAksCH/ABVt5VyFH8LKZUtTCl70Pca14sH5uYitPR8N4OqKO06f4NjsPo8J\n" +
                "    Db62yqp9f3X8LP5O0kgGeIMRiB4VHhWyxL7Q3cpmiB8SwD+qU0OWDPmnEmgSTUXF7Utx4M8Sfd5I\n" +
                "    FVAGjE2Ng/lpnEY/zvFgO62YZqwy/ZsGrYDdO8VrwahV/J9aWmJe0F3pSTvgd3yHrjZq3ptdJQAW\n" +
                "    igYs4DnHDHusSTikt+9m9CxsWzbUg8zmYQSJAAVv2Zm0pq3jlckmGXC2JSh/1i+0keaXzPoWOwdk\n" +
                "    YaDWIp+as24acsbEJrpCk4RUeuA7tQ/58+NkON0nyaFf7YFKef8AF5d9rW7iSd4y1Vo7HhypX46t\n" +
                "    Ao4dJIKku4uQ5sDnQOa014RIMDZyEg/wpLVxNav+7qfWanCuWEivWEVOfDiLQ+vpWlUjeTMADLCt\n" +
                "    W0+XbYL+e1L/AO+h6/xbKEYkgj1RY/yTUuLluIz5szQ3ew/6wyoSP5PzD1rWkThDumDU9aX55d3D\n" +
                "    I91jQu+EkABdAG/FaBf57cAYTQHb9LCDpZgFP+2K4u4qa1LjsP5WPU4iYGMtQZmcEaeTNryeATcO\n" +
                "    hiFgGzuMwlvf7zWJyMnDDtEpz+YfzsGJOAokmMARU1+gtA6+liDUiYrzAIOuH1exGL0rQxhMVPEU\n" +
                "    7xT03A+54j/o1dfmPGHfeZP66a8Rw48RFiRBlUMOuBpr9OFjsBcsG/XJ1x40HbiPiLVZidKYUokT\n" +
                "    GZ/a7sudvqOlMA/7ZqWzo4yw+Vj7niP+jPgeHDj1Rz7zJ/XHl84th94l0j+FSM6n6W+i85WEQOuQ\n" +
                "    448XzFqmxelRRdpmrfvHN3GHdxshTHSlGJLTBcE/tu//AFcMzXSzBs3ELLqll3GR+OWTGluUIMdL\n" +
                "    DMeVrBtOq30uku+ZRn69BbSnbbRN/wAnDLiNDxFCRw42pN/PRjMyphuAW3gwsXV0mzJciYOFDv40\n" +
                "    5P3dlpBsqaweWTbn+jmBW/fXvUbSAFVOKXt+jW+ZL+PF70jaWTLfrofJx8CfjY0jaOTP9nhsBqMe\n" +
                "    /wArUFHSZNJL9cRXNZbBtMxzsZHSfMpH8MSAM1tx0f1hZDswoKRuM7ZH+TWwy86whx0tagSoUbzI\n" +
                "    sC2vLze+CtpJQEtGhtlUeb8OfOwR2mlBjHhjtT8jai46UIyk1jGrj8Z5fu/KxKL0oTLkJjlnzWR5\n" +
                "    ivGtDZw2XMUx9W9tQ9tBqR5jUw4Y5DhImG7aacK36Ji+n808p/b4fen5WGG0coQ/Xwz2h+xmdvXD\n" +
                "    n6npPnQof1QS/wDHNO3dOT2VYHSbNkB454HfOFcKDlmOzFVbKO7VJBIrcsPZu5z+J4whxyXYzAz3\n" +
                "    NmLN3/GL2fzRyv8Ab0dw+VtVbRyjF46OVA9qQfzzJn+3f9c22HSNORaCMTw3j/JJz87Vv2L5i1x/\n" +
                "    g++vmWrDhipSm/fU1petW+nnWpi7I2kkzURoZ/xh8rfDtDKK/sqDoWPzbvtS+HtzPNSMa/8Awn/1\n" +
                "    PKmNjkPbmdAYxSf8fy9189K62QbPAJDXalmZuhz4GHCajJYr8PkehFwzfss1I0Mn1jzyxtsm+ZYl\n" +
                "    +tQ5x/I4Ya9tqjwts5tdOtdyB+M07gMPROS7J7UTsRnWakftE+QcfMWjmYIpBO7vAVsTp4X+cBxV\n" +
                "    QneDavyy7qZXtFov0tLf21J5N8R8bbInIUQjdWnIkU+L17aWgiQvSdmFJSCa1Yqd6aimHp7SFdCJ\n" +
                "    1akuKFsz2nCndibU1o9WHNA7Wt4dfCVKyWYu54eFqdGJFQUrTuhzy5/UZWNIlziNMnzbn5Cwd3Sk\n" +
                "    VQSd2jYv8POzhTKqSKgB8MCPE151+dZa0mgIobuPDX8otIBBLjLrrhCWmAohy/j8j8LDIgKwY65/\n" +
                "    LlgDZWTLlhQdoHzw5U8bGYUsDkDrp8PpqXszeTqPGJIRRLEgmtPWYDWBVJ7zkkjHWj9lnL1CQCAO\n" +
                "    xm+NDYBUEMRR+TeuRsbwdnHXG0EIIk2FCW5E/C26ZIk4ls6V8rK4Ru0Ic8R+dhUIFDRq0bsssDHQ\n" +
                "    wkCRbAnmx+VtTIj94jh+YstqTSgD8BYFgcQD2WIXdLO35Ur1pCKuQBwUTTHPHRvhb6i783Vz+GHw\n" +
                "    7bLQYHANpYYbpDsOVKWkStIYFgff14e6GlINSLG/z+sIf3D+Mru/+ptou7yaurCpbBuz4WcSUAuz\n" +
                "    BuHrSwi0pY0A7q8ONl9Yj9YdN8xBuClNPNm59cYZkSRUC9Rr4Nl5CxOJJ', 'Solo nere', 'Normale', 'Normale', 'Frizione automatica', 'Seat Leon' )";
        String insertChampionship2 = "INSERT INTO Championship (id, name, logo, flags, fuel_consumption, tires_consumption, help, car_list) VALUES (1, 'International TCT', '/9j/4AAQSkZJRgABAQAAAQABAAD/4gIoSUNDX1BST0ZJTEUAAQEAAAIYAAAAAAIQAABtbnRyUkdC\n" +
                "    IFhZWiAAAAAAAAAAAAAAAABhY3NwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQAA9tYAAQAA\n" +
                "    AADTLQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlk\n" +
                "    ZXNjAAAA8AAAAHRyWFlaAAABZAAAABRnWFlaAAABeAAAABRiWFlaAAABjAAAABRyVFJDAAABoAAA\n" +
                "    AChnVFJDAAABoAAAAChiVFJDAAABoAAAACh3dHB0AAAByAAAABRjcHJ0AAAB3AAAADxtbHVjAAAA\n" +
                "    AAAAAAEAAAAMZW5VUwAAAFgAAAAcAHMAUgBHAEIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "    AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFhZWiAA\n" +
                "    AAAAAABvogAAOPUAAAOQWFlaIAAAAAAAAGKZAAC3hQAAGNpYWVogAAAAAAAAJKAAAA+EAAC2z3Bh\n" +
                "    cmEAAAAAAAQAAAACZmYAAPKnAAANWQAAE9AAAApbAAAAAAAAAABYWVogAAAAAAAA9tYAAQAAAADT\n" +
                "    LW1sdWMAAAAAAAAAAQAAAAxlblVTAAAAIAAAABwARwBvAG8AZwBsAGUAIABJAG4AYwAuACAAMgAw\n" +
                "    ADEANv/bAEMAAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEB\n" +
                "    AQEBAQEBAQEBAQEBAQEBAf/bAEMBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEB\n" +
                "    AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAf/AABEICdgRgAMBIgACEQEDEQH/xAAfAAAABgMB\n" +
                "    AQEAAAAAAAAAAAAEBQYHCAkCAwoAAQv/xAB5EAABAgMEBgcEBQMJEwgFARkBAhEDBCEABTFBBgcS\n" +
                "    UWFxCBOBkaGx8AkiwdEUMkLh8RUjUhkzN1VilJXS1BYXGCRDU1RWV3JzdHWSk7KztNMKNDU2OGOC\n" +
                "    1RolRHaDorXC40VkJoSjw+JlhaRGR6UnZnfEKMXllvL/xAAfAQABBAMBAQEBAAAAAAAAAAADAQIE\n" +
                "    BQAGBwgJCgv/xAB3EQABAgMFBQUFBAEIFQgFABsBAhEDITEABBJBUQUGYXGBByKRofATscHR4QgU\n" +
                "    MvFCCRUWFyNSVWIYGTZDU1RWV2Nyc3R1kpOUlbKz0tMkMzQ1Nzi01CVEZHaClrVFosLD5CY5RneD\n" +
                "    CidlhKPi40dmhaTEGliG/9oADAMBAAIRAxEAPwDiVugtNQuER+wgWmlqqLwZccE4clt3gjC0MbpQ\n" +
                "    BMw95Vj5b2wamVpqapkgwpYt+g/i3jb072QpQvaiASxxJ8Rg9D50819tUUp2LESz4s+hDHSngDaS\n" +
                "    EOgfcr5W37Y3Hw+dtQAcAUc22dXx8Pvt7Vu6CISGc4QADT9FPnbxKVTURQhunoW+7Y4+u22aIgBI\n" +
                "    3t67yzdotqUliGBw8eO7ytiQQz03WlrfCHkRMddOdenCzWBBmGkwesx5eXnYWsggMXq/rvsDigkl\n" +
                "    t4PhbNJLs7vvtmpIPA72sqFAyJY5eX1p5zs5Pdpo06VHXwnYGlQS7vVvjbNUQKBd3ZsBb4tABJdq\n" +
                "    nLPvztiEuCXwfwD2ctSEgTJJr1YflXnKxQBXMge6lvgBOFvEEFjb6lWzxFvijtF8LD9oQqVPPL68\n" +
                "    NRZfQt4UIO4i2zbG4+Hzt92QzZ72r69Y1tgUMCXw4ffZCpKyAK/NtHfxHhZC1Ty8SLZGI6dmrZYe\n" +
                "    s7aret6yKVhOgHm7V1tgAEhbenAch5W+gkFxbUlWAbNne24pIZ87IHqHbUfOzFAPOqpDhQfP1MZp\n" +
                "    W9D3+vXx2bWyDxDfh420JG0WdqPbatIUGP4emFiIi4VJfMt7vVXPvYUBw5pRtZGnCVSJWxCiVDcX\n" +
                "    p2WFwSASOZ8vlYKlATm7YW2JLEc277S1nEl0viIHDSelhxAlSSxk40+HN7GyCGG4M/ZllYUFFqGh\n" +
                "    rlYDCw7E/Gw2GnAdp9eFhwAEFyHUDTqGaT1Hp7VkUJEzN36swDPYZLnMn1UfKwwqwBPIevjYAgAE\n" +
                "    AcfI2FJS9TgKc/wtZFZws7FgQBxPnqxtXxAMRLBjOYEh6FttsFJJLhsPnbcEE1w3cfG2Khslnej2\n" +
                "    UTZwxz+LevdYYUCWD28ghOO5vK3jUk7ybZJQ4BJ7PvtjFISQwxHLPlaQmI5HCj+frws1u93TNyS/\n" +
                "    gRxz+dvChB3EWFQlgEHI7+0fPwsESQpmzLcrZF0lnNOzjYiS6nNTTnJrItLnV69GAsJikEkg0Y1x\n" +
                "    ap9NYKVEhjvfz+dvoBUWJO/f8eNvKTs5vYMRISvE9CCJaNrwq+nGWIASwJ7wZujMZZ9bY+vXfb1s\n" +
                "    0lLF2fGoyp6a33aSDQdrejZxiIIBeZYNLgJeuVLYVKCi5cyfoB8K/lbygdlPAV8Plb6kHZPF27rb\n" +
                "    Up2s92W+2tRAFcMG+HCwsTqUUnIPSkunhZAosEgTf4uPO2pJYg+sLbNsbj4fOwdbs4LN49tvoOA/\n" +
                "    cgvZhiiEz0Jp1AM+tiqRTEGLa+uOnutvWopYu2L+FvIU+JrlhUcLaVOoMSe2tvqPc448Me+xcZVh\n" +
                "    KfwkzPCXCQ+pyazcPdaT69Xr5W3LLA8aWxQcRvb42xJ2iKcGe3iNggu/rfZ5SgpJBGInhXjw9M9k\n" +
                "    ALFJBBM+GWfPR7CAoBJFav5W1lYBavrtt9ej8Htpqo8TZiSGIJy8qWRKXcmg9e7322E7SXFGOfLg\n" +
                "    ++2knZrutvCaMavXlytqWhgz48NzWaHdLuzhvEWVJmQJp1zp9NLYdZtEYvyHPK31S2ABwyFLYdWR\n" +
                "    UHtZq83thUnjhWxHBcAzY6hrEYHSXlT523ioB32EW1BDgVam622w0kpL/nYCi5JFtiVgACvo87fF\n" +
                "    KBDB8fnbEByBvt8IYkbi1jFYLqyHPyf1O2CRB0IsFihT1ri/hXlT52+wce0+Vty0lTNxfta31CNn\n" +
                "    BnLYBrCQlSlBgWzPrznnYhiYkl6zAHPN29+lsrbdoJCXeoGHIWyYbh3C3xSXarNws4ISHZ5/T5ej\n" +
                "    OwcQdnn1ytpt62ZQwJfAPh99tB2k1dw57N3H7/EgQpSThDsGsttlvoLHu8wfhbEFwDvD28SwJ3B7\n" +
                "    Iksgg1Ya5H5Wy32KoEdgx52AAEn1S21ZLO5xx3fK2KFMwPYd3P14YRQt+6J956ZUfwPk8qWMh0pl\n" +
                "    U/Fvl0sJQkpHdzo+NtVtwWne3fbHaSDRI50HwsUFAQATTxz6UszEQS4mW8pW9tBIY4ju34/db4mJ\n" +
                "    tNgakH4eviDbWsuCTw8xbVBSUu+D9+HyrYQUSSkUrU8H58LOCAUFRlm/rhPj7hMUOAOfwtpSkpd2\n" +
                "    q3xtsWtw7YPn91vkMhZYj0x5brOWPaGWo9wrp4trwwfg6K+NhYUNluB5Vthb4ABh5m2aU7T1ZrKm\n" +
                "    HhDEzJlItQPR7BAZ+Je2NvW2dXx8Pvtr2QkkD1577PKlAAESEtZSDPk/5WW22GdkEu1flvtguKA4\n" +
                "    Fcj6pb7/AFNWdaeFi1YJiByWp2n1Xv42CoJJDGbzoDLkTMdM7YiGlalEkAhuDsB7vjYUpbhmxx+6\n" +
                "    31CCVJfB8M/WdviEAAPXw9cPjYShP2vQraSlwA/RvXus5agkYcnmTrIinytuJBCRuHytpUtlMCKC\n" +
                "    otstqMIO4LH0N9jrwlAP6VW5tT18LAThxcGYOKmXhwttSagivrDn8bbisChB8PnbUgMQAd9W5nC2\n" +
                "    ZS6qih3cBnu9Z2WGokYQW6ayr699mKSCSDkS3jTwHqtsSggPiOFs0GhGbv5W3higjcD8T4/O2gIY\n" +
                "    uD2ffZ0SGQ2n6U600y099mJiM71arep+Vs7et63rOSvDwGtGy8LMmok1J', 'Nere e blu', 'Normale', 'Normale', 'Frizione automatica, ABS', 'VW Golf, Honda Civic, Peugeot 308, Hyundai i30n, Alfa Romeo Giulietta' )" ;
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

    private void populationRank(SQLiteDatabase db) {
        String insertRankPilot1 = "INSERT INTO RankPilot (idCamp, name, team, car, points) VALUES (0, 'Roberto Cosi', 'Buster eRacing Team', 'Seat Leon', 50) ";
        String insertRankPilot2 = "INSERT INTO RankPilot (idCamp, name, team, car, points) VALUES (0, 'Giacomo Lapadula', 'Naja Racing Italia', 'Seat Leon', 34) ";
        String insertRankPilot3= "INSERT INTO RankPilot (idCamp, name, team, car, points) VALUES (0, 'Andrea Crespi', 'Handy Racing Team', 'Seat Leon', 21) ";
        String insertRankPilot4 = "INSERT INTO RankPilot (idCamp, name, team, car, points) VALUES (0, 'Gennaro Deiulis', 'Team SSD', 'Seat Leon', 12) ";
        String insertRankPilot5 = "INSERT INTO RankPilot (idCamp, name, team, car, points) VALUES (0, 'Fabio Narduzzo', 'Buster eRacing Team', 'Seat Leon', 32) ";
        String insertRankPilot6 = "INSERT INTO RankPilot (idCamp, name, team,car, points) VALUES (0, 'Roberto Contino', 'Handy Racing Team', 'Seat Leon', 25) ";
        String insertRankPilot7 = "INSERT INTO RankPilot (idCamp, name, team,car, points) VALUES (1, 'Gianluca De Matteo', 'Buster eRacing Team', 'Honda Civic', 87) ";
        String insertRankPilot8 = "INSERT INTO RankPilot (idCamp, name, team,car, points) VALUES (1, 'Claudio Gasperini', 'Panzer Team', 'Peugeot 308', 42) ";
        String insertRankPilot9 = "INSERT INTO RankPilot (idCamp, name, team,car, points) VALUES (1, 'Marco Mazzetti', 'Handy Racing Team', 'VW Golf', 68) ";
        String insertRankPilot10 = "INSERT INTO RankPilot (idCamp, name, team,car, points) VALUES (1, 'Dario Raffaeta', 'Indipendente', 'Alfa Romeo Giulietta', 10) ";
        String insertRankPilot11= "INSERT INTO RankPilot (idCamp, name, team,car, points) VALUES (1, 'Ugo Federico Bagnasco', 'Buster eRacing Team', 'Honda Civic', 41) ";
        String insertRankPilot12 = "INSERT INTO RankPilot (idCamp, name, team,car, points) VALUES (1, 'Enrico Sancini', 'Handy Racing Team', 'VW Golf', 54) ";
        db.execSQL(insertRankPilot1);db.execSQL(insertRankPilot2);db.execSQL(insertRankPilot3);db.execSQL(insertRankPilot4);db.execSQL(insertRankPilot5);db.execSQL(insertRankPilot6);
        db.execSQL(insertRankPilot7);db.execSQL(insertRankPilot8);db.execSQL(insertRankPilot9);db.execSQL(insertRankPilot10);db.execSQL(insertRankPilot11);db.execSQL(insertRankPilot12);
        String insertTeam1 = "INSERT INTO RankTeam (idCamp, name, car, points) VALUES (0, 'Busters eRacing Team', 'Seat Leon', 82)";
        String insertTeam2 = "INSERT INTO RankTeam (idCamp, name, car, points) VALUES (0, 'Naja Racing Italia', 'Seat Leon', 34)";
        String insertTeam3 = "INSERT INTO RankTeam (idCamp, name, car, points) VALUES (0, 'Handy Racing Team', 'Seat Leon', 46)";
        String insertTeam4 = "INSERT INTO RankTeam (idCamp, name, car, points) VALUES (0, 'Team SSD', 'Seat Leon', 12)";
        String insertTeam5 = "INSERT INTO RankTeam (idCamp, name, car, points) VALUES (1, 'Busters eRacing Team', 'Honda Civic', 128)";
        String insertTeam6 = "INSERT INTO RankTeam (idCamp, name, car, points) VALUES (1, 'Panzer Team', 'Peugeot 308', 42)";
        String insertTeam7 = "INSERT INTO RankTeam (idCamp, name, car, points) VALUES (1, 'Handy Racing Team', 'VW Golf', 122)";
        String insertTeam8 = "INSERT INTO RankTeam (idCamp, name, car, points) VALUES (1, 'Indipendente', 'Alfa Romeo Giulietta', 10)";
        db.execSQL(insertTeam1);db.execSQL(insertTeam2);db.execSQL(insertTeam3);db.execSQL(insertTeam4);
        db.execSQL(insertTeam5);db.execSQL(insertTeam6);db.execSQL(insertTeam7);db.execSQL(insertTeam8);
    }

    //endregion

}
