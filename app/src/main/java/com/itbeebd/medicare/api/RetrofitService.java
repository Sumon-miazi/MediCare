package com.itbeebd.medicare.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RetrofitService {

    @POST(ApiUrls.ALL_HOSPITAL)
    Call<ResponseBody> getAllHospital(@Body Map<String, Object> body);

    @POST(ApiUrls.ALL_DOCTOR)
    Call<ResponseBody> getAllDoctorByHospitalId(@Body Map<String, Object> body);

    @POST(ApiUrls.ALL_CHAMBER_OF_A_DOCTOR)
    Call<ResponseBody> getAllDoctorChambersByDoctorId(@Body Map<String, Object> body);

    @POST(ApiUrls.SIGN_UP_PATIENT)
    Call<ResponseBody> signUpPatient(@Body Map<String, Object> body);

    @POST(ApiUrls.GET_PATIENT_DETAILS)
    Call<ResponseBody> getPatientDetails(@Body Map<String, Object> body);

    @POST(ApiUrls.CHECK_USER_EXISTENCE)
    Call<ResponseBody> checkUserExistence(@Body Map<String, Object> body);


    @POST(ApiUrls.BOOK_NEW_APPOINTMENT)
    Call<ResponseBody> bookNewAppointment(@Body Map<String, Object> body);


    @POST(ApiUrls.ADD_BLOOD_DONOR)
    Call<ResponseBody> addBloodDonor(@Body Map<String, Object> body);

    @POST(ApiUrls.GET_BLOOD_DONOR_LIST)
    Call<ResponseBody> getBloodDonor(@Body Map<String, Object> body);

    @POST(ApiUrls.NEW_BLOOD_REQUEST)
    Call<ResponseBody> addNewBloodRequest(@Body Map<String, Object> body);

    @POST(ApiUrls.GET_BLOOD_REQUEST)
    Call<ResponseBody> getBloodRequest(@Body Map<String, Object> body);

    @POST(ApiUrls.GET_ALL_BLOOD_BANK)
    Call<ResponseBody> getAllBloodBank(@Body Map<String, Object> body);


    @POST(ApiUrls.SIGN_UP_BLOOD_BANK)
    Call<ResponseBody> signUpBloodBank(@Body Map<String, Object> body);

    @POST(ApiUrls.GET_BLOOD_BANK_DATA)
    Call<ResponseBody> getBloodBankData(@Body Map<String, Object> body);

    @POST(ApiUrls.GET_BLOOD_REQUEST_OF_BLOOD_BANK)
    Call<ResponseBody> getAllBloodRequestOfABloodBankById(@Body Map<String, Object> body);



    @POST(ApiUrls.SIGN_UP_DOCTOR)
    Call<ResponseBody> signUpDoctor(@Body Map<String, Object> body);

    @POST(ApiUrls.GET_DOCTOR_DATA)
    Call<ResponseBody> getDoctorData(@Body Map<String, Object> body);


}
