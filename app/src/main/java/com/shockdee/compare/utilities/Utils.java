package com.shockdee.compare.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shockdee.compare.R;
import com.shockdee.compare.activities.ScrollingPhotoActivity;
import com.shockdee.compare.activities.SettingsActivity;
import com.shockdee.compare.database.DatabaseHelper;
import com.shockdee.compare.models.Brand;
import com.shockdee.compare.models.Photo;
import com.shockdee.compare.models.Store;
import com.shockdee.compare.models.Type;

import java.util.ArrayList;

public class Utils {

    public static ArrayList<Photo> initPhotoFromDB(Context context){
        DatabaseHelper db = new DatabaseHelper(context);
        return db.selectAllPhoto();
    }

    public static ArrayList<Brand> initBrandFromDB(Context context){
        DatabaseHelper db = new DatabaseHelper(context);
        return db.selectAllBrand();
    }

    public static ArrayList<Store> initStoreFromDB(Context context){
        DatabaseHelper db = new DatabaseHelper(context);
        return db.selectAllStore();
    }

    public static ArrayList<Type> initTypeFromDB(Context context){
        DatabaseHelper db = new DatabaseHelper(context);
        return db.selectAllType();
    }

    public static void delPhoto(Context context, int photoId){
        DatabaseHelper db = new DatabaseHelper(context);
        db.delPhoto(photoId);
    }

    public static boolean delStore(Context context, int storeId){
        DatabaseHelper db = new DatabaseHelper(context);
        db.delStore(storeId);
        return true;
    }

    public static boolean delBrand(Context context, int brandId){
        DatabaseHelper db = new DatabaseHelper(context);
        db.delBrand(brandId);
        return true;
    }

    public static boolean delType(Context context, int typeId){
        DatabaseHelper db = new DatabaseHelper(context);
        db.delType(typeId);
        return true;
    }
    //==============================================================================================
    /*public static boolean ajoutStore(Context context){
        new addStoreDialog();
        return true;
    }
    private static final class addStoreDialog implements DialogInterface.OnClickListener{

        Context context;
        DatabaseHelper myDB;
        @Override
        public void onClick(DialogInterface dialog, int which) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Ajout d'une nouvelle enseigne");
            View view = LayoutInflater.from(context).inflate(R.layout.add_dialog, null);
            final EditText edStore = view.findViewById(R.id.edAddDialog);
            builder.setView(view);
            builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String storeTxt = edStore.getText().toString();
                    Store store = new Store(storeTxt);
                    if (storeTxt != null && storeTxt.length() > 0) {
                        boolean isInserted = myDB.insertStore(store);
                        if (isInserted == true) {
                            Toast.makeText(context, "L'enseigne : " + storeTxt + "a été ajoutée", Toast.LENGTH_SHORT).show();
                            //finish();
                            //Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                            //startActivity(intent);

                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "champs vide : Aucune nouvelle entrée a été ajoutée", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            myDB.close();
            builder.show();
        }
    }*/
}
