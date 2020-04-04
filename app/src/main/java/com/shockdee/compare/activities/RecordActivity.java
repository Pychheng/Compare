package com.shockdee.compare.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shockdee.compare.R;
import com.shockdee.compare.database.DatabaseHelper;
import com.shockdee.compare.models.Photo;

public class RecordActivity extends AppCompatActivity {

    Photo photo;
    DatabaseHelper myDB;
    Context context;

    String message;
    String path;
    int idPhoto;

    TextView tvRecord;
    ImageView photoRecord;
    ImageView img_full_size;
    FloatingActionButton fabClose;

    public static Activity ra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        myDB = new DatabaseHelper(this);
        tvRecord = findViewById(R.id.tvRecord);
        photoRecord = findViewById(R.id.photoRecord);
        img_full_size = findViewById(R.id.img_full_size);
        context =this;
        ra = this;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            message = bundle.getString("toString");
            idPhoto = Integer.parseInt(bundle.getString("photoId"));
            path = bundle.getString("path");
            tvRecord.setText(message);
            Glide.with(this)
                    .load(path)
                    .thumbnail((float) 0.25)
                    .into(photoRecord);
        }
        photoRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecordActivity.this, FullsizeActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);

            }
        });

    }

    public void toModif(View view) {
        Intent intent = new Intent(RecordActivity.this, ModifActivity.class);
        intent.putExtra("photoId", String.valueOf(idPhoto));
        startActivity(intent);
    }
}
