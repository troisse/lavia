package com.lavia;

public class UploadInfo{
    private String liqName;
    private String liqPrice;
    private String imageUrl;
    private String liqGroup;
    private String time;


    public UploadInfo(String liqName, String liqPrice, String imageUrl, String liqGroup,String time) {
        this.liqName = liqName;
        this.liqPrice = liqPrice;
        this.imageUrl = imageUrl;
        this.liqGroup = liqGroup;
        this.time = time;
    }

    public UploadInfo() {
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

}
