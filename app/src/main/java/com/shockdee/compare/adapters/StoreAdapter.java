package com.shockdee.compare.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shockdee.compare.R;
import com.shockdee.compare.database.DatabaseHelper;
import com.shockdee.compare.models.Store;
import com.shockdee.compare.utilities.Utils;

import java.util.ArrayList;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    public Context context;
    public ArrayList<Store> storeArrayList;

    public StoreAdapter(Context context, ArrayList<Store> storeArrayList) {
        this.context = context;
        this.storeArrayList = storeArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_item_store_settings;
        public ImageButton btn_item_store_settings;
        public int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_store_settings = itemView.findViewById(R.id.tv_item_store_settings);
            btn_item_store_settings =itemView.findViewById(R.id.btn_item_store_settings);
            btn_item_store_settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Store store = storeArrayList.get(position);
                    DatabaseHelper myDB = new DatabaseHelper(context);
                    myDB.getWritableDatabase();
                    boolean isDeleted = Utils.delStore(context, store.storeId);
                    if (isDeleted = true){
                        Toast.makeText(context, store.storeName + " a été supprimer avec succès", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        storeArrayList.clear();
                        storeArrayList.addAll(Utils.initStoreFromDB(context));
                    }else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store_settings, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Store store = storeArrayList.get(i);
        viewHolder.tv_item_store_settings.setText(store.storeName);
        viewHolder.position = i;
    }

    @Override
    public int getItemCount() {
        return storeArrayList.size();
    }

    public void setFilterStore(ArrayList<Store> newList){
        storeArrayList = new ArrayList<>();
        storeArrayList.addAll(newList);
        notifyDataSetChanged();
    }

}
