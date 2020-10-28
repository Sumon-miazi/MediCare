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

}
