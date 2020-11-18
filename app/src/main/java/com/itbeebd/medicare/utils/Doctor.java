package com.itbeebd.medicare.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class Doctor extends SugarRecord implements Parcelable {
    private int doctor_id;
    private String uid;
    private int hospital_id;
    private String name;
    private String image;
    private String gender;
    private String dob;
    private String bmdcRegNo;
    private String specialist;
    private String educationHistory;
    private String address;
    private String email;
    private String phone;
    private String about;
    private String token;

    public Doctor(){

    }
    public Doctor(int id, int hospital_id, String name, String dob, String education_history, String address, String phone) {
        this.doctor_id = id;
        this.hospital_id = hospital_id;
        this.name = name;
        this.dob = dob;
        this.educationHistory = education_history;
        this.address = address;
        this.phone = phone;
        this.about = "It is One of the best specialist Hospitals here in Dhaka,having virtually all facilities -consultation\n" +
                "With Professors,treatment in emergency situation by duty doctors 24/7";
    }

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

    public Doctor(Parcel parcel) {
        this.doctor_id = parcel.readInt();
        this.hospital_id = parcel.readInt();
        this.name = parcel.readString();
        this.about = parcel.readString();
        this.dob = parcel.readString();
        this.educationHistory = parcel.readString();
        this.address = parcel.readString();
        this.phone = parcel.readString();
        this.image = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.doctor_id);
        parcel.writeInt(this.hospital_id);
        parcel.writeString(this.name);
        parcel.writeString(this.about);
        parcel.writeString(this.dob);
        parcel.writeString(this.educationHistory);
        parcel.writeString(this.address);
        parcel.writeString(this.phone);
        parcel.writeString(this.image);
    }


    public int getDoctorId() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBmdcRegNo() {
        return bmdcRegNo;
    }

    public void setBmdcRegNo(String bmdcRegNo) {
        this.bmdcRegNo = bmdcRegNo;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getEducationHistory() {
        return educationHistory;
    }

    public void setEducationHistory(String educationHistory) {
        this.educationHistory = educationHistory;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
