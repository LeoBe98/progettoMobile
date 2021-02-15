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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.tools.Championship;
import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.Pilot;
import com.example.myapplication.tools.ProfileImage;
import com.example.myapplication.tools.Team;
import com.example.myapplication.tools.Utils;

import java.util.ArrayList;

public class IscriptionChampionshipRankActivity extends AppCompatActivity {
    DBHelper db;
    Button btn_move_info, btn_move_championship;
    Integer champId;
    Integer userId;
    DrawerLayout drawerLayout;
    ListView lv_pilot, lv_team;
    Pilot pilot;
    ArrayList<Pilot> pilotList;
    AdapterPilot adapterPilot;
    Team team;
    ArrayList<Team> teamList;
    AdapterTeam adapterTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iscription_championship_rank);
        userId = Utils.USER.getID();
        drawerLayout = findViewById(R.id.drawer_layout);
        db = new DBHelper(this);
        btn_move_info = (Button) findViewById(R.id.btn_inscription_rank_to_info);
        btn_move_championship = (Button) findViewById(R.id.btn_inscription_rank_to_championship);
        lv_pilot = (ListView) findViewById(R.id.lv_rank_user_iscription_championship);
        lv_team = (ListView) findViewById(R.id.lv_rank_team_iscription_championship);
        pilotList = new ArrayList<>();
        teamList = new ArrayList<>();
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
        TextView nome = (TextView) findViewById(R.id.menuName);
        nome.setText(Utils.USER.getNAME() + " " + Utils.USER.getLASTNAME());
        if (Utils.USER.getPROFILEPHOTO() != "") {
            Bitmap bitmapProfile = ProfileImage.StringToBitMap(Utils.USER.getPROFILEPHOTO());
            ImageView profileMenu = (ImageView) findViewById(R.id.menuProfileImage);
            profileMenu.setImageBitmap(bitmapProfile);
        }

        //region RankPilot
        Cursor getRank = db.getPilotByChampionship(champId);
        for( getRank.moveToFirst(); !getRank.isAfterLast(); getRank.moveToNext() ) {
            Integer idChamp = (getRank.getInt(getRank.getColumnIndex("idCamp")));
            String name = (getRank.getString(getRank.getColumnIndex("name")));
            String team = (getRank.getString(getRank.getColumnIndex("team")));
            String car = (getRank.getString(getRank.getColumnIndex("car")));
            Integer points = (getRank.getInt(getRank.getColumnIndex("points")));
            pilot = new Pilot(idChamp, name, team, car, points);
            pilotList.add(pilot);
        }
        Cursor getUserRank = db.getUserRank(champId);
        for( getUserRank.moveToFirst(); !getUserRank.isAfterLast(); getUserRank.moveToNext() ) {
            Integer idChamp = champId;
            String name = (getUserRank.getString(getUserRank.getColumnIndex("name"))+" "+getUserRank.getString(getUserRank.getColumnIndex("lastname")));
            String team = (getUserRank.getString(getUserRank.getColumnIndex("team")));
            String car = (getUserRank.getString(getUserRank.getColumnIndex("car")));
            Integer points = 0;
            pilot = new Pilot(idChamp, name, team, car, points);
            pilotList.add(pilot);
        }
        adapterPilot = new AdapterPilot(IscriptionChampionshipRankActivity.this, pilotList);
        lv_pilot.setAdapter(adapterPilot);
        //endregion

        //region RankTeam
        Cursor getTeamRank = db.getTeamByChampionship(champId);
        for( getTeamRank.moveToFirst(); !getTeamRank.isAfterLast(); getTeamRank.moveToNext() ) {
            Integer idTeam = (getTeamRank.getInt(getTeamRank.getColumnIndex("idTeam")));
            Integer idChamp = (getTeamRank.getInt(getTeamRank.getColumnIndex("idCamp")));
            String name = (getTeamRank.getString(getTeamRank.getColumnIndex("name")));
            String car = (getTeamRank.getString(getTeamRank.getColumnIndex("car")));
            Integer points = (getTeamRank.getInt(getTeamRank.getColumnIndex("points")));
            team = new Team(idTeam, idChamp, name, car, points);
            teamList.add(team);
        }
        adapterTeam = new AdapterTeam(IscriptionChampionshipRankActivity.this, teamList);
        lv_team.setAdapter(adapterTeam);
        //endregion
        btn_move_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IscriptionChampionshipRankActivity.this, IscriptionChampionshipInfoActivity.class);
                intent.putExtra("champId", champId);
                startActivity(intent);
            }
        });

        btn_move_championship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IscriptionChampionshipRankActivity.this, InscriptionChampionshipActivity.class);
                intent.putExtra("champId", champId);
                startActivity(intent);
            }
        });
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
        Intent intent = new Intent(IscriptionChampionshipRankActivity.this, HomeActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickProfile(View view) {
        Intent intent = new Intent(IscriptionChampionshipRankActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void ClickMyChampionship(View view) {
        Intent intent = new Intent(IscriptionChampionshipRankActivity.this, MyChampionshipActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickChampionship(View view) {
        Intent intent = new Intent(IscriptionChampionshipRankActivity.this, ChampionshipsActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickGallery(View view) {
        Intent intent = new Intent(IscriptionChampionshipRankActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    public void ClickLogOut(View view) {
        db.updateStatus(Utils.STATUS_NOT_LOGGED, -1);
        Intent intent = new Intent(IscriptionChampionshipRankActivity.this, LoginActivity.class);
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