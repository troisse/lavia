package com.example.home.lavia;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Custom2Adapter extends BaseAdapter {
    Context mContext;
    ArrayList<ImageUploadInfo> data;//modify here

    public Custom2Adapter(Context mContext, ArrayList<ImageUploadInfo> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();// # of items in your arraylist
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);// get the actual item
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.liq_list, parent,false);//modify here
            viewHolder = new ViewHolder();
            //modify this block of code
            viewHolder.imageName= (TextView) convertView.findViewById(R.id.name_liq);
            viewHolder.imagePrice= (TextView) convertView.findViewById(R.id.price_liq);
//            viewHolder.imageUrl= (ImageView) convertView.findViewById(R.id.image_liq);

            //Up to here
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //MODIFY THIS BLOCK OF CODE
        final ImageUploadInfo person = data.get(position);//modify here
        viewHolder.imageName.setText(person.getImageName());//modify here
        viewHolder.imagePrice.setText(person.getImagePrice());//modify here
//        Picasso.get().load(person.getImageUrl()).fit().centerCrop().into(viewHolder.imageUrl);
        return convertView;
    }






    static class ViewHolder {
        TextView imageName;
        TextView imagePrice;
//        ImageView imageUrl;

    }

}
