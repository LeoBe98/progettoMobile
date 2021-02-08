package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        db = new DBHelper(this);

        name.setText(Utils.USER.getNAME()+ " " + Utils.USER.getLASTNAME());
        birthdate.setText("Birthdate: " +Utils.USER.getBIRTHDATE());
        fullAddress.setText("Full Address: " +Utils.USER.getFULLADDRESS());
        email.setText("Email: " +Utils.USER.getEMAIL());
        lcircuit.setText("Loved Circuit:" +Utils.USER.getLovedCircuit());
        hcircuit.setText("Hated Circuit:" +Utils.USER.getHatedCircuit());
        rnumber.setText("Race Number:" +Utils.USER.getRACENUMBER());
        lcar.setText("Loved Car:" +Utils.USER.getLovedCar());

        if(Utils.USER.getPROFILEPHOTO() != null) {
            image = ProfileImage.StringToBitMap(Utils.USER.getPROFILEPHOTO());
            iv.setImageBitmap(image);
            iv.setZ(2);
        }


        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });



    }

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
                recreate();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


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
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void ClickProfile(View view){
        recreate();
    }

    public void ClickMyChampionship(View view){
        Intent intent = new Intent(ProfileActivity.this, MyChampionshipActivity.class);
        startActivity(intent);
    }

    public void ClickChampionship(View view){
        Intent intent = new Intent(ProfileActivity.this, ChampionshipsActivity.class);
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

}