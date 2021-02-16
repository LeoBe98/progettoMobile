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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.tools.ObjectChampionship;
import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.ProfileImage;
import com.example.myapplication.tools.Utils;

import java.util.ArrayList;

public class ChampionshipsActivity extends AppCompatActivity {
    DBHelper db;
    ArrayList<ObjectChampionship> championshipList;
    ObjectChampionship championship;
    ListView lv_championship;
    DrawerLayout drawerLayout;
    Integer userId;
    static AdapterChampionship adapterChampionship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championships);

        db = new DBHelper(this);
        drawerLayout = findViewById(R.id.drawer_layout);

        championshipList = new ArrayList<>();
        lv_championship = (ListView) findViewById(R.id.lv_championships);

        //Recupero userId
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

        //Set menu
        TextView nome = (TextView) findViewById(R.id.menuName);
        nome.setText(Utils.USER.getNAME() + " " + Utils.USER.getLASTNAME());
        if (Utils.USER.getPROFILEPHOTO() != "") {
            Bitmap bitmapProfile = ProfileImage.StringToBitMap(Utils.USER.getPROFILEPHOTO());
            ImageView profileMenu = (ImageView) findViewById(R.id.menuProfileImage);
            profileMenu.setImageBitmap(bitmapProfile);
        }

        //Recupero tutti i campionati in cui l'utente non è iscritto
        Cursor getDisponibleChampionship = db.getDisponibleChampionship(userId);
        for( getDisponibleChampionship.moveToFirst(); !getDisponibleChampionship.isAfterLast(); getDisponibleChampionship.moveToNext() ) {
            Integer id  = (getDisponibleChampionship.getInt(getDisponibleChampionship.getColumnIndex("id")));
            String name = (getDisponibleChampionship.getString(getDisponibleChampionship.getColumnIndex("name")));
            String logo = (getDisponibleChampionship.getString(getDisponibleChampionship.getColumnIndex("logo")));
            String flags = (getDisponibleChampionship.getString(getDisponibleChampionship.getColumnIndex("flags")));
            String fuel_consumption = (getDisponibleChampionship.getString(getDisponibleChampionship.getColumnIndex("fuel_consumption")));
            String tires_consumption = (getDisponibleChampionship.getString(getDisponibleChampionship.getColumnIndex("tires_consumption")));
            String help = (getDisponibleChampionship.getString(getDisponibleChampionship.getColumnIndex("help")));
            String car_list = (getDisponibleChampionship.getString(getDisponibleChampionship.getColumnIndex("car_list")));
            championship = new ObjectChampionship(id, name, logo, flags, fuel_consumption, tires_consumption, help, car_list);
            championshipList.add(championship);
        }

        //Invio all'adapter la lista dei campionati e setto alla listView l'adapter
        adapterChampionship = new AdapterChampionship(ChampionshipsActivity.this, championshipList);
        lv_championship.setAdapter(adapterChampionship);

        //Gestisco click sulla listView
        lv_championship.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ChampionshipsActivity.this, NotInscriptionChampionshipActivity.class);
                intent.putExtra("champId", championshipList.get(i).getID());
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
        Intent intent = new Intent(ChampionshipsActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void ClickProfile(View view){
        Intent intent = new Intent(ChampionshipsActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void ClickMyChampionship(View view){
        Intent intent = new Intent(ChampionshipsActivity.this, MyChampionshipActivity.class);
        intent.putExtra("userId", Utils.USER.getID());
        startActivity(intent);
    }

    public void ClickChampionship(View view){
        recreate();
    }

    public void ClickGallery(View view){
        Intent intent = new Intent(ChampionshipsActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    public void ClickLogOut(View view){
        db.updateStatus(Utils.STATUS_NOT_LOGGED, -1);
        Intent intent = new Intent(ChampionshipsActivity.this, LoginActivity.class);
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