package com.itbeebd.medicare.dataClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Patient implements Parcelable {
    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };
    private int id;
    private String name;
    private String uid;
    private String gender;
    private String dob;
    private double weight;
    private String blood_group;
    private String address;
    private String phone;
    private String token;

    public Patient(String name) {
        this.name = name;
        this.uid = "";
    }

    public Patient(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        this.uid = parcel.readString();
        this.gender = parcel.readString();
        this.dob = parcel.readString();
        this.weight = parcel.readDouble();
        this.blood_group = parcel.readString();
        this.address = parcel.readString();
        this.phone = parcel.readString();
        this.token = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.uid);
        parcel.writeString(this.gender);
        parcel.writeString(this.dob);
        parcel.writeDouble(this.weight);
        parcel.writeString(this.blood_group);
        parcel.writeString(this.address);
        parcel.writeString(this.phone);
        parcel.writeString(this.token);
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public double getWeight() {
        return weight;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getToken() {
        return token;
    }
}
