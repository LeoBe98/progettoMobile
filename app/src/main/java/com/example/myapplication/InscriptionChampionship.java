package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.tools.Championship;
import com.example.myapplication.tools.DBHelper;

public class InscriptionChampionship extends AppCompatActivity {

    TextView nameChampionship;
    Integer champId;
    DBHelper db;
    Championship championship;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_championship);
        db = new DBHelper(this);
        nameChampionship = (TextView) findViewById(R.id.tv_name_iscription_championship);

        Intent i = getIntent();

        if (!i.hasExtra("champId")) {
            Toast.makeText(this, "champId mancante", Toast.LENGTH_LONG).show();
            Intent new_i = new Intent(this, LoginActivity.class);
            startActivity(new_i);
        } else {
            champId = i.getIntExtra("champId", -1);
            Log.w("champId ricevuto:", String.valueOf(champId));
            if (champId == -1) {
                Toast.makeText(this, "Champ non creato.", Toast.LENGTH_LONG).show();
                Intent new_i = new Intent(this, LoginActivity.class);
                startActivity(new_i);
            }
        }


        Cursor getMyChampionship = db.getChampionship(champId);
        getMyChampionship.moveToFirst();
      
            Integer id  = (getMyChampionship.getInt(getMyChampionship.getColumnIndex("id")));
            String name = (getMyChampionship.getString(getMyChampionship.getColumnIndex("name")));
            String logo = (getMyChampionship.getString(getMyChampionship.getColumnIndex("logo")));
            String flags = (getMyChampionship.getString(getMyChampionship.getColumnIndex("flags")));
            String fuel_consumption = (getMyChampionship.getString(getMyChampionship.getColumnIndex("fuel_consumption")));
            String tires_consumption = (getMyChampionship.getString(getMyChampionship.getColumnIndex("tires_consumption")));
            String help = (getMyChampionship.getString(getMyChampionship.getColumnIndex("help")));
            String car_list = (getMyChampionship.getString(getMyChampionship.getColumnIndex("car_list")));
            championship = new Championship(id, name, logo, flags, fuel_consumption, tires_consumption, help, car_list);


        nameChampionship.setText(championship.getNAME());
    }
}