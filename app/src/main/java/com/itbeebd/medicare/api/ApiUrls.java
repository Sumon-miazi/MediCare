package com.itbeebd.medicare.api;

public class ApiUrls {

    public static final String IP_ADDRESS = "http://192.168.43.77";

    public static final String BASE_URL = IP_ADDRESS + "/MediCare/public/api/";

    public static final String ALL_HOSPITAL = "getAllHospital";
    public static final String ALL_DOCTOR = "getAllDoctorByHospitalId";
    public static final String ALL_CHAMBER_OF_A_DOCTOR = "getDoctorChambersByDoctorId";


    public static final String SIGN_UP_PATIENT = "signUpPatient";
    public static final String GET_PATIENT_DETAILS = "getPatientDetailsByUid";
    public static final String CHECK_USER_EXISTENCE = "checkUserExistenceByUid";


    public static final String BOOK_NEW_APPOINTMENT = "bookNewAppointment";


    public static final String ADD_BLOOD_DONOR = "addBloodDonor";
    public static final String GET_BLOOD_DONOR_LIST = "getDonorList";
    public static final String NEW_BLOOD_REQUEST = "addNewBloodRequest";
    public static final String GET_BLOOD_REQUEST = "getBloodRequest";
    public static final String GET_ALL_BLOOD_BANK = "getAllBloodBank";

    public static final String SIGN_UP_BLOOD_BANK = "signUpBloodBank";
    public static final String GET_BLOOD_BANK_DATA = "getBloodBankDataByUid";
    public static final String GET_BLOOD_REQUEST_OF_BLOOD_BANK = "getAllBloodRequestOfABloodBankById";



    public static final String SIGN_UP_DOCTOR = "signUpDoctor";
    public static final String GET_DOCTOR_DATA = "getDoctorDataByUid";

    public static final String SIGN_UP_DIAGNOSTIC = "signUpDiagnosticCenter";
    public static final String GET_DIAGNOSTIC_DATA = "getDiagnosticCenterDataByUid";



}
