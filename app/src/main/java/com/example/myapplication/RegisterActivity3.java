package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.tools.DBHelper;
import com.example.myapplication.tools.ProfileImage;
import com.example.myapplication.tools.User;
import com.example.myapplication.tools.Utils;

import java.io.IOException;

public class RegisterActivity3 extends AppCompatActivity {
    Button register_button, upload_image;
    String name, lastname, birthdate, fullAddress, city, postalcode, email, password, repeatPassword;
    String raceNumber, lovedCircuit, hatedCircuit, lovedCar;
    String imageString;
    ImageView profile_image;
    Bitmap bitmap;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        db = new DBHelper(this);
        //prendo i dati dalla activity precedente
        name = getIntent().getStringExtra("name");
        lastname = getIntent().getStringExtra("lastname");
        birthdate = getIntent().getStringExtra("birthdate");
        fullAddress = getIntent().getStringExtra("fullAddress");
        city = getIntent().getStringExtra("city");
        postalcode = getIntent().getStringExtra("postalcode");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        repeatPassword = getIntent().getStringExtra("repeatPassword");
        raceNumber = getIntent().getStringExtra("raceNumber");
        lovedCircuit = getIntent().getStringExtra("lovedCircuit");
        hatedCircuit = getIntent().getStringExtra("hatedCircuit");
        lovedCar = getIntent().getStringExtra("lovedCar");

        upload_image = (Button) findViewById(R.id.uploadeImage);
        register_button = (Button) findViewById(R.id.register3_register);
        profile_image = (ImageView) findViewById(R.id.profile_image_register);



        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });



        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registration(name, lastname, birthdate, fullAddress, city, postalcode, email, password, raceNumber, lovedCircuit, hatedCircuit, lovedCar, imageString);

            }
        });
    }



    public void uploadImage(){
        //carico l'immagine
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        ((Activity) this).startActivityForResult(intent, 1000 );

    }

    public void Registration(String name, String lastname, String birthdate, String fullAddress, String city, String postalcode, String email, String password, String raceNumber, String lovedCircuit, String hatedCircuit, String lovedCar, String imageString){
        Log.e("dati registrazione", name + lastname + birthdate + fullAddress + city + postalcode + email + password + raceNumber + lovedCircuit + hatedCircuit);
        db.addUser(name, lastname, birthdate, fullAddress, city, postalcode, email, password, raceNumber, lovedCircuit, hatedCircuit, lovedCar, imageString);

        Intent intent = new Intent(RegisterActivity3.this, LoginActivity.class);
        startActivity(intent);
        Toast.makeText(RegisterActivity3.this, "Registrazione effettuata", Toast.LENGTH_LONG).show();
        finish();
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
                profile_image.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
