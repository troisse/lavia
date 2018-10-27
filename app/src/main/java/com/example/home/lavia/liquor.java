package com.example.home.lavia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.List;


public  class liquor<data> {
//    public  liquor extends RecyclerView.ViewHolder (){
//    }
public liquor(){

}
    public String liqName, liqPrice ,imageUrl;

    private View mView;
//    private String imageUrl, liqName, liqPrice;

        public static class data{
            String imageUrl, liqName, liqPrice;

            data(String imageUrl, String liqName, String liqPrice) {
                this.liqName = liqName;
                this.liqPrice = liqPrice;
                this.imageUrl = imageUrl;
            }
        }
//        private List<com.example.home.lavia.liquor> liq;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getLiqName() {
            return liqName;
        }

        public void setLiqName(String liqName) {
            this.liqName = liqName;
        }

        public String getLiqPrice() {
            return liqPrice;
        }

        public void setLiqPrice(String liqPrice) {
            this.liqPrice = liqPrice;
        }


//        public liquor(View itemView) {
//            super(itemView);
//            mView = itemView;
//        }



    }

