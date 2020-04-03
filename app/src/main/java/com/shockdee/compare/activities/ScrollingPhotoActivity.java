package com.shockdee.compare.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shockdee.compare.R;
import com.shockdee.compare.database.DatabaseHelper;
import com.shockdee.compare.models.Brand;
import com.shockdee.compare.models.Photo;
import com.shockdee.compare.models.Store;
import com.shockdee.compare.models.Type;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ScrollingPhotoActivity extends AppCompatActivity {
    public static Activity listActivity;
    String brandTxt;
    String typeTxt;
    String storeTxt;
    DatabaseHelper myDB;

    String photoBrand;
    String photoType;
    String photoStore;
    String txtEdPhotoProductName;
    String txtEdPhotoDescription;
    String date;
    Double price;

    EditText edPhotoProductName;
    EditText edPhotoDescription;
    EditText edPrice;

    EditText edType;
    EditText edBrand;
    EditText edStore;
    String spinner = "";
    //TextView spinnerTxt;
    Spinner spPhotoType;
    Spinner spPhotoBrand;
    Spinner spPhotoStore;

    ArrayList<String> spTypeArrayList = new ArrayList<>();
    ArrayList<String> spBrandArrayList = new ArrayList<>();
    ArrayList<String> spStoreArrayList = new ArrayList<>();

    ArrayAdapter<String> storeArrayAdapter;
    ArrayAdapter<String> typeArrayAdapter;
    ArrayAdapter<String> brandArrayAdapter;

    //-----------------------------------
    String currentImagePath = null;
    private static final int IMAGE_REQUEST = 1;
    ImageView imagePath;
    String imageName;
    File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_photo);

        myDB = new DatabaseHelper(this);
        extractBrand();
        extractStore();
        extractType();

        //Casting
        imagePath = findViewById(R.id.photoResult);
        edPhotoDescription = findViewById(R.id.edPhotoDescription);
        edPhotoProductName = findViewById(R.id.edPhotoProductName);
        edPrice = findViewById(R.id.edPrice);

        edType = findViewById(R.id.edType);
        edBrand = findViewById(R.id.edBrand);
        edStore = findViewById(R.id.edStore);
        //spinnerTxt = findViewById(R.id.spinnerTxt);

        spPhotoBrand = findViewById(R.id.spPhotoBrand);
        spPhotoStore = findViewById(R.id.spPhotoStore);
        spPhotoType = findViewById(R.id.spPhotoType);

        spStoreArrayList.add(spinner);
        spBrandArrayList.add(spinner);
        spTypeArrayList.add(spinner);

        storeArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spStoreArrayList);
        spPhotoStore.setAdapter(storeArrayAdapter);
        Collections.sort(spStoreArrayList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        Collections.sort(spBrandArrayList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        Collections.sort(spTypeArrayList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        brandArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spBrandArrayList);
        spPhotoBrand.setAdapter(brandArrayAdapter);

        typeArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spTypeArrayList);
        spPhotoType.setAdapter(typeArrayAdapter);
        spPhotoType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spPhotoType.getSelectedItem().toString();
                if (name != "") {
                    edType.setText(name);
                    spTypeArrayList.clear();
                    extractType();
                    Collections.sort(spTypeArrayList, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });
                } else {
                    edType.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spPhotoBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spPhotoBrand.getSelectedItem().toString();
                if (name != "") {
                    edBrand.setText(name);
                    spBrandArrayList.clear();
                    extractBrand();
                    Collections.sort(spBrandArrayList, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });
                } else {
                    edBrand.setText("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spPhotoStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spPhotoStore.getSelectedItem().toString();
                if (name != "") {
                    edStore.setText(spPhotoStore.getSelectedItem().toString());
                    spStoreArrayList.clear();
                    extractStore();
                    Collections.sort(spStoreArrayList, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });
                } else {
                    edStore.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void savePhoto(View view) {

        txtEdPhotoDescription = edPhotoDescription.getText().toString();
        txtEdPhotoProductName = edPhotoProductName.getText().toString();
        photoBrand = edBrand.getText().toString();
        photoStore = edStore.getText().toString();
        photoType = edType.getText().toString();
        if (edPrice.getText().toString().length()>0 && edPrice.getText().toString()!=null){
            price = Double.valueOf(edPrice.getText().toString());
        }else {
            price = 0.00;
        }

        myDB.getWritableDatabase();
        Photo photo = new Photo(imageName, txtEdPhotoProductName, photoType, photoBrand, photoStore, date, price, txtEdPhotoDescription, currentImagePath);
        boolean isInserted = myDB.insertPhoto(photo);
        if (isInserted == true) {
            Toast.makeText(ScrollingPhotoActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
            boolean end = isFinished();
            if (end == true) {
                toListAcivity();
            }
            finish();
        } else {
            Toast.makeText(ScrollingPhotoActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void addBrand() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Ajout d'une nouvelle marque");
        View view = LayoutInflater.from(this).inflate(R.layout.add_dialog, null);
        final EditText edBrand = view.findViewById(R.id.edAddDialog);
        builder.setView(view);
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                brandTxt = edBrand.getText().toString();
                Brand brand = new Brand(brandTxt);
                if (brandTxt != null && brandTxt.length() > 0) {
                    boolean isInserted = myDB.insertBrand(brand);
                    if (isInserted == true) {
                        Toast.makeText(ScrollingPhotoActivity.this, "Data insterted", Toast.LENGTH_SHORT).show();
                        spBrandArrayList.clear();
                        if (spBrandArrayList.size() == 0) {
                            extractBrand();
                        }
                    } else {
                        Toast.makeText(ScrollingPhotoActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ScrollingPhotoActivity.this, "champs vide", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }

    public void addStore() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Ajout d'une nouvelle enseigne");
        View view = LayoutInflater.from(this).inflate(R.layout.add_dialog, null);
        final EditText edStore = view.findViewById(R.id.edAddDialog);
        builder.setView(view);
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                storeTxt = edStore.getText().toString();
                Store store = new Store(storeTxt);
                if (storeTxt != null && storeTxt.length() > 0) {
                    boolean isInserted = myDB.insertStore(store);
                    if (isInserted == true) {
                        spStoreArrayList.clear();
                        if (spStoreArrayList.size() == 0) {
                            extractStore();
                        }
                        Toast.makeText(ScrollingPhotoActivity.this, "Data insterted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ScrollingPhotoActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ScrollingPhotoActivity.this, "champs vide", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void addType() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Ajout d'une nouvelle cathégorie");
        View view = LayoutInflater.from(this).inflate(R.layout.add_dialog, null);
        final EditText edType = view.findViewById(R.id.edAddDialog);
        builder.setView(view);
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                typeTxt = edType.getText().toString();
                Type type = new Type(typeTxt);
                if (typeTxt != null && typeTxt.length() > 0) {
                    boolean isInserted = myDB.insertType(type);
                    if (isInserted == true) {
                        spTypeArrayList.clear();
                        if (spTypeArrayList.size() == 0) {
                            extractType();
                        }
                        Toast.makeText(ScrollingPhotoActivity.this, "Data insterted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ScrollingPhotoActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ScrollingPhotoActivity.this, "champs vide", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void extractType() {
        myDB.getWritableDatabase();
        Cursor cursor = myDB.spCursorType();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            spTypeArrayList.add(name);
        }
        myDB.close();
    }

    public void extractStore() {
        myDB.getWritableDatabase();
        Cursor cursor = myDB.spCursorStore();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            spStoreArrayList.add(name);
        }
        myDB.close();
    }

    public void extractBrand() {
        myDB.getWritableDatabase();
        Cursor cursor = myDB.spCursorBrand();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            spBrandArrayList.add(name);
        }
        myDB.close();
    }

    public void captureImage(View view) {
        txtEdPhotoProductName = edPhotoProductName.getText().toString();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                File imageFile = null;
                try {
                    imageFile = getImageFile();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (imageFile != null) {
                    Uri imageUri = FileProvider.getUriForFile(this, "com.compare.android.fileprovider", imageFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent, IMAGE_REQUEST);
                    Glide.with(ScrollingPhotoActivity.this)
                            .load(currentImagePath)
                            .into(imagePath);
                }
            }

    }

    private File getImageFile() throws IOException {
        String timestamp = new SimpleDateFormat("HHmmss").format(new Date());
        date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        imageName = "Compare_" + timestamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        imageFile = File.createTempFile(imageName, ".jpg", storageDir);
        currentImagePath = imageFile.getAbsolutePath();

        return imageFile;


    }

    public void add(View view) {

        switch (view.getId()) {
            case R.id.btnAddBrand:
                addBrand();
                break;
            case R.id.btnAddStore:
                addStore();
                break;
            case R.id.btnAddType:
                addType();
                break;
        }
    }

    public boolean isFinished() {
        ListActivity.la.finish();
        return true;
    }

    public void toListAcivity() {
        Intent intent = new Intent(ScrollingPhotoActivity.this, ListActivity.class);
        startActivity(intent);
    }
}
