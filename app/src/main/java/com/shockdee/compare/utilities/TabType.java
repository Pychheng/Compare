package com.shockdee.compare.utilities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shockdee.compare.R;
import com.shockdee.compare.adapters.TypeAdapter;
import com.shockdee.compare.models.Type;

import java.util.ArrayList;

public class TabType extends Fragment {

    public static ArrayList<Type> typeArrayList;
    public SearchView sv_tab_type;
    public RecyclerView rv_tab_type;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_type_settings, container, false);
        rv_tab_type = rootView.findViewById(R.id.rv_tab_type_settings);
        typeArrayList = Utils.initTypeFromDB(getActivity());
        rv_tab_type.setLayoutManager(new LinearLayoutManager(getActivity()));
        final TypeAdapter typeAdapter = new TypeAdapter(getActivity(), typeArrayList);
        rv_tab_type.setAdapter(typeAdapter);

        sv_tab_type = rootView.findViewById(R.id.sv_tab_type_settings);
        sv_tab_type.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {
                searchText = searchText.toLowerCase();
                ArrayList<Type> newList = new ArrayList<>();
                for (Type type : typeArrayList){
                    String nom = type.typeName.toLowerCase();
                    if (nom.contains(searchText)){
                        newList.add(type);
                    }
                }
                typeAdapter.setTypeFilter(newList);
                return false;
            }
        });
        return rootView;
    }

}
