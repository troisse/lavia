package com.lavia;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class customAdapter extends RecyclerView.Adapter<customAdapter.ViewHolder> {

    private Context c;
    ArrayList<liquor> purchase;
    private final OnRecyclerViewItemClickedListener listener;
    ProgressDialog progressDialog;

    customAdapter(ProgressDialog progressDialog,Context c, ArrayList<liquor>purchase, OnRecyclerViewItemClickedListener listener) {
        this.c = c;
        this.progressDialog=progressDialog;
        this.purchase= purchase;
        this.listener = listener;
    }

    public interface OnRecyclerViewItemClickedListener {
        void OnItemClick(liquor item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(c).inflate(R.layout.liq_list,parent,false);

        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final liquor old = purchase.get(position);

        holder.nameLiq.setText(old.getliqName());
        holder.priceLiq.setText("Ksh. "+old.getliqPrice());
        picassoClient.downloading(holder.cardView.getContext(),old.getimageUrl(),holder.imageLiq);
        progressDialog.dismiss();

        holder.bind(old, listener);

    }

    @Override
    public int getItemCount() {

        int arr = 0;

        try{
        if(purchase.size()==0){
            firebaseClient.progressDialog.cancel();
            Toast.makeText(c,"Service Unavailable",Toast.LENGTH_SHORT).show();
        }
        else{
            arr=purchase.size();
        }



    }catch (Exception e){
        Toast.makeText(c,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        return arr;
}


    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageLiq;
        TextView nameLiq;
        TextView priceLiq;

        ViewHolder(final View itemView) {
            super(itemView);

            nameLiq = itemView.findViewById(R.id.name_liq);
            priceLiq = itemView.findViewById(R.id.price_liq);
            imageLiq = itemView.findViewById(R.id.image_liq);
            cardView = itemView.findViewById(R.id.cardView);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                   @Override
//                public void onClick(View v) {
//                    if(listener != null){
//                        int position =getAdapterPosition();
//                        if (position!=RecyclerView.NO_POSITION){
////                            customAdapter.setOnRecyclerViewClickedListener((OnRecyclerViewItemClickedListener) c.getApplicationContext());
//                            listener.onItemClick(itemView).OnRecyclerViewItemClicked(position);
//                            cardView.setCardBackgroundColor(Color.RED);
//                        }
//                    }
//                }
//            });
}

         void bind(final liquor item, final OnRecyclerViewItemClickedListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.OnItemClick(item);
                }
            });
        }
    }


}

