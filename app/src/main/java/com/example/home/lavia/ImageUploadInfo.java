package com.example.home.lavia;

public class ImageUploadInfo {
    String imageGroup;
    String imageName;
    String imagePrice;
    String imageUrl;

    public ImageUploadInfo(){

    }
    public ImageUploadInfo( String group, String name, String price, String imageURL) {
        if (name.trim().equals("")){
            name = "Enter liquor Brand";
        }
        imageGroup = group;
        imageName = name;
        imagePrice = price;
        imageUrl = imageURL;
    }

    public String getImageGroup() {
        return imageGroup;
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
}
