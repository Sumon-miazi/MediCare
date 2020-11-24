package com.itbeebd.medicare.api;

import android.content.Context;

import com.itbeebd.medicare.api.allInterfaces.GetData;
import com.itbeebd.medicare.api.allInterfaces.GetDataFromApiCall;
import com.itbeebd.medicare.api.allInterfaces.GetResponse;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.db.Dao;
import com.itbeebd.medicare.utils.BloodBank;
import com.itbeebd.medicare.utils.BloodDonationRequest;
import com.itbeebd.medicare.utils.BloodDonor;
import com.itbeebd.medicare.utils.BloodRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BloodApi extends BaseService{
    private Context context;
     public BloodApi(){}

    public BloodApi(Context context) {
        this.context = context;
    }

    public void addBloodDonor(int id,
                              Date lastDonate,
                              boolean currentlyAvailable,
                              GetResponse getResponse) {

        System.out.println("addBloodDonor>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.addBloodDonor(retrofitRequestBody.addBloodDonor(id, lastDonate, currentlyAvailable));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("addBloodDonor>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            getResponse.data(true, jsonObject.optString("message"));
                        } else getResponse.data(false, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("addBloodDonor>>>>>>>>>>> catch " + ignore.getMessage());

                        getResponse.data(false, ignore.getMessage());
                    }
                } else getResponse.data(false, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("addBloodDonor>>>>>>>>>>> failed " + t.getMessage());

                getResponse.data(false, t.getMessage());
            }
        });
    }

    public void getBloodDonor(String bloodGroup,
                              GetDataFromApiCall<BloodDonor> getDataFromApiCall) {

        System.out.println("getBloodDonor>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getBloodDonor(retrofitRequestBody.getBloodDonor(bloodGroup));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("getBloodDonor>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {

                            JSONArray donorJsonArray = jsonObject.getJSONArray("data");

                            ArrayList<BloodDonor> bloodDonors = new ArrayList<>();

                            for (int i = 0; i < donorJsonArray.length(); i++) {

                                JSONObject object = donorJsonArray.getJSONObject(i);
                                JSONObject userDetails = object.getJSONObject("patient");

                                BloodDonor bloodDonor = new BloodDonor(object.getInt("id"),
                                        object.getInt("patient_id"),
                                        userDetails.getString("name"),
                                        object.getString("lastDonate"),
                                        userDetails.getString("blood_group"),
                                        userDetails.getString("address"),
                                        userDetails.getString("phone"),
                                        userDetails.getString("token"));
                                bloodDonor.setImage(userDetails.optString("image").equals("null")? null : userDetails.optString("image"));
                                bloodDonors.add(bloodDonor);
                            }
                            getDataFromApiCall.data(bloodDonors, jsonObject.optString("message"));
                        } else getDataFromApiCall.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getBloodDonor>>>>>>>>>>> catch " + ignore.getMessage());

                        getDataFromApiCall.data(null, ignore.getMessage());
                    }
                } else getDataFromApiCall.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("getBloodDonor>>>>>>>>>>> failed " + t.getMessage());

                getDataFromApiCall.data(null, t.getMessage());
            }
        });
    }

    public void addNewBloodRequest(BloodRequest bloodRequest, GetResponse getResponse) {

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.addNewBloodRequest(retrofitRequestBody.addNewBloodRequest(bloodRequest));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("addNewBloodRequest>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            getResponse.data(true, jsonObject.optString("message"));
                        } else getResponse.data(false, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("addNewBloodRequest>>>>>>>>>>> catch " + ignore.getMessage());

                        getResponse.data(false, ignore.getMessage());
                    }
                } else {
                    System.out.println("addNewBloodRequest>>>>>>>>." + response.errorBody());
                    getResponse.data(false, response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("addNewBloodRequest>>>>>>>>>>> failed " + t.getMessage());

                getResponse.data(false, t.getMessage());
            }
        });
    }

    public void getBloodRequest(GetDataFromApiCall<BloodDonationRequest> getDataFromApiCall) {

        System.out.println("getBloodRequest>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getBloodRequest(retrofitRequestBody.getApiKey());
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("getBloodRequest>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {

                            JSONArray bloodRequestArray = jsonObject.getJSONArray("data");

                            ArrayList<BloodDonationRequest> bloodRequests = new ArrayList<>();

                            for (int i = 0; i < bloodRequestArray.length(); i++) {

                                JSONObject object = bloodRequestArray.getJSONObject(i);
                                JSONObject userDetails = object.getJSONObject("patient");

                                BloodDonationRequest bloodRequest = new BloodDonationRequest(
                                        object.getInt("id"),
                                        object.getInt("patient_id"),
                                        userDetails.getString("name"),
                                        object.getString("bloodFor"),
                                        object.getString("city"),
                                        object.getString("hospital"),
                                        object.getString("amount"),
                                        object.getString("bloodGroup"),
                                        object.getString("date"),
                                        userDetails.getString("phone"),
                                        userDetails.getString("token"));

                                bloodRequest.setImage(userDetails.optString("image").equals("null")? null : userDetails.optString("image"));

                                bloodRequests.add(bloodRequest);
                            }
                            getDataFromApiCall.data(bloodRequests, jsonObject.optString("message"));
                        } else getDataFromApiCall.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getBloodRequest>>>>>>>>>>> catch " + ignore.getMessage());

                        getDataFromApiCall.data(null, ignore.getMessage());
                    }
                } else getDataFromApiCall.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("getBloodRequest>>>>>>>>>>> failed " + t.getMessage());

                getDataFromApiCall.data(null, t.getMessage());
            }
        });
    }



    public void signUpBloodBank(BloodBank bloodBank, GetData<BloodBank> getData) {
        System.out.println("signUpBloodBank>>>>>>>>>>> called ");

        Call<ResponseBody> responseBodyCall = service.signUpBloodBank(
                getImageFile(bloodBank.getImage()),
                bloodBank.getUid(),
                bloodBank.getName(),
                bloodBank.getAddress(),
                bloodBank.getAbout(),
                bloodBank.getPhone(),
                bloodBank.getEmail(),
                bloodBank.getToken(),
                bloodBank.getLat(),
                bloodBank.getLon(),
                new RetrofitRequestBody().getApi_key()
        );
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("signUpBloodBank>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            JSONObject userObj = jsonObject.getJSONObject("data");

                            BloodBank bb = new BloodBank(
                                    userObj.getString("name"),
                                    userObj.getString("address"),
                                    userObj.getString("about"),
                                    userObj.getString("phone"),
                                    userObj.getString("email")
                            );
                            bb.setImage(userObj.optString("image").equals("null")? null : userObj.optString("image"));

                            bb.setBloodBankId(userObj.getInt("id"));
                            CustomSharedPref.getInstance(context).setUserId(bb.getBloodBankId());

                            new Dao().saveBloodBankProfile(bb);

                            getData.data(bb, jsonObject.optString("message"));

                        } else getData.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("signUpBloodBank>>>>>>>>>>> catch " + ignore.getMessage());

                        getData.data(null, ignore.getMessage());
                    }
                } else getData.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("signUpPatient>>>>>>>>>>> failed " + t.getMessage());
                getData.data(null, t.getMessage());
            }
        });
    }

    public void getBloodBankData(String uid, GetData<BloodBank> getData) {
        System.out.println("getBloodBankData>>>>>>>>>>> called ");
        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getBloodBankData(retrofitRequestBody.getPatientDetails(uid));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        System.out.println("getBloodBankData>>>>>>>>>>> " + jsonObject.toString());
                        if (jsonObject.optString("status").equals("true")) {
                            JSONObject userObj = jsonObject.getJSONObject("data");
                            BloodBank bb = new BloodBank(
                                    userObj.getString("name"),
                                    userObj.getString("address"),
                                    userObj.getString("about"),
                                    userObj.getString("phone"),
                                    userObj.getString("email")
                            );
                            bb.setImage(userObj.optString("image").equals("null")? null : userObj.optString("image"));

                            bb.setBloodBankId(userObj.getInt("id"));
                            new Dao().saveBloodBankProfile(bb);

                            CustomSharedPref.getInstance(context).setUserId(bb.getBloodBankId());

                            getData.data(bb,jsonObject.optString("message"));

                        } else getData.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getBloodBankData>>>>>>>>>>> catch " + ignore.getMessage());
                        getData.data(null,  ignore.getMessage());
                    }
                }
                else{
                    System.out.println(">>>>>>>>>> " + response.errorBody());
                } getData.data(null,  response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("getBloodBankData>>>>>>>>>>> failed " + t.getLocalizedMessage());
                //  t.printStackTrace();
                getData.data(null, t.getMessage());
            }
        });
    }

    public void getAllBloodBank(GetDataFromApiCall<BloodBank> getDataFromApiCall) {

        System.out.println("getAllBloodBank>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllBloodBank(retrofitRequestBody.getApiKey());
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("getAllBloodBank>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {

                            JSONArray bloodBankArray = jsonObject.getJSONArray("data");

                            ArrayList<BloodBank> bloodBanks = new ArrayList<>();

                            for (int i = 0; i < bloodBankArray.length(); i++) {
                                JSONObject object = bloodBankArray.getJSONObject(i);

                                BloodBank bloodBank = new BloodBank(
                                        object.getInt("id"),
                                        object.getString("name"),
                                        object.getString("address"),
                                        object.getString("phone"),
                                        object.getString("about"),
                                        object.getDouble("latitude"),
                                        object.getDouble("longitude"));
                                bloodBank.setImage(object.optString("image").equals("null")? null : object.optString("image"));

                                bloodBanks.add(bloodBank);
                            }
                            getDataFromApiCall.data(bloodBanks, jsonObject.optString("message"));
                        } else getDataFromApiCall.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getAllBloodBank>>>>>>>>>>> catch " + ignore.getMessage());

                        getDataFromApiCall.data(null, ignore.getMessage());
                    }
                } else getDataFromApiCall.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("getAllBloodBank>>>>>>>>>>> failed " + t.getMessage());

                getDataFromApiCall.data(null, t.getMessage());
            }
        });
    }

    public void getAllBloodRequestOfABloodBankById(int id,final GetDataFromApiCall<BloodRequest> getDataFromApiCall) {

        System.out.println("getAllBloodRequestOfABloodBankById>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllBloodRequestOfABloodBankById(retrofitRequestBody.getDataById(id));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("getAllBloodRequestOfABloodBankById>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            ArrayList<BloodRequest> bloodRequests = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);

                                BloodRequest bloodRequest = new BloodRequest();
                                bloodRequest.setUserId(object.getInt("id"));
                                bloodRequest.setName(object.getString("name"));
                                bloodRequest.setAmount(object.getString("amount"));
                                bloodRequest.setBloodGroup(object.getString("bloodGroup"));
                                bloodRequest.setPhone(object.getString("phone"));
                                bloodRequests.add(bloodRequest);
                            }

                            // Collections.shuffle(questionArrayList);

                            getDataFromApiCall.data(bloodRequests, jsonObject.optString("message"));

                        } else getDataFromApiCall.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getAllBloodRequestOfABloodBankById>>>>>>>>>>> catch " + ignore.getMessage());

                        getDataFromApiCall.data(null, ignore.getMessage());
                    }
                } else getDataFromApiCall.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("getAllBloodRequestOfABloodBankById>>>>>>>>>>> failed " + t.getMessage());

                getDataFromApiCall.data(null, t.getMessage());
            }
        });
    }
}
