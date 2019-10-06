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
import com.shockdee.compare.adapters.StoreAdapter;
import com.shockdee.compare.models.Store;

import java.util.ArrayList;

public class TabStore extends Fragment {

    public RecyclerView rv_tab_store;
    public ArrayList<Store> storeArrayList;
    public SearchView sv_tab_store;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_store_settings, container, false);
        rv_tab_store = rootView.findViewById(R.id.rv_tab_store_settings);
        storeArrayList = Utils.initStoreFromDB(getActivity());
        final StoreAdapter storeAdapter = new StoreAdapter(getActivity(), storeArrayList);
        rv_tab_store.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_tab_store.setAdapter(storeAdapter);

        sv_tab_store = rootView.findViewById(R.id.sv_tab_store_settings);
        sv_tab_store.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String seachTxt) {
                seachTxt = seachTxt.toLowerCase();
                ArrayList<Store> newList = new ArrayList<>();
                for (Store store : storeArrayList){
                    String nom = store.storeName.toLowerCase();
                    if (nom.contains(seachTxt)){
                        newList.add(store);
                    }
                }
                storeAdapter.setFilterStore(newList);
                return false;
            }
        });
        return rootView;
    }
}
