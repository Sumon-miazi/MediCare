package com.itbeebd.medicare.api;

public class ApiUrls {


    public static final String IP_ADDRESS = "http://medicare.itbeebd.com";
    public static final String BASE_URL = IP_ADDRESS + "/api/";
    public static final String BASE_IMAGE_URL = IP_ADDRESS + "/storage/";

    public static final String HOSPITAL = "hospitals";
    public static final String DIAGNOSTIC = "diagnostic centers";
    public static final String BLOODBANK = "blood banks";
    public static final String PHARMACY = "pharmacies";


   // public static final String IP_ADDRESS = "http://192.168.43.77";
   // public static final String BASE_URL = IP_ADDRESS + "/MediCare/public/api/";
   // public static final String BASE_IMAGE_URL = IP_ADDRESS + "/MediCare/public/storage/";


    public static final String SEARCH_NEARBY = "searchNearbyByNameAndDistance";
    public static final String ALL_HOSPITAL = "getAllHospital";
    public static final String ALL_SPECIALIST = "getAllSpecialist";
    public static final String ALL_DOCTOR = "getAllDoctorByHospitalId";
    public static final String ALL_DOCTOR_BY_SPECIALIST_ID = "getAllDoctorBySpecialistId";
    public static final String ALL_CHAMBER_OF_A_DOCTOR = "getDoctorChambersByDoctorId";


    public static final String SIGN_UP_PATIENT = "signUpPatient";
    public static final String GET_PATIENT_DETAILS = "getPatientDetailsByUid";
    public static final String CHECK_USER_EXISTENCE = "checkUserExistenceByUid";


    public static final String BOOK_NEW_APPOINTMENT = "bookNewAppointment";
    public static final String GET_ALL_APPOINTMENT = "getAllAppointment";
    public static final String GET_NEXT_APPOINTMENT = "getNextAppointment";
    public static final String GET_AN_APPOINTMENT_REPORTS = "getAnAppointmentReports";
    public static final String GET_ALL_REPORTS = "getAllReports";


    public static final String ADD_BLOOD_DONOR = "addBloodDonor";
    public static final String GET_BLOOD_DONOR_LIST = "getDonorList";
    public static final String NEW_BLOOD_REQUEST = "addNewBloodRequest";
    public static final String GET_BLOOD_REQUEST = "getBloodRequest";
    public static final String GET_ALL_BLOOD_BANK = "getAllBloodBank";
    public static final String GET_BD_BR_COUNT = "getBloodDonorBloodRequestCount";

    public static final String SIGN_UP_BLOOD_BANK = "signUpBloodBank";
    public static final String GET_BLOOD_BANK_DATA = "getBloodBankDataByUid";
    public static final String GET_BLOOD_REQUEST_OF_BLOOD_BANK = "getAllBloodRequestOfABloodBankById";

    public static final String GET_ALL_PHARMACY = "getAllPharmacies";
    public static final String SIGN_UP_PHARMACY = "signUpPharmacy";
    public static final String GET_PHARMACY_DATA = "getPharmacyDataByUid";


    public static final String SIGN_UP_DOCTOR = "signUpDoctor";
    public static final String GET_DOCTOR_DATA = "getDoctorDataByUid";

    public static final String SIGN_UP_DIAGNOSTIC = "signUpDiagnosticCenter";
    public static final String ALL_DIAGNOSTIC = "getAllDiagnosticCenter";
    public static final String GET_DIAGNOSTIC_DATA = "getDiagnosticCenterDataByUid";
    public static final String ORDER_TEST = "orderTest";



}
