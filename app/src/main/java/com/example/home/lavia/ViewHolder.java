package com.example.home.lavia;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView imageLiq;
    TextView nameLiq, priceLiq;

    private void findView(View view){
        imageLiq = view.findViewById(R.id.image_liq);
        nameLiq = view.findViewById(R.id.name_liq);
        priceLiq = view.findViewById(R.id.price_liq);
    }
    public ViewHolder(View view) {
        super(view);
        findView(view);

//        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
