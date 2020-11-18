package com.itbeebd.medicare.utils;

public class Specialist {
    private int id;
    private String name;
    private String icon;

    public Specialist(int id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
}

