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
import com.shockdee.compare.models.Brand;
import com.shockdee.compare.models.Type;
import com.shockdee.compare.utilities.Utils;

import java.util.ArrayList;

/**
 * To do : suppression & ajout d'éléments
 */
public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {

    public Context context;
    public ArrayList<Type> typeArrayList;

    public TypeAdapter(Context context, ArrayList<Type> typeArrayList) {
        this.context = context;
        this.typeArrayList = typeArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvItemTypeTab;
        public ImageButton btnItemTypeTab;
        public int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemTypeTab = itemView.findViewById(R.id.tv_item_type_settings);
            btnItemTypeTab = itemView.findViewById(R.id.btn_item_type_settings);
            btnItemTypeTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Type type = typeArrayList.get(position);
                    DatabaseHelper myDB = new DatabaseHelper(context);
                    myDB.getWritableDatabase();
                    boolean isDeleted = Utils.delType(context, type.typeID);
                    if (isDeleted = true){
                        Toast.makeText(context, "la cathégorie "+type.typeName+" a été supprimé avec succès", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        typeArrayList.clear();
                        typeArrayList.addAll(Utils.initTypeFromDB(context));
                    }else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public TypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_type_settings, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TypeAdapter.ViewHolder viewHolder, int i) {
        Type type = typeArrayList.get(i);
        viewHolder.tvItemTypeTab.setText(type.typeName);
        viewHolder.position = i;
    }

    @Override
    public int getItemCount() {
        return typeArrayList.size();
    }
    public void setTypeFilter(ArrayList<Type> newList) {
        typeArrayList = new ArrayList<>();
        typeArrayList.addAll(newList);
        notifyDataSetChanged();
    }

}
