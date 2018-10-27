package com.example.home.lavia;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView imageUrl;
    public TextView nameLiq, priceLiq;
     ViewDataBinding binding;

    public ViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object data) {
        binding.setVariable(BR.data, data);
        binding.executePendingBindings();
    }

    private void findView(View view){
        imageUrl = view.findViewById(R.id.image_liq);
        nameLiq = view.findViewById(R.id.name_liq);
        priceLiq = view.findViewById(R.id.price_liq);
    }
    public ViewHolder(View view) {
        super(view);
        findView(view);

        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
