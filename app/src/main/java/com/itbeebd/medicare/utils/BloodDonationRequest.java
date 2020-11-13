package com.itbeebd.medicare.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class BloodDonationRequest implements Parcelable {

    private int id;
    private int userId;
    private String name;
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


    public static final Creator<BloodDonationRequest> CREATOR = new Creator<BloodDonationRequest>() {
        @Override
        public BloodDonationRequest createFromParcel(Parcel in) {
            return new BloodDonationRequest(in);
        }

        @Override
        public BloodDonationRequest[] newArray(int size) {
            return new BloodDonationRequest[size];
        }
    };

    public BloodDonationRequest(Parcel parcel) {
        this.id = parcel.readInt();
        this.userId = parcel.readInt();
        this.name = parcel.readString();
        this.bloodFor = parcel.readString();
        this.city = parcel.readString();
        this.hospital = parcel.readString();
        this.amount = parcel.readString();
        this.bloodGroup = parcel.readString();
        this.bloodNeededDateTime = parcel.readString();
        this.phone = parcel.readString();
        this.token = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.userId);
        parcel.writeString(this.name);
        parcel.writeString(this.bloodFor);
        parcel.writeString(this.city);
        parcel.writeString(this.hospital);
        parcel.writeString(this.amount);
        parcel.writeString(this.bloodGroup);
        parcel.writeString(this.bloodNeededDateTime);
        parcel.writeString(this.phone);
        parcel.writeString(this.token);
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
}
