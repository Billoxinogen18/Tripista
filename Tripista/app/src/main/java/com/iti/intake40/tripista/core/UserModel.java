package com.iti.intake40.tripista.core;

public class UserModel {
    String id;
    String name;
    String phone;
    String passWord;
    String imageUrl;

    public UserModel() {

    }
    public UserModel(String id, String name, String phone, String passWord, String imageUrl) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.passWord = passWord;
        this.imageUrl = imageUrl;
    }

    public UserModel(String id, String name, String phone, String passWord) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.passWord = passWord;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
