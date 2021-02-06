package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.tools.CreateList;
import com.example.myapplication.tools.MyAdapter;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        //creo il recyclerview per contenere le foto
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        //creo un array di foto da stampare a schermo
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<CreateList> createLists = prepareData();
        MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);


    }


    private final String image_titles[] = {
            "img1",
            "img2",
            "img3",
            "img4",
            "img5",
            "img6",
            "img7",
            "img8",

    };

    private final Integer image_ids[] = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8,
    };

    private ArrayList<CreateList> prepareData() {
        //popolo l'array
        ArrayList<CreateList> theimage = new ArrayList<>();
        for (int i = 0; i < image_titles.length; i++) {
            CreateList createList = new CreateList();
            createList.setImage_title(image_titles[i]);
            createList.setImage_ID(image_ids[i]);
            theimage.add(createList);
        }
        return theimage;
    }
}
