package com.itbeebd.medicare.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class BloodBank implements Parcelable {

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
    private int id;
    private String name;
    private String address;
    private String phone;
    private String about;

    public BloodBank(String name) {
        this.id = 0;
        this.name = name;
        this.address = "Cumilla, Shovupur";
        this.phone = "+8801311205352";
        this.about = "I am nothing but a server of allah";
    }


    public BloodBank(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        this.address = parcel.readString();
        this.phone = parcel.readString();
        this.about = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.address);
        parcel.writeString(this.phone);
        parcel.writeString(this.about);
    }

}
