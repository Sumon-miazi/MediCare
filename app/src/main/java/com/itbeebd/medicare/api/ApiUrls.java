package com.itbeebd.medicare.api;

public class ApiUrls {

    public static final String IP_ADDRESS = "http://192.168.43.77";

    public static final String BASE_URL = IP_ADDRESS + "/MediCare/public/api/";

    public static final String ALL_HOSPITAL = "getAllHospital";
    public static final String ALL_DOCTOR = "getAllDoctorByHospitalId";
    public static final String ALL_CHAMBER_OF_A_DOCTOR = "getDoctorChambersByDoctorId";
}
