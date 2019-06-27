package com.example.carpoolingappv1;

public class User {

    public boolean isConductor;
    public String fullName;
    public String email;
    public String bDate;
    public Integer phone;
    public String wilaya;

    public String profilePic;

    //conductor
    public String carModel;
    public String carKey;

    public boolean isConductor() {
        return isConductor;
    }

    public void setConductor(boolean conductor) {
        isConductor = conductor;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getWilaya() {
        return wilaya;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarKey() {
        return carKey;
    }

    public void setCarKey(String carKey) {
        this.carKey = carKey;
    }

    public User() {

    }


    public User(boolean isConductor, String fullName, String email, String bDate, Integer phone, String wilaya,String profilePic) {
        this.isConductor = isConductor;
        this.fullName = fullName;
        this.email = email;
        this.bDate = bDate;
        this.phone = phone;
        this.wilaya = wilaya;
        this.profilePic = profilePic;
    }

    public User(boolean isConductor, String fullName, String email, String bDate, Integer phone, String wilaya, String carModel, String carKey,String profilePic) {
        this.isConductor = isConductor;
        this.fullName = fullName;
        this.email = email;
        this.bDate = bDate;
        this.phone = phone;
        this.wilaya = wilaya;
        this.carModel = carModel;
        this.carKey = carKey;
        this.profilePic = profilePic;
    }
}