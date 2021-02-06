package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.ReadFromJson;
import com.example.myapplication.tools.Utils;

public class HomeActivity extends AppCompatActivity {
    Button logOut, gallery;
    DBHelper db;
    Integer userId;
    String name, lastname;
    TextView textView;

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = new DBHelper(this);
        userId = Utils.USER.getID();
        name = Utils.USER.getNAME();
        lastname = Utils.USER.getLASTNAME();

        drawerLayout = findViewById(R.id.drawer_layout);
        textView = (TextView) findViewById(R.id.textview_hello);

        textView.setText("Welcome Back " +name+ " " +lastname);


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
        recreate();
    }

    public void ClickLogOut(View view){
        db.updateStatus(Utils.STATUS_NOT_LOGGED, -1);
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public void ClickGallery(View view){
        Intent intent = new Intent(HomeActivity.this, GalleryActivity.class);
        startActivity(intent);
    }
}
