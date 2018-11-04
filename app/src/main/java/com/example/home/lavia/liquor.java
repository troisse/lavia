package com.example.home.lavia;

import java.util.ArrayList;

public  class liquor {
    private String liqName;
    private String liqPrice;
    private String imageUrl;
    private String liqGroup;
//    public data data;


    public liquor() {
        }


    public liquor(String liqGroup, String liqName, String liqPrice, String imageUrl) {
        this.liqName = liqName;
        this.liqPrice = liqPrice;
        this.imageUrl = imageUrl;
        this.liqGroup = liqGroup;
}

    public String getLiqGroup() {
        return liqGroup;
    }

    public void setLiqGroup(String liqGroup) {
        this.liqGroup = liqGroup;
    }

        public String getimageUrl() {
            return imageUrl;
        }

        public void setimageUrl(String imageUrl) {
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
//public void data(){
//        setImageUrl(data.toString());
//        setLiqName(data.toString());
//        setLiqPrice(data.toString());
//    }
    }

