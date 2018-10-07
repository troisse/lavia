package com.example.home.lavia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class customAdapter extends BaseAdapter{
    Context c;
    ArrayList<ImageUploadInfo> data;//modify here
    LayoutInflater inflater;

    public customAdapter(Context c, ArrayList<ImageUploadInfo> data) {
        this.c = c;
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
     if (inflater == null){
         inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     }if (convertView==null){
         convertView= inflater.inflate(R.layout.liq_list,parent,false);
        }
        liquor holder =new liquor(convertView);
        holder.nameLiq.setText(data.get(position).getImageName());
        holder.priceLiq.setText(data.get(position).getImagePrice());
        picassoClient.downloading(c,data.get(position).getImageUrl(),holder.imageLiq);
    return convertView;
    }

}
