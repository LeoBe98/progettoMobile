package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.tools.Championship;
import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.Utils;

public class IscriptionChampionshipInfoActivity extends AppCompatActivity {
    Integer champId;
    DBHelper db;

    TextView tv_name, tv_flags, tv_fuel, tv_tire, tv_driving;
    Championship championship;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iscription_championship_info);


        tv_name = (TextView) findViewById(R.id.tv_name_iscription_championship_info);
        tv_flags = (TextView) findViewById(R.id.tv_setting_flags_result_championship_info);
        tv_fuel = (TextView) findViewById(R.id.tv_setting_FuelConsumption_result_championship_info);
        tv_tire = (TextView) findViewById(R.id.tv_setting_tire_wear_result_championship_info);
        tv_driving = (TextView) findViewById(R.id.tv_setting_driving_aids_result_championship_info);



        db = new DBHelper(this);
        Intent i = getIntent();

        if (!i.hasExtra("champId")) {
            Toast.makeText(this, "champId mancante", Toast.LENGTH_LONG).show();
            Intent new_i = new Intent(this, LoginActivity.class);
            startActivity(new_i);
        } else {
            champId = i.getIntExtra("champId", -1);
            Log.w("champId ricevuto in not:", String.valueOf(champId));
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

        tv_name.setText(championship.getNAME());
        tv_flags.setText(championship.getFLAGS());
        tv_fuel.setText(championship.getFuelConsumption());
        tv_tire.setText(championship.getTiresConsumption());
        tv_driving.setText(championship.getHELP());


    }
}