package com.itbeebd.medicare.dataClasses;

public class Doctor {
    private int id;
    private int hospital_id;
    private String name;
    private String dob;
    private String education_history;
    private String address;
    private String phone;

    public Doctor(int id, int hospital_id, String name, String dob, String education_history, String address, String phone) {
        this.id = id;
        this.hospital_id = hospital_id;
        this.name = name;
        this.dob = dob;
        this.education_history = education_history;
        this.address = address;
        this.phone = phone;
    }

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
}
