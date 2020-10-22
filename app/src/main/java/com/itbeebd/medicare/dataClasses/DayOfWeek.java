package com.itbeebd.medicare.dataClasses;

import java.util.ArrayList;

public class DayOfWeek {
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
}
