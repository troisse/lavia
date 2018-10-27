package com.example.home.lavia;

public class UploadInfo<data> {
    private String liqName;
    private String liqPrice;
    private String imageUrl;
    private String liqGroup;
    private String selectedType;

    public UploadInfo(String liqName, String liqPrice, String imageUrl, String liqGroup, String selectedType) {
        this.liqName = liqName;
        this.liqPrice = liqPrice;
        this.imageUrl = imageUrl;
        this.liqGroup = liqGroup;
this.selectedType = selectedType;

    }

    public UploadInfo() {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLiqGroup() {
        return liqGroup;
    }
    public void setLiqGroup(String liqGroup){
        this.liqGroup = liqGroup;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }
}
