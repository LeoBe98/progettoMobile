package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
        int resID = getResources().getIdentifier(image, "drawable", getPackageName());
        Log.e("resId", ""+resID);
        singlePhoto.setImageResource(resID);


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*  Log.e("share", "ci sono");
                Bitmap bm = ((android.graphics.drawable.BitmapDrawable) singlePhoto.getDrawable()).getBitmap();
                try {
                    java.io.File file = new java.io.File(getExternalCacheDir() + "/image.jpg");
                    java.io.OutputStream out = new java.io.FileOutputStream(file);
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {}
                Intent iten = new Intent(android.content.Intent.ACTION_SEND);*/
                //iten.setType("*/*");
                /*
                iten.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new java.io.File(getExternalCacheDir() + "/image.jpg")));
                startActivity(Intent.createChooser(iten, "Send image"));*/

            }
        });
    }

}

