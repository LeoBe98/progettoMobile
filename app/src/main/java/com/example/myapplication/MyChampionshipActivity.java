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
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.tools.Championship;
import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.Utils;

import java.util.ArrayList;

public class MyChampionshipActivity extends AppCompatActivity {
    DBHelper db;
    ArrayList<Championship> mychampionshipList;
    Championship mychampionship;
    ListView lv_mychampionship;
    DrawerLayout drawerLayout;
    Integer userId;
    static ChampionshipAdapter championshipAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_championship);
        drawerLayout = findViewById(R.id.drawer_layout);
        db = new DBHelper(this);

        mychampionshipList = new ArrayList<Championship>();
        lv_mychampionship = (ListView) findViewById(R.id.lv_mychampionships);


        Intent i = getIntent();

        if (!i.hasExtra("userId")) {
            Toast.makeText(this, "userId mancante", Toast.LENGTH_LONG).show();
            Intent new_i = new Intent(this, LoginActivity.class);
            startActivity(new_i);
        } else {
            userId = i.getIntExtra("userId", -1);
            Log.w("userId ricevuto:", String.valueOf(userId));
            if (userId == -1) {
                Toast.makeText(this, "Utente non creato.", Toast.LENGTH_LONG).show();
                Intent new_i = new Intent(this, LoginActivity.class);
                startActivity(new_i);
            }
        }

        Cursor getMyChampionship = db.getMyChampionship(userId);

        for( getMyChampionship.moveToFirst(); !getMyChampionship.isAfterLast(); getMyChampionship.moveToNext() ) {


            Integer id  = (getMyChampionship.getInt(getMyChampionship.getColumnIndex("id")));
            String name = (getMyChampionship.getString(getMyChampionship.getColumnIndex("name")));
            String logo = (getMyChampionship.getString(getMyChampionship.getColumnIndex("logo")));
            String flags = (getMyChampionship.getString(getMyChampionship.getColumnIndex("flags")));
            String fuel_consumption = (getMyChampionship.getString(getMyChampionship.getColumnIndex("fuel_consumption")));
            String tires_consumption = (getMyChampionship.getString(getMyChampionship.getColumnIndex("tires_consumption")));
            String help = (getMyChampionship.getString(getMyChampionship.getColumnIndex("help")));
            String car_list = (getMyChampionship.getString(getMyChampionship.getColumnIndex("car_list")));
            mychampionship = new Championship(id, name, logo, flags, fuel_consumption, tires_consumption, help, car_list);
           // Log.e("before add " +k, toAdd.getNAME());
            mychampionshipList.add(mychampionship);
          //  Log.e("after add " +k, listToAdd.get(k).getNAME() );


        }
        championshipAdapter = new ChampionshipAdapter(MyChampionshipActivity.this, mychampionshipList);
        lv_mychampionship.setAdapter(championshipAdapter);

        lv_mychampionship.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MyChampionshipActivity.this, InscriptionChampionship.class);
                intent.putExtra("champId", mychampionshipList.get(i).getID());

                startActivity(intent);

            }
        });
    }

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
        Intent intent = new Intent(MyChampionshipActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void ClickProfile(View view){
        Intent intent = new Intent(MyChampionshipActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void ClickMyChampionship(View view){
        recreate();
    }

    public void ClickChampionship(View view){
        Intent intent = new Intent(MyChampionshipActivity.this, ChampionshipsActivity.class);
        startActivity(intent);
    }

    public void ClickGallery(View view){
        Intent intent = new Intent(MyChampionshipActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    public void ClickLogOut(View view){
        db.updateStatus(Utils.STATUS_NOT_LOGGED, -1);
        Intent intent = new Intent(MyChampionshipActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}