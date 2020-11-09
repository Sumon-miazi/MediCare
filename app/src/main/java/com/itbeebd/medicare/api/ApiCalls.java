package com.itbeebd.medicare.api;

import com.itbeebd.medicare.api.allInterfaces.GetDataFromApiCall;
import com.itbeebd.medicare.api.allInterfaces.GetPatientInfo;
import com.itbeebd.medicare.api.allInterfaces.GetResponse;
import com.itbeebd.medicare.utils.CustomDayOfWeek;
import com.itbeebd.medicare.utils.Doctor;
import com.itbeebd.medicare.utils.DoctorChamber;
import com.itbeebd.medicare.utils.Hospital;
import com.itbeebd.medicare.utils.Patient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCalls {

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiUrls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private final RetrofitService service = retrofit.create(RetrofitService.class);


    public void getAllHospital(final GetDataFromApiCall<Hospital> getAllHospital) {

        System.out.println("getAllHospital>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllHospital(retrofitRequestBody.getApiKey());
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("getAllHospital>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            JSONArray hospitalsJsonArray = jsonObject.getJSONArray("data");

                            ArrayList<Hospital> hospitalArrayList = new ArrayList<>();

                            for (int i = 0; i < hospitalsJsonArray.length(); i++) {

                                JSONObject object = hospitalsJsonArray.getJSONObject(i);

                                Hospital hospital = new Hospital(object.getInt("id"),
                                        object.getString("name"),
                                        object.getString("address"),
                                        object.getString("phone"),
                                        object.getDouble("lat"),
                                        object.getDouble("long"));

                                hospitalArrayList.add(hospital);
                            }

                            // Collections.shuffle(questionArrayList);

                            getAllHospital.data(hospitalArrayList, jsonObject.optString("message"));

                        } else getAllHospital.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getAllHospital>>>>>>>>>>> catch " + ignore.getMessage());

                        getAllHospital.data(null, ignore.getMessage());
                    }
                } else getAllHospital.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("getAllHospital>>>>>>>>>>> failed " + t.getMessage());

                getAllHospital.data(null, t.getMessage());
            }
        });
    }

    public void getAllDoctorByHospitalId(int hospitalId, final GetDataFromApiCall<Doctor> getAllDoctor) {

        System.out.println("getAllDoctorByHospitalId>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllDoctorByHospitalId(retrofitRequestBody.getAllDoctorByHospitalId(hospitalId));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("getAllDoctorByHospitalId>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            JSONArray doctorJsonArray = jsonObject.getJSONArray("data");

                            ArrayList<Doctor> doctorArrayList = new ArrayList<>();

                            for (int i = 0; i < doctorJsonArray.length(); i++) {

                                JSONObject object = doctorJsonArray.getJSONObject(i);

                                Doctor doctor = new Doctor(object.getInt("id"),
                                        object.getInt("hospital_id"),
                                        object.getString("name"),
                                        object.getString("dob"),
                                        object.getString("education_history"),
                                        object.getString("address"),
                                        object.getString("phone"));

                                doctorArrayList.add(doctor);
                            }

                            // Collections.shuffle(questionArrayList);

                            getAllDoctor.data(doctorArrayList, jsonObject.optString("message"));
                        } else getAllDoctor.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getAllDoctorByHospitalId>>>>>>>>>>> catch " + ignore.getMessage());

                        getAllDoctor.data(null, ignore.getMessage());
                    }
                } else getAllDoctor.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("getAllDoctorByHospitalId>>>>>>>>>>> failed " + t.getMessage());

                getAllDoctor.data(null, t.getMessage());
            }
        });
    }

    public void getAllDoctorChambersByDoctorId(int doctorId, final GetDataFromApiCall<DoctorChamber> getAllDoctorChamber) {

        System.out.println("getAllDoctorChambersByDoctorId>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllDoctorChambersByDoctorId(retrofitRequestBody.getAllDoctorChambersByDoctorId(doctorId));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("getAllDoctorChambersByDoctorId>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            JSONArray doctorChamberJsonArray = jsonObject.getJSONArray("data");

                            ArrayList<DoctorChamber> doctorChamberArrayList = new ArrayList<>();

                            for (int i = 0; i < doctorChamberJsonArray.length(); i++) {

                                JSONObject object = doctorChamberJsonArray.getJSONObject(i);
                                JSONObject hospitalObj = object.getJSONObject("hospital");
                                DoctorChamber doctorChamber = new DoctorChamber(object.getInt("id"),
                                        object.getInt("doctor_id"),
                                        object.getInt("hospital_id"),
                                        hospitalObj.getString("name"),
                                        object.getString("visit_fee"),
                                        hospitalObj.getString("address"),
                                        hospitalObj.getString("phone"),
                                        hospitalObj.getDouble("lat"),
                                        hospitalObj.getDouble("long"));

                                JSONArray availableDays = object.getJSONArray("available_days");
                                ArrayList<CustomDayOfWeek> customDayOfWeekArrayList = new ArrayList<>();

                                for (int j = 0; j < availableDays.length(); j++) {
                                    JSONObject dayObj = availableDays.getJSONObject(j);
                                    ArrayList<String> times = new ArrayList<>();

                                    JSONArray timesList = dayObj.getJSONArray("available_times");
                                    for (int k = 0; k < timesList.length(); k++) {
                                        JSONObject obj = timesList.getJSONObject(k);
                                        times.add(obj.getString("time"));
                                    }
                                    CustomDayOfWeek customDayOfWeek = new CustomDayOfWeek(dayObj.getString("day"), times);
                                    customDayOfWeekArrayList.add(customDayOfWeek);
                                }
                                doctorChamber.setCustomDayOfWeekArrayList(customDayOfWeekArrayList);
                                doctorChamberArrayList.add(doctorChamber);
                            }

                            // Collections.shuffle(questionArrayList);

                            getAllDoctorChamber.data(doctorChamberArrayList, jsonObject.optString("message"));
                        } else getAllDoctorChamber.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getAllDoctorChambersByDoctorId>>>>>>>>>>> catch " + ignore.getMessage());

                        getAllDoctorChamber.data(null, ignore.getMessage());
                    }
                } else getAllDoctorChamber.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("getAllDoctorChambersByDoctorId>>>>>>>>>>> failed " + t.getMessage());

                getAllDoctorChamber.data(null, t.getMessage());
            }
        });
    }

    public void signUpPatient(Patient patient, GetPatientInfo getPatientInfo) {

        System.out.println("signUpPatient>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.signUpPatient(retrofitRequestBody.signUpPatient(patient));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("signUpPatient>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            JSONObject patientJsonObj = jsonObject.getJSONObject("data");

                            Patient patientInfo = new Patient();
                            patientInfo.setId(patientJsonObj.getInt("id"));
                            patientInfo.setName(patientJsonObj.getString("name"));
                            patientInfo.setUid(patientJsonObj.getString("uid"));

                            getPatientInfo.data(patientInfo, jsonObject.optString("message"));
                        } else getPatientInfo.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("signUpPatient>>>>>>>>>>> catch " + ignore.getMessage());

                        getPatientInfo.data(null, ignore.getMessage());
                    }
                } else getPatientInfo.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("signUpPatient>>>>>>>>>>> failed " + t.getMessage());

                getPatientInfo.data(null, t.getMessage());
            }
        });
    }

    public void checkUserExistOrNot(String uid, GetResponse getResponse) {
        System.out.println("checkUserExistOrNot>>>>>>>>>>> called ");
        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.checkUserExistence(retrofitRequestBody.getPatientDetails(uid));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        System.out.println("checkUserExistOrNot>>>>>>>>>>> " + jsonObject.toString());
                        getResponse.data(jsonObject.optString("success").equals("true"), jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("checkUserExistOrNot>>>>>>>>>>> catch " + ignore.getMessage());
                        getResponse.data(false, jsonObject.optString("message"));
                    }
                } else getResponse.data(false, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("checkUserExistOrNot>>>>>>>>>>> failed " + t.getMessage());
                getResponse.data(false, t.getMessage());
            }
        });
    }

    public void getUserData(String uid, GetPatientInfo getPatientInfo) {
        System.out.println("getUserData>>>>>>>>>>> called ");
        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getPatientDetails(retrofitRequestBody.getPatientDetails(uid));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        System.out.println("getUserData>>>>>>>>>>> " + jsonObject.toString());
                        if (jsonObject.optString("status").equals("true")) {
                            JSONObject userObj = jsonObject.getJSONObject("data");

                            Patient patient = new Patient(
                                    userObj.getInt("id"),
                                    userObj.getString("name"),
                                    userObj.getString("uid"),
                                    userObj.getString("gender"),
                                    userObj.getString("dob"),
                                    userObj.getDouble("weight"),
                                    userObj.getString("blood_group"),
                                    userObj.getInt("is_blood_donor"),
                                    userObj.getString("address"),
                                    userObj.getString("phone"),
                                    userObj.getString("token")
                            );

                            getPatientInfo.data(patient, jsonObject.optString("message"));
                        } else getPatientInfo.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getUserData>>>>>>>>>>> catch " + ignore.getMessage());
                        getPatientInfo.data(null, ignore.getMessage());
                    }
                } else getPatientInfo.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("getUserData>>>>>>>>>>> failed " + t.getMessage());
                getPatientInfo.data(null, t.getMessage());
            }
        });
    }

}
