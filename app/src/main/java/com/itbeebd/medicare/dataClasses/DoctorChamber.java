package com.itbeebd.medicare.dataClasses;

import java.util.ArrayList;

public class DoctorChamber {
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
