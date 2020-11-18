package com.itbeebd.medicare.utils;

import java.util.Date;

public class BloodRequest {

    private int userId;
    private int recordId;
    private String bloodFor;
    private String city;
    private String hospital;
    private String amount;
    private Date bloodNeededDateTime;
    private String bloodGroup;
    private String bloodNeededDateTimeString;

    private String name;
    private String phone;

    public BloodRequest(){

    }

    public BloodRequest(int userId, String bloodFor, String city, String hospital, String amount, String bloodGroup) {
        this.userId = userId;
        this.bloodFor = bloodFor;
        this.city = city;
        this.hospital = hospital;
        this.amount = amount;
        this.bloodGroup = bloodGroup;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getBloodFor() {
        return bloodFor;
    }

    public void setBloodFor(String bloodFor) {
        this.bloodFor = bloodFor;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getBloodNeededDateTime() {
        return bloodNeededDateTime;
    }

    public void setBloodNeededDateTime(Date bloodNeededDateTime) {
        this.bloodNeededDateTime = bloodNeededDateTime;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBloodNeededDateTimeString() {
        return bloodNeededDateTimeString;
    }

    public void setBloodNeededDateTimeString(String bloodNeededDateTimeString) {
        this.bloodNeededDateTimeString = bloodNeededDateTimeString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
