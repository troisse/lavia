package com.example.home.lavia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class liquor extends RecyclerView.Adapter<liquor.ImageViewHolder> {
    private Context mContext;
    private List<liquor> mUploads;

    private String imageName;
    private String imagePrice;
    private String imageUrl;
    public liquor(Context context, List<liquor> uploads) {
        this.mContext = context;
        this.mUploads = uploads;
    }

    public liquor() {
    }
    public liquor(  String name, String price, String imageURL) {
        if (name.trim().equals("")){
            name = "Enter liquor Brand";
        }

        imageName = name;
        imagePrice = price;
        imageUrl = imageURL;
    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePrice() {
        return imagePrice;
    }

    public void setImagePrice(String imagePrice) {
        this.imagePrice = imagePrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView Name;
        public TextView Price;
        public ImageView liq_image;

        public ImageViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name_liq);
            Price = itemView.findViewById(R.id.price_liq);
            liq_image = itemView.findViewById(R.id.image_liq);
        }
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.liq_list, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        liquor uploadCurrent = mUploads.get(position);
        holder.Name.setText(uploadCurrent.getImageName());
        holder.Price.setText(uploadCurrent.getImagePrice());
        Picasso.get().load(uploadCurrent.getImageUrl())
        .fit()
        .centerCrop()
        .into(holder.liq_image);


    }

    @Override
    public int getItemCount() {
        return mUploads.size();

    }


}
