package com.example.home.lavia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public abstract class customAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context c;
    ArrayList<liquor> data;

    public customAdapter(Context c, ArrayList<liquor> data) {
        this.c = c;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.liq_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameLiq.setText(data.get(position).getLiqName());
        holder.priceLiq.setText(data.get(position).getLiqPrice());

        picassoClient.downloading(c,data.get(position).getimageUrl(),holder.imageLiq);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }}
