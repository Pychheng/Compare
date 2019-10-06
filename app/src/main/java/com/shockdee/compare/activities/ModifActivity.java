package com.shockdee.compare.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.shockdee.compare.utilities.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * faire les recherches
 */
public class ModifActivity extends AppCompatActivity {

    int idPhoto;
    Bundle bundle;
    String brandTxt;
    String storeTxt;
    String typeTxt;

    ArrayList<Photo> photoArrayList;
    Photo photo;

    ImageView modifImg;
    DatabaseHelper myDB;

    String modifPhotoName;
    String modifBrand;
    String modifType;
    String modifStore;
    String txtEdModifProductName;
    String txtEdModifDescription;
    String modifDate;
    Double modifPrice;
    String modifPath;

    EditText edModifProductName;
    EditText edModifDescription;
    EditText edModifPrice;

    EditText edModifType;
    EditText edModifBrand;
    EditText edModifStore;
    String spinner = "";
    TextView spinnerTxt;
    Spinner spModifType;
    Spinner spModifBrand;
    Spinner spModifStore;

    ArrayList<String> spModifTypeArrayList = new ArrayList<>();
    ArrayList<String> spModifBrandArrayList = new ArrayList<>();
    ArrayList<String> spModifStoreArrayList = new ArrayList<>();

    ArrayAdapter<String> modifStoreArrayAdapter;
    ArrayAdapter<String> modifTypeArrayAdapter;
    ArrayAdapter<String> modifBrandArrayAdapter;

    Button modifBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif);

        myDB = new DatabaseHelper(this);
        extractBrand();
        extractStore();
        extractType();


        spModifStoreArrayList.add(spinner);
        spModifBrandArrayList.add(spinner);
        spModifTypeArrayList.add(spinner);

        edModifBrand = findViewById(R.id.edModifBrand);
        edModifDescription = findViewById(R.id.edModifDescription);
        edModifProductName = findViewById(R.id.edModifProductName);
        edModifPrice = findViewById(R.id.edModifPrice);
        edModifStore = findViewById(R.id.edModifStore);
        edModifType = findViewById(R.id.edModifType);
        modifImg = findViewById(R.id.modifImg);

        spModifBrand = findViewById(R.id.spModifBrand);
        spModifStore = findViewById(R.id.spModifStore);
        spModifType = findViewById(R.id.spModifType);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            idPhoto = Integer.parseInt(bundle.getString("photoId"));
            photo = myDB.searchPhotoById(idPhoto);
            edModifProductName.setText(photo.photoProductName);
            edModifPrice.setText(String.valueOf(photo.photoPrice));
            edModifType.setText(photo.photoProductType);
            edModifBrand.setText(photo.photoBrand);
            edModifStore.setText(photo.photoStore);
            edModifDescription.setText(photo.photoDescription);
            modifDate = photo.photoDate;
            modifPath = photo.photoPath;
            modifPhotoName = photo.photoName;
            Glide.with(this)
                    .load(photo.photoPath)
                    .into(modifImg);
        }

        modifBtn = findViewById(R.id.modifBtn);
        modifBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modif();
            }
        });
        //====================================Spinner===============================================

        modifStoreArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spModifStoreArrayList);
        spModifStore.setAdapter(modifStoreArrayAdapter);

        modifBrandArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spModifBrandArrayList);
        spModifBrand.setAdapter(modifBrandArrayAdapter);

        modifTypeArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spModifTypeArrayList);
        spModifType.setAdapter(modifTypeArrayAdapter);

        spModifType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spModifType.getSelectedItem().toString();
                if (name != "") {
                    edModifType.setText(name);
                    spModifTypeArrayList.clear();
                    extractType();
                    Collections.sort(spModifTypeArrayList, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });
                } /*else {
                    edModifType.setText("");
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spModifBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spModifBrand.getSelectedItem().toString();
                if (name != "") {
                    edModifBrand.setText(name);
                    spModifBrandArrayList.clear();
                    extractBrand();
                    Collections.sort(spModifBrandArrayList, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });
                }/* else {
                    edModifBrand.setText("");
                }*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spModifStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spModifStore.getSelectedItem().toString();
                if (name != "") {
                    edModifStore.setText(spModifStore.getSelectedItem().toString());
                    spModifStoreArrayList.clear();
                    extractStore();
                    Collections.sort(spModifStoreArrayList, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });
                } /*else {
                    edModifStore.setText("");
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //=======================================tri================================================
        Collections.sort(spModifStoreArrayList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        Collections.sort(spModifBrandArrayList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        Collections.sort(spModifTypeArrayList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public void modif() {
        txtEdModifProductName = edModifProductName.getText().toString();
        modifPrice = Double.valueOf(edModifPrice.getText().toString());
        modifType = edModifType.getText().toString();
        modifBrand = edModifBrand.getText().toString();
        modifStore = edModifStore.getText().toString();
        txtEdModifDescription = edModifDescription.getText().toString();
        //modifPath;
        //modifDate;
        boolean isUpdated = myDB.updateStudent(idPhoto,
                modifPhotoName,
                txtEdModifProductName,
                modifType,
                modifBrand,
                modifStore,
                modifDate,
                modifPrice,
                txtEdModifDescription,
                modifPath);
        if (isUpdated == true) {
            Toast.makeText(ModifActivity.this, photo.photoProductName + " a été modifié", Toast.LENGTH_SHORT).show();
            boolean end = isFinished();
            if (end == true) {
                toListAcivity();
        }
        finish();

        }else {
            Toast.makeText(ModifActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean isFinished() {
        ListActivity.la.finish();
        RecordActivity.ra.finish();
        return true;
    }

    public void toListAcivity() {
        Intent intent = new Intent(ModifActivity.this, ListActivity.class);
        startActivity(intent);
    }


    public void addModifBrand() {
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
                        Toast.makeText(ModifActivity.this, "Data insterted", Toast.LENGTH_SHORT).show();
                        spModifBrandArrayList.clear();
                        if (spModifBrandArrayList.size() == 0) {
                            extractBrand();
                        }
                    } else {
                        Toast.makeText(ModifActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ModifActivity.this, "champs vide", Toast.LENGTH_SHORT).show();
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

    private void addModifStore() {
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
                        spModifStoreArrayList.clear();
                        if (spModifStoreArrayList.size() == 0) {
                            extractStore();
                        }
                        Toast.makeText(ModifActivity.this, "Data insterted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ModifActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ModifActivity.this, "champs vide", Toast.LENGTH_SHORT).show();
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

    public void addModifType() {
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
                        spModifTypeArrayList.clear();
                        if (spModifTypeArrayList.size() == 0) {
                            extractType();
                        }
                        Toast.makeText(ModifActivity.this, "Data insterted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ModifActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ModifActivity.this, "champs vide", Toast.LENGTH_SHORT).show();
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
            spModifTypeArrayList.add(name);
        }
        myDB.close();
    }

    public void extractStore() {
        myDB.getWritableDatabase();
        Cursor cursor = myDB.spCursorStore();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            spModifStoreArrayList.add(name);
        }
        myDB.close();
    }

    public void extractBrand() {
        myDB.getWritableDatabase();
        Cursor cursor = myDB.spCursorBrand();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            spModifBrandArrayList.add(name);
        }
        myDB.close();
    }

    public void addModif(View view) {
        switch (view.getId()) {
            case R.id.btnModifAddType:
                addModifType();
                break;
            case R.id.btnModifAddStore:
                addModifStore();
                break;
            case R.id.btnModifAddBrand:
                addModifBrand();
                break;
        }
    }
}
