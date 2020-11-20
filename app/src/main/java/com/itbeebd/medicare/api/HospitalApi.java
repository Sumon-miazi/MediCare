package com.itbeebd.medicare.api;

import com.itbeebd.medicare.api.allInterfaces.GetDataFromApiCall;
import com.itbeebd.medicare.utils.Hospital;
import com.itbeebd.medicare.utils.Specialist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalApi extends BaseService {

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
                                hospital.setImage(object.optString("image"));

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

    public void getAllSpecialist(final GetDataFromApiCall<Specialist> getDataFromApiCall) {

        System.out.println("getAllSpecialist>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllSpecialist(retrofitRequestBody.getApiKey());
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("getAllSpecialist>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            JSONArray JsonArray = jsonObject.getJSONArray("data");

                            ArrayList<Specialist> specialists = new ArrayList<>();

                            for (int i = 0; i < JsonArray.length(); i++) {

                                JSONObject object = JsonArray.getJSONObject(i);

                                Specialist specialist = new Specialist(object.getInt("id"),
                                        object.getString("type"),
                                        object.getString("icon"));

                                specialists.add(specialist);
                            }

                            // Collections.shuffle(questionArrayList);

                            getDataFromApiCall.data(specialists, jsonObject.optString("message"));

                        } else getDataFromApiCall.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getAllSpecialist>>>>>>>>>>> catch " + ignore.getMessage());

                        getDataFromApiCall.data(null, ignore.getMessage());
                    }
                } else getDataFromApiCall.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("getAllSpecialist>>>>>>>>>>> failed " + t.getMessage());

                getDataFromApiCall.data(null, t.getMessage());
            }
        });
    }
}
