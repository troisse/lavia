package com.example.home.lavia;

public class userPurchase {
    private String buyName;
    private String buyPrice;
    private String amount;
    private String contact;
    private String userName;


    userPurchase() {
    }

    public userPurchase(String userName,String contact,String buyName, String buyPrice, String amount) {
        this.buyName = buyName;
        this.buyPrice = buyPrice;
        this.amount = amount;
        this.contact = contact;
        this.userName = userName;
    }

    public String getContact() {
        return contact;
    }

    void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserName() {
        return userName;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBuyName() {
        return buyName;
    }

    public void setBuyName(String buyName) {
        this.buyName = buyName;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}