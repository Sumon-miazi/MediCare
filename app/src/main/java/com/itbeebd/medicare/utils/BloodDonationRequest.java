package com.itbeebd.medicare.utils;

import java.io.Serializable;

public class BloodDonationRequest implements Serializable {

    private int id;
    private int userId;
    private String name;
    private String image;
    private String phone;
    private String token;
    private String bloodFor;
    private String city;
    private String hospital;
    private String amount;
    private String bloodGroup;
    private String bloodNeededDateTime;

    public BloodDonationRequest(int id,
                                int userId,
                                String name,
                                String bloodFor,
                                String city,
                                String hospital,
                                String amount,
                                String bloodGroup,
                                String bloodNeededDateTime,
                                String phone,
                                String token) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.bloodFor = bloodFor;
        this.city = city;
        this.hospital = hospital;
        this.amount = amount;
        this.bloodGroup = bloodGroup;
        this.bloodNeededDateTime = bloodNeededDateTime;
        this.phone = phone;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getToken() {
        return token;
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

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getBloodNeededDateTime() {
        return bloodNeededDateTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
