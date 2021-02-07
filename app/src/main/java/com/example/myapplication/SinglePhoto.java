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

public class SinglePhoto extends AppCompatActivity {
    ImageView singlePhoto;
    String image;
    Button shareButton;

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
             shareImage(getApplicationContext(), resID);

            }
        });
    }

    public static void shareImage(Context context, Integer resource_id) {
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), resource_id);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), b, "Title", null);
        Uri imageUri = Uri.parse(path);
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        context.startActivity(Intent.createChooser(share, "Select"));
    }

}

