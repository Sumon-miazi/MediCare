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

}
