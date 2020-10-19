package com.itbeebd.medicare.api;

import com.itbeebd.medicare.api.allInterfaces.GetDataFromApiCall;
import com.itbeebd.medicare.dataClasses.Doctor;
import com.itbeebd.medicare.dataClasses.Hospital;

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

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiUrls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private RetrofitService service = retrofit.create(RetrofitService.class);


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
}
