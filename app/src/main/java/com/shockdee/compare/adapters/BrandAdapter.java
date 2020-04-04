package com.shockdee.compare.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shockdee.compare.R;
import com.shockdee.compare.database.DatabaseHelper;
import com.shockdee.compare.models.Brand;
import com.shockdee.compare.utilities.Utils;

import java.util.ArrayList;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {

    public Context context;
    public ArrayList<Brand> brandArrayList;

    public BrandAdapter(Context context, ArrayList<Brand> brandArrayList) {
        this.context = context;
        this.brandArrayList = brandArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTvBrandSettings;
        public ImageButton itemBrandBtnDel;
        public int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTvBrandSettings = itemView.findViewById(R.id.tv_item_brand_settings);
            itemBrandBtnDel = itemView.findViewById(R.id.btn_item_brand_settings);
            itemBrandBtnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Brand brand = brandArrayList.get(position);
                    DatabaseHelper myDB = new DatabaseHelper(context);
                    myDB.getWritableDatabase();

                    boolean isDeleted = Utils.delBrand(context, brand.brandID);
                    if (isDeleted = true) {
                        Toast.makeText(context, brand.brandName + " a été supprimer avec succès", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        brandArrayList.clear();
                        brandArrayList.addAll(Utils.initBrandFromDB(context));
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_brand_settings, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Brand brand = brandArrayList.get(position);
        viewHolder.itemTvBrandSettings.setText(brand.brandName);
        viewHolder.position = position;
    }

    @Override
    public int getItemCount() {
        return brandArrayList.size();
    }

    public void setBrandFilter(ArrayList<Brand> newList) {
        brandArrayList = new ArrayList<>();
        brandArrayList.addAll(newList);
        notifyDataSetChanged();
    }

}
