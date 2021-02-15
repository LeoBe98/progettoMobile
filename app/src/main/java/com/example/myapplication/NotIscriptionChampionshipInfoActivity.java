package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.tools.Championship;
import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.ProfileImage;
import com.example.myapplication.tools.Utils;

public class NotIscriptionChampionshipInfoActivity extends AppCompatActivity {

    Integer champId;
    DBHelper db;
    Button btn_move_rank, btn_move_championship;
    TextView tv_name, tv_flags, tv_fuel, tv_tire, tv_driving, tv_forum;
    Championship championship;
    Integer userId;
    DrawerLayout drawerLayout;
    ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_iscription_championship_info);
        userId = Utils.USER.getID();
        drawerLayout = findViewById(R.id.drawer_layout);

        tv_name = (TextView) findViewById(R.id.tv_name_not_iscription_championship_info);
        tv_flags = (TextView) findViewById(R.id.tv_not_setting_flags_result_championship_info);
        tv_fuel = (TextView) findViewById(R.id.tv_not_setting_FuelConsumption_result_championship_info);
        tv_tire = (TextView) findViewById(R.id.tv_not_setting_tire_wear_result_championship_info);
        tv_driving = (TextView) findViewById(R.id.tv_not_setting_driving_aids_result_championship_info);
        tv_forum = (TextView) findViewById(R.id.not_forum_championship_info);
        im = (ImageView) findViewById(R.id.logonotChamp);
        btn_move_rank = (Button) findViewById(R.id.btn_not_inscription_info_to_rank);
        btn_move_championship = (Button) findViewById(R.id.btn_not_inscription_info_to_championship);

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

        TextView nome = (TextView) findViewById(R.id.menuName);
        nome.setText(Utils.USER.getNAME() + " " + Utils.USER.getLASTNAME());
        if (Utils.USER.getPROFILEPHOTO() != "") {
            Bitmap bitmapProfile = ProfileImage.StringToBitMap(Utils.USER.getPROFILEPHOTO());
            ImageView profileMenu = (ImageView) findViewById(R.id.menuProfileImage);
            profileMenu.setImageBitmap(bitmapProfile);
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

        tv_name.setText(championship.getNAME());
        tv_flags.setText(championship.getFLAGS());
        tv_fuel.setText(championship.getFuelConsumption());
        tv_tire.setText(championship.getTiresConsumption());
        tv_driving.setText(championship.getHELP());

        tv_forum.setMovementMethod(LinkMovementMethod.getInstance());
        tv_forum.setText("forum." + championship.getNAME().replaceAll("\\s+", "") + ".com");

        if(name.equals("Leon Supercopa")){
            im.setImageResource(R.drawable.logochamp0);
        }
        else{
            im.setImageResource(R.drawable.logochamp1);
        }

        btn_move_championship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotIscriptionChampionshipInfoActivity.this, NotInscriptionChampionshipActivity.class);
                intent.putExtra("champId", champId);
                startActivity(intent);
            }
        });

        btn_move_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotIscriptionChampionshipInfoActivity.this, NotIscriptionChampionshipRankActivity.class);
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
        Intent intent = new Intent(NotIscriptionChampionshipInfoActivity.this, HomeActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickProfile(View view) {
        Intent intent = new Intent(NotIscriptionChampionshipInfoActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void ClickMyChampionship(View view) {
        Intent intent = new Intent(NotIscriptionChampionshipInfoActivity.this, MyChampionshipActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickChampionship(View view) {
        Intent intent = new Intent(NotIscriptionChampionshipInfoActivity.this, ChampionshipsActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickGallery(View view) {
        Intent intent = new Intent(NotIscriptionChampionshipInfoActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    public void ClickLogOut(View view) {
        db.updateStatus(Utils.STATUS_NOT_LOGGED, -1);
        Intent intent = new Intent(NotIscriptionChampionshipInfoActivity.this, LoginActivity.class);
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