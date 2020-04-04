package com.shockdee.compare.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.shockdee.compare.R;
import com.shockdee.compare.database.DatabaseHelper;
import com.shockdee.compare.models.Brand;
import com.shockdee.compare.models.Store;
import com.shockdee.compare.models.Type;
import com.shockdee.compare.utilities.TabBrand;
import com.shockdee.compare.utilities.TabStore;
import com.shockdee.compare.utilities.TabType;
import com.shockdee.compare.utilities.Utils;

/**
 *
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    DatabaseHelper myDB;
    public static Activity settingsActivity;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        myDB = new DatabaseHelper(this);
        settingsActivity = this;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * addBrandTab pour le nom de la méthode onclick & addTabBrand pour la méthode d'ajout
     */

    public void addItemTab(View view) {

        switch (view.getId()) {
            case R.id.btnAddTypeTab:
                addTabType();
                break;
            case R.id.btnAddBrandTab:
                addTabBrand();
                break;
            case R.id.btnAddStoreTab:
                addStoreType();
                break;
        }

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    TabBrand tabBrand = new TabBrand();
                    return tabBrand;
                case 1:
                    TabType tabType = new TabType();
                    return tabType;
                case 2:
                    TabStore tabStore = new TabStore();
                    return tabStore;
                default:
                    return null;

            }
            //return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    //**********************************************************************************************
    public void addTabBrand() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Ajout d'une nouvelle marque");
        View view = LayoutInflater.from(this).inflate(R.layout.add_dialog, null);
        final EditText edBrand = view.findViewById(R.id.edAddDialog);
        builder.setView(view);
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String brandTxt = edBrand.getText().toString();
                Brand brand = new Brand(brandTxt);
                if (brandTxt != null && brandTxt.length() > 0) {
                    boolean isInserted = myDB.insertBrand(brand);
                    if (isInserted == true) {
                        Toast.makeText(SettingsActivity.this, "La marque : " + brandTxt + "a été ajouté", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SettingsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SettingsActivity.this, "champs vide", Toast.LENGTH_SHORT).show();
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

    public void addTabType() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Ajout d'une nouvelle cathégorie");
        View view = LayoutInflater.from(this).inflate(R.layout.add_dialog, null);
        final EditText edType = view.findViewById(R.id.edAddDialog);
        builder.setView(view);
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String typeTxt = edType.getText().toString();
                Type type = new Type(typeTxt);
                if (typeTxt != null && typeTxt.length() > 0) {
                    boolean isInserted = myDB.insertType(type);
                    if (isInserted == true) {
                        Toast.makeText(SettingsActivity.this, "La cathégorie : " + typeTxt + "a été ajoutée", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(SettingsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SettingsActivity.this, "champs vide : Aucune nouvelle entrée a été ajoutée", Toast.LENGTH_SHORT).show();
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

    public void addStoreType() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Ajout d'une nouvelle enseigne");
        View view = LayoutInflater.from(this).inflate(R.layout.add_dialog, null);
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
                        Toast.makeText(SettingsActivity.this, "L'enseigne : " + storeTxt + "a été ajoutée", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(SettingsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SettingsActivity.this, "champs vide : Aucune nouvelle entrée a été ajoutée", Toast.LENGTH_SHORT).show();
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
}
