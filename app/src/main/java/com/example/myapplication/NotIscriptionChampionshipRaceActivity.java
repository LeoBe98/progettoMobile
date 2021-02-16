package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.ObjectPilot;
import com.example.myapplication.tools.ProfileImage;
import com.example.myapplication.tools.ObjectRace;
import com.example.myapplication.tools.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class NotIscriptionChampionshipRaceActivity extends AppCompatActivity {
    DBHelper db;
    Integer userId, raceId, champId;
    DrawerLayout drawerLayout;
    ObjectRace race;
    TextView name;
    ListView lv_pilot;
    ObjectPilot pilot;
    ArrayList<ObjectPilot> pilotList;
    AdapterPilot adapterPilot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_inscription_championship_race);

        db = new DBHelper(this);
        userId = Utils.USER.getID();
        drawerLayout = findViewById(R.id.drawer_layout);

        lv_pilot = (ListView) findViewById(R.id.lv_not_race);
        name = (TextView) findViewById(R.id.notraceName);
        //inizializzo la lista
        pilotList = new ArrayList<>();

        //Recupero raceId
        Intent i = getIntent();
        if (!i.hasExtra("raceId")) {
            Toast.makeText(this, "raceId mancante", Toast.LENGTH_LONG).show();
            Intent new_i = new Intent(this, LoginActivity.class);
            startActivity(new_i);
        } else {
            raceId = i.getIntExtra("raceId", -1);
            Log.w("raceId ricevuto:", String.valueOf(raceId));
            if (raceId == -1) {
                Toast.makeText(this, "race non creato.", Toast.LENGTH_LONG).show();
                Intent new_i = new Intent(this, LoginActivity.class);
                startActivity(new_i);
            }
        }
        //Recupero champId
        if (!i.hasExtra("champId")) {
            Toast.makeText(this, "champId mancante", Toast.LENGTH_LONG).show();
            Intent new_i = new Intent(this, LoginActivity.class);
            startActivity(new_i);
        } else {
            champId = i.getIntExtra("champId", -1);
            Log.w("champId ricevuto:", String.valueOf(champId));
            if (champId == -1) {
                Toast.makeText(this, "champ non creato.", Toast.LENGTH_LONG).show();
                Intent new_i = new Intent(this, LoginActivity.class);
                startActivity(new_i);
            }
        }

        //Set menu
        TextView nome = (TextView) findViewById(R.id.menuName);
        nome.setText(Utils.USER.getNAME() + " " + Utils.USER.getLASTNAME());
        if (Utils.USER.getPROFILEPHOTO() != "") {
            Bitmap bitmapProfile = ProfileImage.StringToBitMap(Utils.USER.getPROFILEPHOTO());
            ImageView profileMenu = (ImageView) findViewById(R.id.menuProfileImage);
            profileMenu.setImageBitmap(bitmapProfile);
        }

        //Recupero dati corsa
        Cursor getRace = db.getRace(raceId);
        for (getRace.moveToFirst(); !getRace.isAfterLast(); getRace.moveToNext()) {
            Integer id_cal = (getRace.getInt(getRace.getColumnIndex("id")));
            Integer id_camp = (getRace.getInt(getRace.getColumnIndex("idCamp")));
            String circuit = (getRace.getString(getRace.getColumnIndex("circuit")));
            String data = (getRace.getString(getRace.getColumnIndex("data")));
            race = new ObjectRace(id_cal, id_camp, circuit, data);
        }

        name.setText(race.getCIRCUIT());
        //Creo dati random per visualizzare la gara
        Cursor getRank = db.getPilotByChampionship(champId);
        for (getRank.moveToFirst(); !getRank.isAfterLast(); getRank.moveToNext()) {
            Integer idChamp = (getRank.getInt(getRank.getColumnIndex("idCamp")));
            String name = (getRank.getString(getRank.getColumnIndex("name")));
            String team = (getRank.getString(getRank.getColumnIndex("team")));
            String car = (getRank.getString(getRank.getColumnIndex("car")));
            Integer points = (int) (Math.random() * 20);
            pilot = new ObjectPilot(idChamp, name, team, car, points);
            pilotList.add(pilot);
        }
        //Riordino la lista in modo decrescente con i valori casuali di punti
        Collections.sort(pilotList);
        Collections.reverse(pilotList);
        //Invio la lista e setto l'adapter nella listView
        adapterPilot = new AdapterPilot(NotIscriptionChampionshipRaceActivity.this, pilotList);
        lv_pilot.setAdapter(adapterPilot);
    }

    //region MENU
    public void ClickMenu(View view) {
        //Apro il drawer
        openDrawer(drawerLayout);

    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        //Apro il drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        //chiudo il drawer
        closeDrawer(drawerLayout);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view) {
        Intent intent = new Intent(NotIscriptionChampionshipRaceActivity.this, HomeActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickProfile(View view) {
        Intent intent = new Intent(NotIscriptionChampionshipRaceActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void ClickMyChampionship(View view) {
        Intent intent = new Intent(NotIscriptionChampionshipRaceActivity.this, MyChampionshipActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickChampionship(View view) {
        Intent intent = new Intent(NotIscriptionChampionshipRaceActivity.this, ChampionshipsActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickGallery(View view) {
        Intent intent = new Intent(NotIscriptionChampionshipRaceActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    public void ClickLogOut(View view) {
        db.updateStatus(Utils.STATUS_NOT_LOGGED, -1);
        Intent intent = new Intent(NotIscriptionChampionshipRaceActivity.this, LoginActivity.class);
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