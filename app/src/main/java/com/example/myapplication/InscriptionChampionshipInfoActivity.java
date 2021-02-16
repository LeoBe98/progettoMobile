package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.tools.ObjectChampionship;
import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.ProfileImage;
import com.example.myapplication.tools.Utils;

public class InscriptionChampionshipInfoActivity extends AppCompatActivity {
    Integer champId, userId;
    DBHelper db;
    Button btn_move_rank, btn_move_championship, btn_notify;
    TextView tv_name, tv_flags, tv_fuel, tv_tire, tv_driving, tv_forum;
    ObjectChampionship championship;
    DrawerLayout drawerLayout;
    ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_championship_info);

        db = new DBHelper(this);
        userId = Utils.USER.getID();
        drawerLayout = findViewById(R.id.drawer_layout);

        tv_name = (TextView) findViewById(R.id.tv_name_iscription_championship_info);
        tv_flags = (TextView) findViewById(R.id.tv_setting_flags_result_championship_info);
        tv_fuel = (TextView) findViewById(R.id.tv_setting_FuelConsumption_result_championship_info);
        tv_tire = (TextView) findViewById(R.id.tv_setting_tire_wear_result_championship_info);
        tv_driving = (TextView) findViewById(R.id.tv_setting_driving_aids_result_championship_info);
        tv_forum = (TextView) findViewById(R.id.forum_championship_info);
        im = (ImageView) findViewById(R.id.logoChamp);
        btn_move_rank = (Button) findViewById(R.id.btn_inscription_info_to_rank);
        btn_move_championship = (Button) findViewById(R.id.btn_inscription_info_to_championship);
        btn_notify = (Button) findViewById(R.id.btn_notify);

        //Recupero champId
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

        //Set Menu
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

        //Setto info campionato
        tv_name.setText(championship.getNAME());
        tv_flags.setText(championship.getFLAGS());
        tv_fuel.setText(championship.getFuelConsumption());
        tv_tire.setText(championship.getTiresConsumption());
        tv_driving.setText(championship.getHELP());

        //Setto immagine campionato
        if (name.equals("Leon Supercopa")) {
            im.setImageResource(R.drawable.logochamp0);
        } else {
            im.setImageResource(R.drawable.logochamp1);
        }

        //Setto forum come link
        tv_forum.setMovementMethod(LinkMovementMethod.getInstance());
        tv_forum.setText("forum." + championship.getNAME().replaceAll("\\s+", "") + ".com");

        //region navigation championship
        btn_move_championship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InscriptionChampionshipInfoActivity.this, InscriptionChampionshipActivity.class);
                intent.putExtra("champId", champId);
                startActivity(intent);
            }
        });

        btn_move_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InscriptionChampionshipInfoActivity.this, InscriptionChampionshipRankActivity.class);
                intent.putExtra("champId", champId);
                startActivity(intent);
            }
        });
        //endregion

        btn_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notify(InscriptionChampionshipInfoActivity.this);
            }
        });

    }

    //region update championship
    private void Notify(Activity a) {
        InscriptionChampionshipInfoActivity.NotifyDialog cdd = new InscriptionChampionshipInfoActivity.NotifyDialog(a);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }

    private class NotifyDialog extends Dialog implements android.view.View.OnClickListener {
        public Activity c;
        public Button update, esc;
        public EditText flag;

        public NotifyDialog(Activity a) {
            super(a);
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_notify);
            update = (Button) findViewById(R.id.dialog_notify_type_yes);
            esc = (Button) findViewById(R.id.dialog_notify_type_no);
            flag = (EditText) findViewById(R.id.et_dialog_notify);

            update.setOnClickListener(this);
            esc.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_notify_type_yes:
                    String s = flag.getText().toString();
                    if (s.isEmpty()) {

                        Toast.makeText(InscriptionChampionshipInfoActivity.this, "Void field", Toast.LENGTH_LONG).show();
                    } else {
                        db.updateFlag(s, champId);
                        tv_flags.setText(s);
                        championship.setFLAGS(s);

                        //Imposto channel notifica per versioni maggiori all'8 per problemi di compatibilità
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                            NotificationManager manager = getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel);
                        }

                        // Create an Intent for the activity you want to start
                        Intent resultIntent = new Intent(InscriptionChampionshipInfoActivity.this, InscriptionChampionshipInfoActivity.class);
                        resultIntent.putExtra("champId", champId);
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(InscriptionChampionshipInfoActivity.this);
                        stackBuilder.addNextIntentWithParentStack(resultIntent);
                        PendingIntent resultPendingIntent =
                                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        //Creo la notifica e setto titolo icon e contenuto
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(InscriptionChampionshipInfoActivity.this, "My Notification");
                        builder.setContentTitle("Modifica");
                        builder.setContentText("L'impostazione flag del tuo campionato è stata modificata in " + s);
                        builder.setSmallIcon(R.drawable.icon_championship);
                        builder.setSound(uri);
                        builder.setContentIntent(resultPendingIntent);
                        builder.setAutoCancel(true);

                        //"Invio" la notifica
                        NotificationManagerCompat manager = NotificationManagerCompat.from(InscriptionChampionshipInfoActivity.this);
                        manager.notify(1, builder.build());

                        dismiss();
                    }
                    break;
                case R.id.dialog_notify_type_no:
                    dismiss();
                    break;
                default:
                    break;
            }
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
        Intent intent = new Intent(InscriptionChampionshipInfoActivity.this, HomeActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickProfile(View view) {
        Intent intent = new Intent(InscriptionChampionshipInfoActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void ClickMyChampionship(View view) {
        Intent intent = new Intent(InscriptionChampionshipInfoActivity.this, MyChampionshipActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickChampionship(View view) {
        Intent intent = new Intent(InscriptionChampionshipInfoActivity.this, ChampionshipsActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickGallery(View view) {
        Intent intent = new Intent(InscriptionChampionshipInfoActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    public void ClickLogOut(View view) {
        db.updateStatus(Utils.STATUS_NOT_LOGGED, -1);
        Intent intent = new Intent(InscriptionChampionshipInfoActivity.this, LoginActivity.class);
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