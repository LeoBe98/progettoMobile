package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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


    Integer userId;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_championship);
        userId = Utils.USER.getID();
        drawerLayout = findViewById(R.id.drawer_layout);

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
            raceList.add(race);
        }
        adapterRace = new AdapterRace(InscriptionChampionshipActivity.this, raceList);
        lv_race.setAdapter(adapterRace);


        lv_race.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(InscriptionChampionshipActivity.this, IscriptionChampionshipRaceActivity.class);
                intent.putExtra("raceId", raceList.get(i).getID());
                intent.putExtra("champId", champId);
                startActivity(intent);

            }
        });


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
                Intent intent = new Intent(InscriptionChampionshipActivity.this, IscriptionChampionshipRankActivity.class);
                intent.putExtra("champId", champId);
                startActivity(intent);
            }
        });
    }

    //region MENU
    public void ClickMenu(View view)
    {
        //Apro il drawer
        openDrawer(drawerLayout);

    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        //Apro il drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        //chiudo il drawer
        closeDrawer(drawerLayout);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        Intent intent = new Intent(InscriptionChampionshipActivity.this, HomeActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickProfile(View view){
        Intent intent = new Intent(InscriptionChampionshipActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void ClickMyChampionship(View view){
        Intent intent = new Intent(InscriptionChampionshipActivity.this, MyChampionshipActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickChampionship(View view){
        Intent intent = new Intent(InscriptionChampionshipActivity.this, ChampionshipsActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickGallery(View view){
        Intent intent = new Intent(InscriptionChampionshipActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    public void ClickLogOut(View view){
        db.updateStatus(Utils.STATUS_NOT_LOGGED, -1);
        Intent intent = new Intent(InscriptionChampionshipActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
    //endregion
}