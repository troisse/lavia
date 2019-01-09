package com.example.home.lavia;

public class sale {
    private String userName;
    private String serviceContact;
    private String buyName;
    private String buyPrice;
    private String amount;
    private String contact;


    public sale(){

    }

    public sale(String userName, String custPhone, String buyName, String buyPrice, String amount, String total) {
        this.userName = userName;
        this.serviceContact = custPhone;
        this.buyName = buyName;
        this.buyPrice = buyPrice;
        this.amount = amount;
        this.contact = total;
    }

    public String getServiceContact() {
        return serviceContact;
    }

    public void setServiceContact(String serviceContact) {
        this.serviceContact = serviceContact;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
