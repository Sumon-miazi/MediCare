package com.itbeebd.medicare.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class BloodBank extends SugarRecord implements Parcelable {

    private int id;
    private String name;
    private String uid;
    private String address;
    private String phone;
    private String about;
    private String email;
    private String token;
    private double lat;
    private double lon;

    public BloodBank(){

    }

    public BloodBank(String name, String address, String phone, String about, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.about = about;
        this.email = email;
        this.lat = 0.0;
        this.lon = 0.0;
    }

    public BloodBank(int id, String name, String address, String phone, String about, double lat, double lon) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.about = about;
        this.lat = lat;
        this.lon = lon;
    }

    public static final Creator<BloodBank> CREATOR = new Creator<BloodBank>() {
        @Override
        public BloodBank createFromParcel(Parcel in) {
            return new BloodBank(in);
        }

        @Override
        public BloodBank[] newArray(int size) {
            return new BloodBank[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public BloodBank(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        this.address = parcel.readString();
        this.phone = parcel.readString();
        this.about = parcel.readString();
        this.lat = parcel.readDouble();
        this.lon = parcel.readDouble();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.address);
        parcel.writeString(this.phone);
        parcel.writeString(this.about);
        parcel.writeDouble(this.lat);
        parcel.writeDouble(this.lon);
    }

    public int getBloodBankId() {
        return id;
    }

    public void setBloodBankId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
