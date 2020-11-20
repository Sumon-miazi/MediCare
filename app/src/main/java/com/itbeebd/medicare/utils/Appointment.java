package com.itbeebd.medicare.utils;

import java.time.LocalDate;

public class Appointment {

    private int patient_id;
    private int doctor_id;
    private int doctor_chamber_id;
    private int hospital_id;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int status;

    // these will hold the doctor info
    private String name;
    private String hospitalName;
    private String image;
    private String degree;
    private String address;

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getDoctor_chamber_id() {
        return doctor_chamber_id;
    }

    public void setDoctor_chamber_id(int doctor_chamber_id) {
        this.doctor_chamber_id = doctor_chamber_id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDateTime(LocalDate localDate, String time){
        this.year = localDate.getYear();
        this.month = localDate.getMonthValue();
        this.day = localDate.getDayOfMonth();

        String[] t = time.split(":");
        this.hour = Integer.parseInt(t[0]);
        this.minute = Integer.parseInt(t[1]);
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

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
