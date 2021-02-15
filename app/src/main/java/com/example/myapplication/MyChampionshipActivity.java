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

import com.example.myapplication.tools.Championship;
import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.ProfileImage;
import com.example.myapplication.tools.Utils;

import java.util.ArrayList;

public class MyChampionshipActivity extends AppCompatActivity {
    DBHelper db;
    ArrayList<Championship> mychampionshipList;
    Championship mychampionship;
    ListView lv_mychampionship;
    DrawerLayout drawerLayout;
    Integer userId;
    static AdapterChampionship adapterChampionship;
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

        TextView nome = (TextView) findViewById(R.id.menuName);
        nome.setText(Utils.USER.getNAME() + " " + Utils.USER.getLASTNAME());
        if (Utils.USER.getPROFILEPHOTO() != "") {
            Bitmap bitmapProfile = ProfileImage.StringToBitMap(Utils.USER.getPROFILEPHOTO());
            ImageView profileMenu = (ImageView) findViewById(R.id.menuProfileImage);
            profileMenu.setImageBitmap(bitmapProfile);
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
        adapterChampionship = new AdapterChampionship(MyChampionshipActivity.this, mychampionshipList);
        lv_mychampionship.setAdapter(adapterChampionship);

        lv_mychampionship.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MyChampionshipActivity.this, InscriptionChampionshipActivity.class);
                intent.putExtra("champId", mychampionshipList.get(i).getID());
                startActivity(intent);

            }
        });
    }


    //regione MENU
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
        intent.putExtra("userId", Utils.USER.getID());
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
    //endregion
}