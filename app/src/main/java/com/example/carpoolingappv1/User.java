package com.example.carpoolingappv1;

public class User {

    public boolean isConductor;
    public String fullName;
    public String email;
    public String bDate;
    public Integer phone;
    public String wilaya;

    //conductor
    public String carModel;
    public String CarKey;




    public User() {

    }


    public User(boolean isConductor, String fullName, String email, String bDate, Integer phone, String wilaya) {
        this.isConductor = isConductor;
        this.fullName = fullName;
        this.email = email;
        this.bDate = bDate;
        this.phone = phone;
        this.wilaya = wilaya;
    }

    public User(boolean isConductor, String fullName, String email, String bDate, Integer phone, String wilaya, String carModel, String carKey) {
        this.isConductor = isConductor;
        this.fullName = fullName;
        this.email = email;
        this.bDate = bDate;
        this.phone = phone;
        this.wilaya = wilaya;
        this.carModel = carModel;
        this.CarKey = carKey;
    }
}