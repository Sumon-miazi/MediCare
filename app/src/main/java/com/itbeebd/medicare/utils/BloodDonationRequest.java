package com.itbeebd.medicare.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class BloodDonationRequest implements Parcelable {

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
    private int id;
    private String name;
    private String bloodGroup;
    private String address;
    private String phone;

    public BloodDonationRequest(String name, String bloodGroup, String address, String phone) {
        this.id = 0;
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.phone = phone;
    }

    public BloodDonationRequest(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        this.bloodGroup = parcel.readString();
        this.address = parcel.readString();
        this.phone = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.bloodGroup);
        parcel.writeString(this.address);
        parcel.writeString(this.phone);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
