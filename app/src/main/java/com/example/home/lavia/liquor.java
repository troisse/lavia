package com.example.home.lavia;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class liquor {
    TextView nameLiq, priceLiq;
    ImageView imageLiq;
    public  liquor(View itemView){
        nameLiq = (TextView)itemView.findViewById(R.id.name_liq);
        priceLiq = (TextView) itemView.findViewById(R.id.price_liq);
        imageLiq = (ImageView) itemView.findViewById(R.id.image_liq);
    }
}
