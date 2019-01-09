package com.example.home.lavia;

import java.util.ArrayList;
import java.util.Map;

public  class liquor extends  UploadInfo{
//    protected String[] data;

    private String store;
    private String liqName;
    private String liqPrice;
    private String imageUrl;
    private String time;
    private String liqGroup;

    public liquor() {
        }

    //public setters and getters for the fields

    public liquor(String store, String liqName, String liqPrice, String imageUrl, String time, String liqGroup) {
        this.liqName = liqName;
        this.liqPrice = liqPrice;
        this.imageUrl = imageUrl;
        this.store = store;
        this.time=time;
        this.liqGroup=liqGroup;
    }

    public String getLiqGroup() {
        return liqGroup;
    }

    public void setLiqGroup(String liqGroup){
        this.liqGroup = liqGroup;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

        public String getimageUrl() {
            return imageUrl;
        }

        public void setimageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getliqName() {
            return liqName;
        }

        public void setliqName(String liqName) {
            this.liqName = liqName;
        }

        public String getliqPrice() {
            return liqPrice;
        }

        public void setliqPrice(String liqPrice) {
            this.liqPrice = liqPrice;
        }

    }

