package com.itbeebd.medicare.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CustomDayOfWeek implements Parcelable {
    private final String day;
    private final ArrayList<String> times;

    public CustomDayOfWeek(String day, ArrayList<String> times) {
        this.day = day;
        this.times = times;
    }

    public static final Creator<CustomDayOfWeek> CREATOR = new Creator<CustomDayOfWeek>() {
        @Override
        public CustomDayOfWeek createFromParcel(Parcel in) {
            return new CustomDayOfWeek(in);
        }

        @Override
        public CustomDayOfWeek[] newArray(int size) {
            return new CustomDayOfWeek[size];
        }
    };

    public String getDay() {
        return day;
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    public CustomDayOfWeek(Parcel parcel) {
        this.day = parcel.readString();
        this.times = parcel.readArrayList(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.day);
        parcel.writeList(this.times);
    }
}
