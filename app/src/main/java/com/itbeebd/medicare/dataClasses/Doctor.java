package com.itbeebd.medicare.dataClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Doctor implements Parcelable {
    private int id;
    private int hospital_id;
    private String name;
    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };
    private String dob;
    private String education_history;
    private String address;
    private String phone;
    private String about;

    public int getId() {
        return id;
    }

    public int getHospital_id() {
        return hospital_id;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getEducation_history() {
        return education_history;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Doctor(int id, int hospital_id, String name, String dob, String education_history, String address, String phone) {
        this.id = id;
        this.hospital_id = hospital_id;
        this.name = name;
        this.dob = dob;
        this.education_history = education_history;
        this.address = address;
        this.phone = phone;
        this.about = "something";
    }


    public Doctor(Parcel parcel) {
        this.id = parcel.readInt();
        this.hospital_id = parcel.readInt();
        this.name = parcel.readString();
        this.about = parcel.readString();
        this.dob = parcel.readString();
        this.education_history = parcel.readString();
        this.address = parcel.readString();
        this.phone = parcel.readString();
    }

    public String getAbout() {
        return about;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.hospital_id);
        parcel.writeString(this.name);
        parcel.writeString(this.about);
        parcel.writeString(this.dob);
        parcel.writeString(this.education_history);
        parcel.writeString(this.address);
        parcel.writeString(this.phone);
    }
}
