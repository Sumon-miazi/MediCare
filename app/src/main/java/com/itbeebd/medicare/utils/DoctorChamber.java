package com.itbeebd.medicare.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DoctorChamber implements Parcelable {
    private int id;
    private int doctor_id;
    private int hospital_id;
    private String name;
    private String visit_fee;
    private String address;
    private String phone;
    private double lat;
    private double lon;
    private ArrayList<DayOfWeek> dayOfWeekArrayList;

    public DoctorChamber() {

    }

    public DoctorChamber(int id, int doctor_id, int hospital_id, String name, String visit_fee, String address, String phone, double lat, double lon) {
        this.id = id;
        this.doctor_id = doctor_id;
        this.hospital_id = hospital_id;
        this.name = name;
        this.visit_fee = visit_fee;
        this.address = address;
        this.phone = phone;
        this.lat = lat;
        this.lon = lon;
    }


    public static final Creator<DoctorChamber> CREATOR = new Creator<DoctorChamber>() {
        @Override
        public DoctorChamber createFromParcel(Parcel in) {
            return new DoctorChamber(in);
        }

        @Override
        public DoctorChamber[] newArray(int size) {
            return new DoctorChamber[size];
        }
    };

    public DoctorChamber(Parcel parcel) {
        this.id = parcel.readInt();
        this.doctor_id = parcel.readInt();
        this.hospital_id = parcel.readInt();
        this.name = parcel.readString();
        this.visit_fee = parcel.readString();
        this.address = parcel.readString();
        this.phone = parcel.readString();
        this.lat = parcel.readDouble();
        this.lon = parcel.readDouble();
        this.dayOfWeekArrayList = parcel.createTypedArrayList(DayOfWeek.CREATOR);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.doctor_id);
        parcel.writeInt(this.hospital_id);
        parcel.writeString(this.name);
        parcel.writeString(this.visit_fee);
        parcel.writeString(this.address);
        parcel.writeString(this.phone);
        parcel.writeDouble(this.lat);
        parcel.writeDouble(this.lon);
        parcel.writeTypedList(this.dayOfWeekArrayList);
    }

    public int getId() {
        return id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public int getHospital_id() {
        return hospital_id;
    }

    public String getVisit_fee() {
        return visit_fee;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    public ArrayList<DayOfWeek> getDayOfWeekArrayList() {
        return dayOfWeekArrayList;
    }

    public void setDayOfWeekArrayList(ArrayList<DayOfWeek> dayOfWeekArrayList) {
        this.dayOfWeekArrayList = dayOfWeekArrayList;
    }
}