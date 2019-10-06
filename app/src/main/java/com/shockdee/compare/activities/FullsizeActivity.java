package com.shockdee.compare.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shockdee.compare.R;

public class FullsizeActivity extends AppCompatActivity {

    ImageView img_full_size;
    String path;

    int width;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullsize);

        img_full_size = findViewById(R.id.img_full_size);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            path = bundle.getString("path");
            Glide.with(this)
                    .load(path)
                    .into(img_full_size);
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.8), (int)(height*0.8));

    }
}
