package com.itbeebd.medicare.utils;

public class Hospital {
    private int id;
    private String name;
    private String address;
    private String phone;
    private double lat;
    private double lon;

    public Hospital(int id, String name, String address, String phone, double lat, double lon) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
