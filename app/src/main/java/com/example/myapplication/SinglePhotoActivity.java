package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class SinglePhotoActivity extends AppCompatActivity {
    ImageView singlePhoto;
    String image;
    Button shareButton;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_photo);
        singlePhoto = (ImageView) findViewById(R.id.singlePhoto);
        shareButton = (Button) findViewById(R.id.btn_share);

        //Recupero nome immagine e la mostro
        image = getIntent().getStringExtra("imageName");
        final int resID = getResources().getIdentifier(image, "drawable", getPackageName());

        Log.e("resId", ""+resID);
        singlePhoto.setImageResource(resID);


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /* shareImage(getApplicationContext(), resID);
                //Recupero Imm
                Bitmap b = BitmapFactory.decodeResource(getApplicationContext().getResources(), resID);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), b, "Img", null);
                Uri imageUri = Uri.parse(path);


                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, imageUri);


                Intent startIntent = Intent.createChooser(sharingIntent, context.getResources().getString(R.string.app_name));
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(startIntent);*/
            }
        });
    }

    public static void shareImage(Context context, Integer resource_id) {


    /*    */


    }

}

