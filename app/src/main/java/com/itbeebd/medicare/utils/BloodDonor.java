package com.itbeebd.medicare.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class BloodDonor implements Parcelable {


    private int id;
    private int userId;
    private String name;
    private String image;
    private String lastDonateDate;
    private String totalBloodDonation;
    private String bloodGroup;
    private String address;
    private String phone;
    private String about;
    private String Token;

    public BloodDonor(int id, int userId, String name, String lastDonateDate,  String bloodGroup, String address, String phone, String token) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.lastDonateDate = lastDonateDate;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.phone = phone;
        this.Token = token;
    }

    public BloodDonor(String name, String bloodGroup, String address) {
        this.id = 0;
        this.name = name;
        this.lastDonateDate = "21 May, 2020";
        this.totalBloodDonation = "9";
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.phone = "+8801311205352";
        this.about = "I am nothing but a server of allah";
    }

    public static final Creator<BloodDonor> CREATOR = new Creator<BloodDonor>() {
        @Override
        public BloodDonor createFromParcel(Parcel in) {
            return new BloodDonor(in);
        }

        @Override
        public BloodDonor[] newArray(int size) {
            return new BloodDonor[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    public BloodDonor(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        this.image = parcel.readString();
        this.lastDonateDate = parcel.readString();
        this.totalBloodDonation = parcel.readString();
        this.bloodGroup = parcel.readString();
        this.address = parcel.readString();
        this.phone = parcel.readString();
        this.about = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.image);
        parcel.writeString(this.lastDonateDate);
        parcel.writeString(this.totalBloodDonation);
        parcel.writeString(this.bloodGroup);
        parcel.writeString(this.address);
        parcel.writeString(this.phone);
        parcel.writeString(this.about);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastDonateDate() {
        return lastDonateDate;
    }

    public String getTotalBloodDonation() {
        return totalBloodDonation;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getAbout() {
        return about;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
