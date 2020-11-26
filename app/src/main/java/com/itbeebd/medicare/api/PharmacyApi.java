package com.itbeebd.medicare.api;

import android.content.Context;

import com.itbeebd.medicare.api.allInterfaces.GetData;
import com.itbeebd.medicare.api.allInterfaces.GetDataFromApiCall;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.utils.Pharmacy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PharmacyApi  extends BaseService{
    private Context context;

    public PharmacyApi(){

    }

    public PharmacyApi(Context context) {
        this.context = context;
    }

    public void signUpPharmacy(Pharmacy pharmacy, GetData<Pharmacy> getData) {
        System.out.println("signUpPharmacy>>>>>>>>>>> called ");

        Call<ResponseBody> responseBodyCall = service.signUpPharmacy(
                getImageFile(pharmacy.getImage()),
                pharmacy.getUid(),
                pharmacy.getName(),
                pharmacy.getAddress(),
                pharmacy.getAbout(),
                pharmacy.getPhone(),
                pharmacy.getEmail(),
                pharmacy.getToken(),
                pharmacy.getLat(),
                pharmacy.getLon(),
                new RetrofitRequestBody().getApi_key()
        );
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("signUpPharmacy>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            JSONObject userObj = jsonObject.getJSONObject("data");

                            Pharmacy bb = new Pharmacy(
                                    userObj.getString("name"),
                                    userObj.getString("address"),
                                    userObj.getString("about"),
                                    userObj.getString("phone"),
                                    userObj.getString("email")
                            );
                            bb.setImage(userObj.optString("image").equals("null")? null : userObj.optString("image"));

                            bb.setPharmacy_id(userObj.getInt("id"));
                            CustomSharedPref.getInstance(context).setUserId(bb.getPharmacy_id());

                          //  new Dao().saveBloodBankProfile(bb);

                            getData.data(bb, jsonObject.optString("message"));

                        } else getData.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("signUpPharmacy>>>>>>>>>>> catch " + ignore.getMessage());

                        getData.data(null, ignore.getMessage());
                    }
                } else getData.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("signUpPharmacy>>>>>>>>>>> failed " + t.getMessage());
                getData.data(null, t.getMessage());
            }
        });
    }

    public void getAllPharmacy(GetDataFromApiCall<Pharmacy> getDataFromApiCall) {

        System.out.println("getAllPharmacy>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllPharmacy(retrofitRequestBody.getApiKey());
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("getAllPharmacy>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {

                            JSONArray bloodBankArray = jsonObject.getJSONArray("data");

                            ArrayList<Pharmacy> pharmacies = new ArrayList<>();

                            for (int i = 0; i < bloodBankArray.length(); i++) {
                                JSONObject object = bloodBankArray.getJSONObject(i);

                                Pharmacy pharmacy = new Pharmacy(
                                        object.getInt("id"),
                                        object.getString("name"),
                                        object.getString("address"),
                                        object.getString("phone"),
                                        object.getString("about"),
                                        object.getDouble("latitude"),
                                        object.getDouble("longitude"));
                                pharmacy.setImage(object.optString("image").equals("null")? null : object.optString("image"));

                                pharmacies.add(pharmacy);
                            }
                            getDataFromApiCall.data(pharmacies, jsonObject.optString("message"));
                        } else getDataFromApiCall.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getAllPharmacy>>>>>>>>>>> catch " + ignore.getMessage());

                        getDataFromApiCall.data(null, ignore.getMessage());
                    }
                } else getDataFromApiCall.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("getAllPharmacy>>>>>>>>>>> failed " + t.getMessage());

                getDataFromApiCall.data(null, t.getMessage());
            }
        });
    }
}
