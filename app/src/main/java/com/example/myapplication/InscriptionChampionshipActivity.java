package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.tools.Championship;
import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.Race;
import com.example.myapplication.tools.Utils;

import java.util.ArrayList;

public class InscriptionChampionshipActivity extends AppCompatActivity {
    DBHelper db;
    Integer champId;
    Button btn_disiscription, btn_move_info, btn_move_rank;
    TextView nameChampionship;
    Championship championship;
    Race race;
    ArrayList<Race> raceList;
    ListView lv_race;
    AdapterRace adapterRace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_championship);

        db = new DBHelper(this);
        nameChampionship = (TextView) findViewById(R.id.tv_name_iscription_championship);
        btn_disiscription = (Button) findViewById(R.id.btn_disiscription);
        btn_move_info = (Button) findViewById(R.id.btn_championship_to_info);
        btn_move_rank = (Button) findViewById(R.id.btn_championship_to_rank);
        lv_race = (ListView) findViewById(R.id.lv_races_iscription_championship);
        raceList = new ArrayList<>();
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

        Integer id = (getMyChampionship.getInt(getMyChampionship.getColumnIndex("id")));
        String name = (getMyChampionship.getString(getMyChampionship.getColumnIndex("name")));
        String logo = (getMyChampionship.getString(getMyChampionship.getColumnIndex("logo")));
        String flags = (getMyChampionship.getString(getMyChampionship.getColumnIndex("flags")));
        String fuel_consumption = (getMyChampionship.getString(getMyChampionship.getColumnIndex("fuel_consumption")));
        String tires_consumption = (getMyChampionship.getString(getMyChampionship.getColumnIndex("tires_consumption")));
        String help = (getMyChampionship.getString(getMyChampionship.getColumnIndex("help")));
        String car_list = (getMyChampionship.getString(getMyChampionship.getColumnIndex("car_list")));
        championship = new Championship(id, name, logo, flags, fuel_consumption, tires_consumption, help, car_list);
        nameChampionship.setText(championship.getNAME());


        Cursor getCalendarioChampionship = db.getCalendarChampionship(champId);
        for( getCalendarioChampionship.moveToFirst(); !getCalendarioChampionship.isAfterLast(); getCalendarioChampionship.moveToNext() ) {
            Integer id_cal  = (getCalendarioChampionship.getInt(getCalendarioChampionship.getColumnIndex("id")));
            Integer id_camp = (getCalendarioChampionship.getInt(getCalendarioChampionship.getColumnIndex("idCamp")));
            String circuit = (getCalendarioChampionship.getString(getCalendarioChampionship.getColumnIndex("circuit")));
            String data = (getCalendarioChampionship.getString(getCalendarioChampionship.getColumnIndex("data")));
            race = new Race(id_cal, id_camp, circuit, data);
            Log.e("circuit", circuit);
            // Log.e("before add " +k, toAdd.getNAME());
            raceList.add(race);
            //  Log.e("after add " +k, listToAdd.get(k).getNAME() );
        }
        adapterRace = new AdapterRace(InscriptionChampionshipActivity.this, raceList);
        lv_race.setAdapter(adapterRace);





        btn_disiscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ProvaIscription", Utils.USER.getID() + " " + champId);
                db.deleteIscription(Utils.USER.getID(), champId);
                Intent intent = new Intent(InscriptionChampionshipActivity.this, ChampionshipsActivity.class);
                intent.putExtra("userId", Utils.USER.getID());
                startActivity(intent);
            }
        });

        btn_move_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InscriptionChampionshipActivity.this, IscriptionChampionshipInfoActivity.class);
                intent.putExtra("champId", champId);
                startActivity(intent);
            }
        });

        btn_move_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InscriptionChampionshipActivity.this, IscriptionChampionshipInfoActivity.class);
                intent.putExtra("champId", champId);
                startActivity(intent);
            }
        });
    }
}