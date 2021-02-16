package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.tools.ObjectChampionship;
import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.ProfileImage;
import com.example.myapplication.tools.ObjectRace;
import com.example.myapplication.tools.Utils;

import java.util.ArrayList;

public class NotInscriptionChampionshipActivity extends AppCompatActivity {
    DBHelper db;
    Integer champId, userId;
    Button btn_inscription, btn_move_info, btn_move_rank;
    TextView nameChampionship;
    ObjectChampionship championship;
    ObjectRace race;
    ArrayList<ObjectRace> raceList;
    ListView lv_race;
    AdapterRace adapterRace;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_inscription_championship);

        db = new DBHelper(this);
        userId = Utils.USER.getID();
        drawerLayout = findViewById(R.id.drawer_layout);

        nameChampionship = (TextView) findViewById(R.id.tv_name_not_iscription_championship);
        btn_inscription = (Button) findViewById(R.id.btn_iscription);
        btn_move_info = (Button) findViewById(R.id.btn_not_championship_to_info);
        btn_move_rank = (Button) findViewById(R.id.btn_not_championship_to_rank);
        lv_race = (ListView) findViewById(R.id.lv_races_not_iscription_championship);
        raceList = new ArrayList<>();

        //recupero champId
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

        //Set menu
        TextView nome = (TextView) findViewById(R.id.menuName);
        nome.setText(Utils.USER.getNAME() + " " + Utils.USER.getLASTNAME());
        if (Utils.USER.getPROFILEPHOTO() != "") {
            Bitmap bitmapProfile = ProfileImage.StringToBitMap(Utils.USER.getPROFILEPHOTO());
            ImageView profileMenu = (ImageView) findViewById(R.id.menuProfileImage);
            profileMenu.setImageBitmap(bitmapProfile);
        }

        //Recupero info campionato
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
        championship = new ObjectChampionship(id, name, logo, flags, fuel_consumption, tires_consumption, help, car_list);
        nameChampionship.setText(championship.getNAME());

        //Recupero calendario campionato
        Cursor getCalendarioChampionship = db.getCalendarChampionship(champId);
        for (getCalendarioChampionship.moveToFirst(); !getCalendarioChampionship.isAfterLast(); getCalendarioChampionship.moveToNext()) {
            Integer id_cal = (getCalendarioChampionship.getInt(getCalendarioChampionship.getColumnIndex("id")));
            Integer id_camp = (getCalendarioChampionship.getInt(getCalendarioChampionship.getColumnIndex("idCamp")));
            String circuit = (getCalendarioChampionship.getString(getCalendarioChampionship.getColumnIndex("circuit")));
            String data = (getCalendarioChampionship.getString(getCalendarioChampionship.getColumnIndex("data")));
            race = new ObjectRace(id_cal, id_camp, circuit, data);
            raceList.add(race);
        }

        //Invio lista all'adapter e lo setto alla listView
        adapterRace = new AdapterRace(NotInscriptionChampionshipActivity.this, raceList);
        lv_race.setAdapter(adapterRace);

        //Gestisco il click
        lv_race.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(NotInscriptionChampionshipActivity.this, NotIscriptionChampionshipRaceActivity.class);
                intent.putExtra("raceId", raceList.get(i).getID());
                intent.putExtra("champId", champId);
                startActivity(intent);
            }
        });

        //Bottone iscrizione
        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inscription(NotInscriptionChampionshipActivity.this);
            }
        });

        //region navigation championship
        btn_move_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotInscriptionChampionshipActivity.this, NotInscriptionChampionshipInfoActivity.class);
                intent.putExtra("champId", champId);
                startActivity(intent);
            }
        });

        btn_move_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotInscriptionChampionshipActivity.this, NotIscriptionChampionshipRankActivity.class);
                intent.putExtra("champId", champId);
                startActivity(intent);
            }
        });
        //endregion
    }


    //region inscription
    private void Inscription(Activity a) {
        NotInscriptionChampionshipActivity.CustomDialogInscription cdd = new NotInscriptionChampionshipActivity.CustomDialogInscription(a);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }

    private class CustomDialogInscription extends Dialog implements android.view.View.OnClickListener {

        public Activity c;
        public Button update, esc;
        public EditText car, team;
        public TextView carList;

        public CustomDialogInscription(Activity a) {
            super(a);
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_inscription_championship);
            update = (Button) findViewById(R.id.dialog_inscription_type_yes);
            esc = (Button) findViewById(R.id.dialog_inscription_type_no);
            carList = (TextView) findViewById(R.id.dialog_disponible_cars);
            car = (EditText) findViewById(R.id.et_dialog_choose_car);
            team = (EditText) findViewById(R.id.et_dialog_choose_team);

            //Stampo macchine disponibili
            carList.setText("Disponible car: " + championship.getCarList());

            update.setOnClickListener(this);
            esc.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            String[] controlCar = championship.CAR_LIST.split(",");
            switch (v.getId()) {
                case R.id.dialog_inscription_type_yes:
                    String s1 = team.getText().toString();
                    String s2 = car.getText().toString();
                    if (s1.isEmpty() || s2.isEmpty()) {
                        Toast.makeText(NotInscriptionChampionshipActivity.this, "Void field", Toast.LENGTH_LONG).show();
                    } else if (!checkCar(s2, controlCar)) {
                        Toast.makeText(NotInscriptionChampionshipActivity.this, "Car not disponible, write equals list", Toast.LENGTH_LONG).show();
                    } else {
                        db.addIscription(Utils.USER.getID(), champId, s1, s2);
                        Intent intent = new Intent(NotInscriptionChampionshipActivity.this, MyChampionshipActivity.class);
                        intent.putExtra("userId", Utils.USER.getID());
                        startActivity(intent);
                        dismiss();
                    }
                    break;
                case R.id.dialog_inscription_type_no:
                    dismiss();
                    break;
                default:
                    break;
            }
        }

        private boolean checkCar(String s2, String[] controlCar) {
            //Controllo se la macchina inserita è uguale ad una di quelle disponibili
            for (int i = 0; i < controlCar.length; i++) {
                if (s2.equals(controlCar[i])) {
                    return true;
                }
            }
            return false;
        }
    }
    //endregion


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
        Intent intent = new Intent(NotInscriptionChampionshipActivity.this, HomeActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickProfile(View view) {
        Intent intent = new Intent(NotInscriptionChampionshipActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void ClickMyChampionship(View view) {
        Intent intent = new Intent(NotInscriptionChampionshipActivity.this, MyChampionshipActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickChampionship(View view) {
        Intent intent = new Intent(NotInscriptionChampionshipActivity.this, ChampionshipsActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickGallery(View view) {
        Intent intent = new Intent(NotInscriptionChampionshipActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    public void ClickLogOut(View view) {
        db.updateStatus(Utils.STATUS_NOT_LOGGED, -1);
        Intent intent = new Intent(NotInscriptionChampionshipActivity.this, LoginActivity.class);
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