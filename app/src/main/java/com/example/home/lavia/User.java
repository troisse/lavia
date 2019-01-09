package com.example.home.lavia;

public class User {
    private String imageName;
    private String imagePrice;
    private String UserId;
    private String Username;
    private String Contact;
    private String Purchase;

    public User(){

    }

    public User(String imageName,String imagePrice, String userId, String username, String contact, String purchase) {
        this.UserId = userId;
        this.Username = username;
        this.Contact = contact;
        this.Purchase = purchase;
        this.imageName = imageName;
        this.imagePrice = imagePrice;
    }

    public String getUserId(String id) {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getPurchase() {
        return Purchase;
    }

    public void setPurchase(String purchase) {
        Purchase = purchase;
    }
}
