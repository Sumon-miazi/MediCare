package com.itbeebd.medicare.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class Patient extends SugarRecord implements Parcelable {

    private String name;
    private String image;
    private String uid;
    private String gender;
    private String dob;
    private double weight;
    private String blood_group;
    private int is_blood_donor;
    private String address;
    private String phone;
    private String token;
    private int patient_id;

    public Patient() {
        this.name = "Anonymous";
        this.uid = "";
        this.gender = "";
        this.dob = "";
        this.weight = 0.0;
        this.blood_group = "";
        this.is_blood_donor = 0;
        this.address = "";
        this.phone = "";
        this.token = "";
        this.image = null;
    }

    public Patient(int patient_id,
                   String name,
                   String uid,
                   String gender,
                   String dob,
                   double weight,
                   String blood_group,
                   int is_blood_donor,
                   String address,
                   String phone,
                   String token) {
        this.patient_id = patient_id;
        this.name = name;
        this.uid = uid;
        this.gender = gender;
        this.dob = dob;
        this.weight = weight;
        this.blood_group = blood_group;
        this.is_blood_donor = is_blood_donor;
        this.address = address;
        this.phone = phone;
        this.token = token;
    }


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

    public Patient(Parcel parcel) {
        this.patient_id = parcel.readInt();
        this.name = parcel.readString();
        this.uid = parcel.readString();
        this.gender = parcel.readString();
        this.dob = parcel.readString();
        this.weight = parcel.readDouble();
        this.blood_group = parcel.readString();
        this.is_blood_donor = parcel.readInt();
        this.address = parcel.readString();
        this.phone = parcel.readString();
        this.token = parcel.readString();
        this.image = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.patient_id);
        parcel.writeString(this.name);
        parcel.writeString(this.uid);
        parcel.writeString(this.gender);
        parcel.writeString(this.dob);
        parcel.writeDouble(this.weight);
        parcel.writeString(this.blood_group);
        parcel.writeInt(this.is_blood_donor);
        parcel.writeString(this.address);
        parcel.writeString(this.phone);
        parcel.writeString(this.token);
        parcel.writeString(this.image);
    }

    public int getPatientId() {
        return patient_id;
    }

    public void setPatientId(int id) {
        this.patient_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIs_blood_donor() {
        return is_blood_donor;
    }

    public void setIs_blood_donor(int is_blood_donor) {
        this.is_blood_donor = is_blood_donor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
