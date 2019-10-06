package com.shockdee.compare.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shockdee.compare.R;
import com.shockdee.compare.activities.RecordActivity;
import com.shockdee.compare.activities.ScrollingPhotoActivity;
import com.shockdee.compare.models.Photo;
import com.shockdee.compare.utilities.Utils;

import java.io.File;
import java.util.ArrayList;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    Context context;
    ArrayList<Photo> photoArrayList;

    public PhotoAdapter(Context context, ArrayList<Photo> photoArrayList) {
        this.context = context;
        this.photoArrayList = photoArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView itemTitle;
        public TextView itemPrice;
        public TextView itemDescription;
        public ImageView itemPhoto;
        public ImageButton itemBtnDel;

        public int position;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);

            itemPhoto = itemView.findViewById(R.id.item_photo);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemPrice = itemView.findViewById(R.id.item_price);
            itemBtnDel = itemView.findViewById(R.id.item_btn_del);
            itemDescription = itemView.findViewById(R.id.item_description);
            itemBtnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Photo photo = photoArrayList.get(position);
                    Utils.delPhoto(context, photo.photoId);
                    String file = photo.photoPath;
                    File fdel = new File(file);
                    if(fdel.exists()){
                        fdel.delete();
                        photoArrayList.remove(position);
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file))));
                    }
                    notifyDataSetChanged();
                    notifyItemRemoved(position);
                    notifyItemChanged(position, photoArrayList.size());
                }
            });

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Photo photo = photoArrayList.get(i);
        myViewHolder.itemPrice.setText(String.valueOf(photo.photoPrice)+" €");
        myViewHolder.itemTitle.setText(photo.photoProductName);
        myViewHolder.itemDescription.setText(photo.photoDescription);
        Glide.with(context)
                .load(photo.photoPath)
                .thumbnail((float) 0.01)
                .into(myViewHolder.itemPhoto);
        myViewHolder.position = i;
    }

    @Override
    public int getItemCount() {
        return photoArrayList.size();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MyViewHolder viewHolder = (MyViewHolder) v.getTag();
            final int position = viewHolder.position;
            Intent intent = new Intent(context, RecordActivity.class);
            intent.putExtra("photoId", String.valueOf(photoArrayList.get(position).photoId));
            intent.putExtra("toString", photoArrayList.get(position).toString());
            intent.putExtra("path", photoArrayList.get(position).photoPath);
            context.startActivity(intent);
        }
    };

    public void setFilter(ArrayList<Photo> newList){
        photoArrayList = new ArrayList<>();
        photoArrayList.addAll(newList);
        notifyDataSetChanged();
    }


}
