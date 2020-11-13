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

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getBloodFor() {
        return bloodFor;
    }

    public String getCity() {
        return city;
    }

    public String getHospital() {
        return hospital;
    }

    public String getAmount() {
        return amount;
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

    public String getBloodNeededDateTimeString() {
        return bloodNeededDateTimeString;
    }

    public void setBloodNeededDateTimeString(String bloodNeededDateTimeString) {
        this.bloodNeededDateTimeString = bloodNeededDateTimeString;
    }
}
