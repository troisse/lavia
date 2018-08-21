package com.example.home.lavia;

import android.content.Context;

import java.util.List;

public class ImageUploadInfo {
    private String imageName;
    private String imagePrice;
    private String imageUrl;
    private String imageGroup;
    private String key;

    public ImageUploadInfo(String imageName, String imagePrice, String imageUrl, String imageGroup,String key) {
        this.imageName = imageName;
        this.imagePrice = imagePrice;
        this.imageUrl = imageUrl;
        this.imageGroup = imageGroup;
        this.key = key;
    }

    public ImageUploadInfo() {
    }

    public void setImageGroup(String imageGroup) {
        this.imageGroup = imageGroup;
    }
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePrice() {
        return imagePrice;
    }

    public void setImagePrice(String imagePrice) {
        this.imagePrice = imagePrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageGroup() {
        return imageGroup;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
