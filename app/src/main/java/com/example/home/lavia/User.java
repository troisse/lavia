package com.example.home.lavia;

class User {
    String UserId;
    String Username;
    public User(){

    }

    public User(String userId, String username) {
        UserId = userId;
        Username = username;
    }

    public String getUserId() {
        return UserId;
    }

    public String getUsername() {
        return Username;
    }
}
