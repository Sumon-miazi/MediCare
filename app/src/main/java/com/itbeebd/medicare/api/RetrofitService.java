package com.itbeebd.medicare.api;

import java.util.Date;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface RetrofitService {


    @POST(ApiUrls.SEARCH_NEARBY)
    Call<ResponseBody> getNearByNameAndDistance(@Body Map<String, Object> body);

    @POST(ApiUrls.ALL_HOSPITAL)
    Call<ResponseBody> getAllHospital(@Body Map<String, Object> body);

    @POST(ApiUrls.ALL_DIAGNOSTIC)
    Call<ResponseBody> getAllDiagnosticCenter(@Body Map<String, Object> body);

    @POST(ApiUrls.ALL_SPECIALIST)
    Call<ResponseBody> getAllSpecialist(@Body Map<String, Object> body);

    @POST(ApiUrls.ALL_DOCTOR_BY_SPECIALIST_ID)
    Call<ResponseBody> getAllDoctorBySpecialistId(@Body Map<String, Object> body);

    @POST(ApiUrls.ALL_DOCTOR)
    Call<ResponseBody> getAllDoctorByHospitalId(@Body Map<String, Object> body);

    @POST(ApiUrls.ALL_CHAMBER_OF_A_DOCTOR)
    Call<ResponseBody> getAllDoctorChambersByDoctorId(@Body Map<String, Object> body);

   // @POST(ApiUrls.SIGN_UP_PATIENT)
   // Call<ResponseBody> signUpPatient(@Body Map<String, Object> body);

    @Multipart
    @POST(ApiUrls.SIGN_UP_PATIENT)
    Call<ResponseBody> signUpPatient(@Part MultipartBody.Part image,
                                              @Part("uid") String uid,
                                              @Part("name") String name,
                                              @Part("gender") String services,
                                              @Part("is_blood_donor") int is_blood_donor,
                                              @Part("lastDonate") Date lastDonate,
                                              @Part("dob") String  dob,
                                              @Part("weight") double  weight,
                                              @Part("blood_group") String blood_group,
                                              @Part("address") String address,
                                              @Part("phone") String phone,
                                              @Part("token") String token,
                                              @Part("api_key") String api_key);


    @POST(ApiUrls.GET_PATIENT_DETAILS)
    Call<ResponseBody> getPatientDetails(@Body Map<String, Object> body);

    @POST(ApiUrls.CHECK_USER_EXISTENCE)
    Call<ResponseBody> checkUserExistence(@Body Map<String, Object> body);



    @POST(ApiUrls.BOOK_NEW_APPOINTMENT)
    Call<ResponseBody> bookNewAppointment(@Body Map<String, Object> body);

    @POST(ApiUrls.GET_ALL_APPOINTMENT)
    Call<ResponseBody> getAllAppointment(@Body Map<String, Object> body);

    @POST(ApiUrls.GET_NEXT_APPOINTMENT)
    Call<ResponseBody> getNextAppointment(@Body Map<String, Object> body);

    @POST(ApiUrls.GET_AN_APPOINTMENT_REPORTS)
    Call<ResponseBody> getAnAppointmentReports(@Body Map<String, Object> body);

    @POST(ApiUrls.GET_ALL_REPORTS)
    Call<ResponseBody> getAllReports(@Body Map<String, Object> body);

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

    @POST(ApiUrls.GET_ALL_PHARMACY)
    Call<ResponseBody> getAllPharmacy(@Body Map<String, Object> body);


   // @POST(ApiUrls.SIGN_UP_BLOOD_BANK)
   // Call<ResponseBody> signUpBloodBank(@Body Map<String, Object> body);

    @Multipart
    @POST(ApiUrls.SIGN_UP_BLOOD_BANK)
    Call<ResponseBody> signUpBloodBank(@Part MultipartBody.Part image,
                                    @Part("uid") String uid,
                                    @Part("name") String name,
                                    @Part("address") String address,
                                    @Part("about") String about,
                                    @Part("phone") String phone,
                                    @Part("email") String email,
                                    @Part("token") String token,
                                    @Part("lat") double lat,
                                    @Part("long") double lon,
                                    @Part("api_key") String api_key);

    @Multipart
    @POST(ApiUrls.SIGN_UP_PHARMACY)
    Call<ResponseBody> signUpPharmacy(@Part MultipartBody.Part image,
                                       @Part("uid") String uid,
                                       @Part("name") String name,
                                       @Part("address") String address,
                                       @Part("about") String about,
                                       @Part("phone") String phone,
                                       @Part("email") String email,
                                       @Part("token") String token,
                                       @Part("lat") double lat,
                                       @Part("long") double lon,
                                       @Part("api_key") String api_key);

    @POST(ApiUrls.GET_BLOOD_BANK_DATA)
    Call<ResponseBody> getBloodBankData(@Body Map<String, Object> body);

    @POST(ApiUrls.GET_BLOOD_REQUEST_OF_BLOOD_BANK)
    Call<ResponseBody> getAllBloodRequestOfABloodBankById(@Body Map<String, Object> body);



   // @POST(ApiUrls.SIGN_UP_DOCTOR)
   // Call<ResponseBody> signUpDoctor(@Body Map<String, Object> body);

    @Multipart
    @POST(ApiUrls.SIGN_UP_DOCTOR)
    Call<ResponseBody> signUpDoctor(@Part MultipartBody.Part image,
                                              @Part("uid") String uid,
                                              @Part("name") String name,
                                              @Part("bmdcRegNo") String bmdcRegNo,
                                              @Part("specialist") String specialist,
                                              @Part("gender") String gender,
                                              @Part("address") String address,
                                              @Part("about") String about,
                                              @Part("educationHistory") String educationHistory,
                                              @Part("phone") String phone,
                                              @Part("email") String email,
                                              @Part("token") String token,
                                              @Part("api_key") String api_key);


    @POST(ApiUrls.GET_DOCTOR_DATA)
    Call<ResponseBody> getDoctorData(@Body Map<String, Object> body);


    @POST(ApiUrls.ORDER_TEST)
    Call<ResponseBody> orderTest(@Body Map<String, Object> body);

    @POST(ApiUrls.GET_DIAGNOSTIC_DATA)
    Call<ResponseBody> getDiagnosticCenterData(@Body Map<String, Object> body);

    @Multipart
    @POST(ApiUrls.SIGN_UP_DIAGNOSTIC)
    Call<ResponseBody> signUpDiagnosticCenter(@Part MultipartBody.Part image,
                                       @Part("uid") String uid,
                                       @Part("name") String name,
                                       @Part("services") String services,
                                       @Part("address") String address,
                                       @Part("phone") String phone,
                                       @Part("email") String email,
                                       @Part("token") String token,
                                       @Part("lat") double lat,
                                       @Part("long") double lon,
                                       @Part("api_key") String api_key);


}
