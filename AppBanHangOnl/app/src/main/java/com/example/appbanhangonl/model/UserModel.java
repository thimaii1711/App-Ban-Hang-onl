package com.example.appbanhangonl.model;

public class UserModel {
    int id;
    String email;
    String pass;
    String username;
    String mobile;
    String token;
    int status;
    String ImageUser;

    public UserModel() {

    }

    public UserModel(String email, String username, String mobile, String str_hinhanh) {
        this.email = email;
        this.username = username;
        this.mobile = mobile;
        ImageUser = str_hinhanh;
    }

    public String getImageUser() {
        return ImageUser;
    }

    public void setImageUser(String imageUser) {
        this.ImageUser = imageUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
