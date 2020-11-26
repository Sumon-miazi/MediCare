package com.itbeebd.medicare.utils;

import java.io.Serializable;

public class Pharmacy implements Serializable {
    private int pharmacy_id;
    private String name;
    private String image;
    private String uid;
    private String address;
    private String phone;
    private String about;
    private String email;
    private String token;
    private double lat;
    private double lon;

    public Pharmacy(){

    }
    public Pharmacy(int pharmacy_id, String name, String address, String phone, String about, double lat, double lon) {
        this.pharmacy_id = pharmacy_id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.about = about;
        this.lat = lat;
        this.lon = lon;
    }

    public Pharmacy(String name, String address, String about, String phone, String email) {
        this.name = name;
        this.address = address;
        this.about = about;
        this.phone = phone;
        this.email = email;
    }

    public int getPharmacy_id() {
        return pharmacy_id;
    }

    public void setPharmacy_id(int pharmacy_id) {
        this.pharmacy_id = pharmacy_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
