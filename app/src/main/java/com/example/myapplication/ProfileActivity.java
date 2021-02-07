package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.Utils;

public class ProfileActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    DBHelper db;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        drawerLayout = findViewById(R.id.drawer_layout);
        name = findViewById(R.id.profileName);
        db = new DBHelper(this);

        name.setText(Utils.USER.getNAME()+ " " + Utils.USER.getLASTNAME());
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