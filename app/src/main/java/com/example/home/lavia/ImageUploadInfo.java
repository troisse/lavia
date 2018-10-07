package com.example.home.lavia;

public class ImageUploadInfo<data> {
    private String imageName;
    private String imagePrice;
    private String imageUrl;
    private String imageGroup;
    private String selectedType;

    public ImageUploadInfo(String imageName, String imagePrice, String imageUrl, String imageGroup,String selectedType) {
        this.imageName = imageName;
        this.imagePrice = imagePrice;
        this.imageUrl = imageUrl;
        this.imageGroup = imageGroup;
this.selectedType = selectedType;
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

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }
}
