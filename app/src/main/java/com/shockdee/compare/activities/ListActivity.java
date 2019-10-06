package com.shockdee.compare.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shockdee.compare.R;
import com.shockdee.compare.adapters.PhotoAdapter;
import com.shockdee.compare.database.DatabaseHelper;
import com.shockdee.compare.models.Photo;
import com.shockdee.compare.utilities.Utils;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    PhotoAdapter adapter;

    ArrayList<Photo> photoArrayList;

    Context context;

    DatabaseHelper myDB;
    SearchView sv;

    public static Activity la;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        la =this;

        context = this;
        myDB = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.rv);
        sv = findViewById(R.id.sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchTerm) {
                searchTerm = searchTerm.toLowerCase();
                ArrayList<Photo> newList = new ArrayList<>();
                for (Photo photo : photoArrayList){
                    String name = photo.photoProductName.toLowerCase();
                    if (name.contains(searchTerm)){
                        newList.add(photo);
                    }
                }
                adapter.setFilter(newList);
                return false;
            }
        });

        photoArrayList = Utils.initPhotoFromDB(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PhotoAdapter(this, photoArrayList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, ScrollingPhotoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void end(){
        finish();
    }

}
