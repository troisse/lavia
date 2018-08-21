package com.example.home.lavia;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 1. where WE INFLATE OUR MODEL LAYOUT INTO VIEW ITEM
 * 2. THEN BIND DATA
 */
public class CustomAdapter extends BaseAdapter{

    private Context c;
    ArrayList<ImageUploadInfo> liqs;

    public CustomAdapter(Context c, ArrayList<ImageUploadInfo> liqs) {

        this.c = c;
        this.liqs = liqs;
    }




    @Override
    public int getCount() {
        return liqs.size();
    }

    @Override
    public Object getItem(int position) {
        return liqs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView=LayoutInflater.from(c).inflate(R.layout.liq_list, parent, false);
        }

        TextView imageName= (TextView) convertView.findViewById(R.id.name_liq);
        TextView imagePrice= (TextView) convertView.findViewById(R.id.price_liq);
        ImageView imageUrl= (ImageView) convertView.findViewById(R.id.image_liq);

        final ImageUploadInfo s= (ImageUploadInfo) this.getItem(position);

        imageName.setText(s.getImageName());
        imagePrice.setText(s.getImagePrice());
        Picasso.get().load(s.getImageUrl())
                .fit()
                .centerCrop()
                .into(imageUrl);


        return convertView;
    }
}
