package com.shockdee.compare.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.shockdee.compare.R;
import com.shockdee.compare.activities.SettingsActivity;
import com.shockdee.compare.adapters.BrandAdapter;
import com.shockdee.compare.database.DatabaseHelper;
import com.shockdee.compare.models.Brand;

import java.util.ArrayList;
import java.util.EventListener;

public class TabBrand extends Fragment {

    public static ArrayList<Brand> brandArrayList;
    public android.support.v7.widget.SearchView svItemBrandSettings;
    public static RecyclerView rvItemBrandSettings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_brand_settings, container, false);
        rvItemBrandSettings = rootView.findViewById(R.id.rv_tab_brand_settings);
        brandArrayList = Utils.initBrandFromDB(getActivity());
        rvItemBrandSettings.setLayoutManager(new LinearLayoutManager(getActivity()));
        final BrandAdapter brandAdapter = new BrandAdapter(getActivity(), brandArrayList);
        rvItemBrandSettings.setAdapter(brandAdapter);

        svItemBrandSettings = rootView.findViewById(R.id.sv_tab_brand_settings);
        svItemBrandSettings.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }


            @Override
            public boolean onQueryTextChange(String searchText) {
                searchText = searchText.toLowerCase();
                ArrayList<Brand> newList = new ArrayList<>();
                for (Brand brand : brandArrayList){
                    String nom = brand.brandName.toLowerCase();
                    if (nom.contains(searchText)){
                        newList.add(brand);
                    }
                }
                brandAdapter.setBrandFilter(newList);
                return false;
            }
        });
        return rootView;
    }

}
