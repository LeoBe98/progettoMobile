package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.ProfileImage;
import com.example.myapplication.tools.Utils;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    DBHelper db;
    TextView name, birthdate, fullAddress, email, lcircuit, hcircuit, rnumber, lcar;
    Bitmap image;
    String imageString;
    ImageView iv;
    Button btn_change;
    Integer userId;
    ImageView l_circuit, h_circuit, l_car, r_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userId = Utils.USER.getID();
        drawerLayout = findViewById(R.id.drawer_layout);
        name = findViewById(R.id.profileName);
        birthdate = findViewById(R.id.profileBirthdate);
        fullAddress = findViewById(R.id.profileFullAddress);
        email = findViewById(R.id.profileEmail);
        lcircuit = findViewById(R.id.ProfileLovedCircuit);
        hcircuit = findViewById(R.id.profileHatedCircuit);
        rnumber = findViewById(R.id.profileRaceNumber);
        lcar = findViewById(R.id.profileLovedCar);
        iv = findViewById(R.id.imageViewProfile);
        btn_change = findViewById(R.id.btn_changeImageProfile);
        l_circuit = (ImageView) findViewById(R.id.editLCircuitProfile);
        h_circuit = (ImageView) findViewById(R.id.editHCircuitProfile);
        l_car = (ImageView) findViewById(R.id.editCarProfile);
        r_number = (ImageView) findViewById(R.id.editNumberProfile);
        db = new DBHelper(this);

        TextView nome = (TextView) findViewById(R.id.menuName);
        nome.setText(Utils.USER.getNAME() + " " + Utils.USER.getLASTNAME());
        if (Utils.USER.getPROFILEPHOTO() != "") {
            Bitmap bitmapProfile = ProfileImage.StringToBitMap(Utils.USER.getPROFILEPHOTO());
            ImageView profileMenu = (ImageView) findViewById(R.id.menuProfileImage);
            profileMenu.setImageBitmap(bitmapProfile);
        }

        name.setText(Utils.USER.getNAME()+ " " + Utils.USER.getLASTNAME());
        birthdate.setText("Birthdate: " +Utils.USER.getBIRTHDATE());
        fullAddress.setText("Full Address: " +Utils.USER.getFULLADDRESS());
        email.setText("Email: " +Utils.USER.getEMAIL());
        lcircuit.setText("Loved Circuit: " +Utils.USER.getLovedCircuit());
        hcircuit.setText("Hated Circuit: " +Utils.USER.getHatedCircuit());
        rnumber.setText("Race Number: " +Utils.USER.getRACENUMBER());
        lcar.setText("Loved Car: " +Utils.USER.getLovedCar());

        if(Utils.USER.getPROFILEPHOTO() != null) {
            image = ProfileImage.StringToBitMap(Utils.USER.getPROFILEPHOTO());
            iv.setImageBitmap(image);
            iv.setZ(2);
        }



        //region UPDATE button

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();

            }
        });

        l_circuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeLovedCircuit(ProfileActivity.this);
            }
        });

        h_circuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeHatedCircuit(ProfileActivity.this);
            }
        });

        l_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeLovedCar(ProfileActivity.this);
            }
        });

        r_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeRaceNumber(ProfileActivity.this);
            }
        });
        //endregion
    }

    //region update loved circuit
    private void ChangeLovedCircuit(Activity a) {
        ProfileActivity.CustomDialogLovedCircuit cdd = new ProfileActivity.CustomDialogLovedCircuit(a);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }

    private class CustomDialogLovedCircuit extends Dialog implements android.view.View.OnClickListener {

        public Activity c;
        public Button update, esc;
        public EditText loved_circuit;

        public CustomDialogLovedCircuit(Activity a) {
            super(a);
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_update_loved_circuit);
            update = (Button) findViewById(R.id.dialog_confirm_loved_circuit_type_yes);
            esc = (Button) findViewById(R.id.dialog_update_loved_circuit_type_no);
            loved_circuit = (EditText) findViewById(R.id.et_dialog_update_loved_circuit);

            update.setOnClickListener(this);
            esc.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_confirm_loved_circuit_type_yes:
                   String s = loved_circuit.getText().toString();
                    if(s.isEmpty()){

                        Toast.makeText(ProfileActivity.this, "Campo vuoto", Toast.LENGTH_LONG).show();
                    }
                    else {
                        db.updateLovedCircuit(s, userId);
                        lcircuit.setText("Loved Circuit: " +s);
                        Utils.USER.setLovedCircuit(s);
                        dismiss();
                    }

                    //

                    break;
                case R.id.dialog_update_loved_circuit_type_no:
                    //
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }
    //endregion

    //region update hated circuit
    private void ChangeHatedCircuit(Activity a) {
        ProfileActivity.CustomDialogHatedCircuit cdd = new ProfileActivity.CustomDialogHatedCircuit(a);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }

    private class CustomDialogHatedCircuit extends Dialog implements android.view.View.OnClickListener {

        public Activity c;
        public Button update, esc;
        public EditText hated_circuit;

        public CustomDialogHatedCircuit(Activity a) {
            super(a);
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_update_hated_circuit);
            update = (Button) findViewById(R.id.dialog_confirm_hated_circuit_type_yes);
            esc = (Button) findViewById(R.id.dialog_update_hated_circuit_type_no);
            hated_circuit = (EditText) findViewById(R.id.et_dialog_update_hated_circuit);

            update.setOnClickListener(this);
            esc.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_confirm_hated_circuit_type_yes:
                    String s = hated_circuit.getText().toString();
                    if(s.isEmpty()){

                        Toast.makeText(ProfileActivity.this, "Campo vuoto", Toast.LENGTH_LONG).show();
                    }
                    else {
                        db.updateHatedCircuit(s, userId);
                        hcircuit.setText("Hated Circuit: " +s);
                        Utils.USER.setHatedCircuit(s);
                        dismiss();
                    }

                    //

                    break;
                case R.id.dialog_update_hated_circuit_type_no:
                    //
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }
    //endregion

    //region update loved car
    private void ChangeLovedCar(Activity a) {
        ProfileActivity.CustomDialogLovedCar cdd = new ProfileActivity.CustomDialogLovedCar(a);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }

    private class CustomDialogLovedCar extends Dialog implements android.view.View.OnClickListener {

        public Activity c;
        public Button update, esc;
        public EditText loved_car;

        public CustomDialogLovedCar(Activity a) {
            super(a);
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_update_loved_car);
            update = (Button) findViewById(R.id.dialog_confirm_loved_car_type_yes);
            esc = (Button) findViewById(R.id.dialog_update_loved_car_type_no);
            loved_car = (EditText) findViewById(R.id.et_dialog_update_loved_car);

            update.setOnClickListener(this);
            esc.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_confirm_loved_car_type_yes:
                    String s = loved_car.getText().toString();
                    if(s.isEmpty()){

                        Toast.makeText(ProfileActivity.this, "Campo vuoto", Toast.LENGTH_LONG).show();
                    }
                    else {
                        db.updateLovedCar(s, userId);
                        lcar.setText("Loved Car: " + s);
                        Utils.USER.setLovedCar(s);
                        dismiss();
                    }
                    //

                    break;
                case R.id.dialog_update_loved_car_type_no:
                    //
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }
    //endregion

    //region update race number
    private void ChangeRaceNumber(Activity a) {
        ProfileActivity.CustomDialogRaceNumber cdd = new ProfileActivity.CustomDialogRaceNumber(a);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }

    private class CustomDialogRaceNumber extends Dialog implements android.view.View.OnClickListener {

        public Activity c;
        public Button update, esc;
        public EditText race_number;

        public CustomDialogRaceNumber(Activity a) {
            super(a);
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_update_race_number);
            update = (Button) findViewById(R.id.dialog_confirm_race_number_type_yes);
            esc = (Button) findViewById(R.id.dialog_update_race_number_type_no);
            race_number = (EditText) findViewById(R.id.et_dialog_update_race_number);

            update.setOnClickListener(this);
            esc.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dialog_confirm_race_number_type_yes:
                    String s = race_number.getText().toString();
                    if(s.isEmpty()){

                        Toast.makeText(ProfileActivity.this, "Void Field", Toast.LENGTH_LONG).show();
                    }
                    else if(checkRaceNumber(s)){
                        db.updateRaceNumber(s, userId);
                        rnumber.setText("Race Number: " + s);
                        Utils.USER.setRACENUMBER(s);
                        dismiss();
                    }
                    //

                    break;
                case R.id.dialog_update_race_number_type_no:
                    //
                    dismiss();
                    break;
                default:
                    break;
            }
        }

        private boolean checkRaceNumber(String _raceNumber) {
            try {
                int controlNumber = Integer.parseInt(_raceNumber);
                if (controlNumber < 0) {
                    Toast.makeText(ProfileActivity.this, "No negative number", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (_raceNumber.length() > 2) {
                    Toast.makeText(ProfileActivity.this, "Race Number must be between 0 and 99", Toast.LENGTH_LONG).show();
                    return false;
                }
            } catch (NumberFormatException ex) {
                Toast.makeText(ProfileActivity.this, "Race Number must be a number", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
    }
    //endregion



    public void uploadImage(){
        //carico l'immagine
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        ((Activity) this).startActivityForResult(intent, 1000 );


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageString = ProfileImage.BitMapToString(bitmap);
                Log.e("USER_ID", ""+Utils.USER.getID());
                db.updateImage(imageString, Utils.USER.getID());
                Utils.USER.setPROFILEPHOTO(imageString);
                recreate();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


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
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickProfile(View view){
        recreate();
    }

    public void ClickMyChampionship(View view){
        Intent intent = new Intent(ProfileActivity.this, MyChampionshipActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickChampionship(View view){
        Intent intent = new Intent(ProfileActivity.this, ChampionshipsActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void ClickGallery(View view){
        Intent intent = new Intent(ProfileActivity.this, GalleryActivity.class);
        startActivity(intent);
    }

    public void ClickLogOut(View view){
        db.updateStatus(Utils.STATUS_NOT_LOGGED, -1);
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
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