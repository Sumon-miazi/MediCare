package com.itbeebd.medicare.dataClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DayOfWeek implements Parcelable {
    private String day;
    private ArrayList<String> times;

    public DayOfWeek(String day, ArrayList<String> times) {
        this.day = day;
        this.times = times;
    }

    public String getDay() {
        return day;
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    public static final Creator<DayOfWeek> CREATOR = new Creator<DayOfWeek>() {
        @Override
        public DayOfWeek createFromParcel(Parcel in) {
            return new DayOfWeek(in);
        }

        @Override
        public DayOfWeek[] newArray(int size) {
            return new DayOfWeek[size];
        }
    };

    public DayOfWeek(Parcel parcel) {
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
