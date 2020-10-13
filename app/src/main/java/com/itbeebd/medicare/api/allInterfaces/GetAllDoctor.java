package com.itbeebd.medicare.api.allInterfaces;

import com.itbeebd.medicare.dataClasses.Doctor;

import java.util.ArrayList;

public interface GetAllDoctor {
    void data(ArrayList<Doctor> doctors, String message);
}