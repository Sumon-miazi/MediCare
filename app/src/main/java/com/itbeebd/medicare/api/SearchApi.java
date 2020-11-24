package com.itbeebd.medicare.api;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.itbeebd.medicare.api.allInterfaces.GetSearchedData;
import com.itbeebd.medicare.utils.BloodBank;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.itbeebd.medicare.utils.Hospital;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchApi extends BaseService{

    private Context context;

    public SearchApi() {

    }

    public SearchApi(Context context) {
        this.context = context;
    }
    public void getNearby(LatLng userLocation, String name, int distance, final GetSearchedData getSearchedData) {

        System.out.println("getNearByNameAndDistance>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getNearByNameAndDistance(retrofitRequestBody.getNearByNameAndDistance(userLocation,name, distance));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("getNearByNameAndDistance>>>>>>>>>>> " + jsonObject.toString());
                        System.out.println("message>>>>>>>>>>> " + jsonObject.optString("message"));
                        System.out.println("condition>>>>>>>>>>> " + name.toLowerCase().equals(ApiUrls.HOSPITAL));
                        System.out.println("condition>>>>>>>>>>> " + name.toLowerCase().equals(ApiUrls.DIAGNOSTIC));
                        System.out.println("condition>>>>>>>>>>> " + name.toLowerCase().equals(ApiUrls.BLOODBANK));
                       // System.out.println("name>>>>>>>>>>> " + name);

                        if (jsonObject.optString("status").equals("true")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if(name.toLowerCase().equals(ApiUrls.HOSPITAL)){
                                System.out.println("name>>>>>>>>>>> " + name);
                                getSearchedData.data(
                                        getHospitalsFromJsonArray(jsonArray),
                                        null,
                                        null,
                                        jsonObject.optString("message"));
                            }

                            else if(name.toLowerCase().equals(ApiUrls.DIAGNOSTIC)){
                                System.out.println("name>>>>>>>>>>> " + name);
                                getSearchedData.data(
                                        null,
                                        getDiagnosticsFromJsonArray(jsonArray),
                                        null,
                                        jsonObject.optString("message"));
                            }

                            else if(name.toLowerCase().equals(ApiUrls.BLOODBANK)){
                                System.out.println("name>>>>>>>>>>> " + name);
                                getSearchedData.data(
                                        null,
                                        null,
                                        getBloodBanksFromJsonArray(jsonArray),
                                        jsonObject.optString("message"));
                            }


                        } else getSearchedData.data(null, null, null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getNearByNameAndDistance>>>>>>>>>>> catch " + ignore.getMessage());

                        getSearchedData.data(null, null, null,  ignore.getMessage());
                    }
                } else getSearchedData.data(null, null, null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("getNearByNameAndDistance>>>>>>>>>>> failed " + t.getMessage());

                getSearchedData.data(null, null, null, t.getMessage());
            }
        });
    }

    private ArrayList<Hospital> getHospitalsFromJsonArray(JSONArray hospitalsJsonArray){
        try {
            ArrayList<Hospital> hospitalArrayList = new ArrayList<>();

            for (int i = 0; i < hospitalsJsonArray.length(); i++) {

                JSONObject object = hospitalsJsonArray.getJSONObject(i);

                Hospital hospital = new Hospital(object.getInt("id"),
                        object.getString("name"),
                        object.getString("address"),
                        object.getString("phone"),
                        object.getDouble("latitude"),
                        object.getDouble("longitude"));
                hospital.setImage(object.optString("image").equals("null")? null : object.optString("image"));

                hospitalArrayList.add(hospital);
            }

            return hospitalArrayList;
        }
        catch (Exception ignore){
            return null;
        }
    }

    private ArrayList<DiagnosticCenter> getDiagnosticsFromJsonArray(JSONArray diagnosticsJsonArray){
        try {
            ArrayList<DiagnosticCenter> diagnosticCenters = new ArrayList<>();

            for (int i = 0; i < diagnosticsJsonArray.length(); i++) {

                JSONObject object = diagnosticsJsonArray.getJSONObject(i);

                DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
                diagnosticCenter.setDiagnosticId(object.getInt("id"));
                diagnosticCenter.setName(object.getString("name"));
                diagnosticCenter.setImage(object.optString("image").equals("null")? null : object.optString("image"));
                diagnosticCenter.setAddress(object.getString("address"));
                diagnosticCenter.setPhone(object.getString("phone"));
                diagnosticCenter.setServices(object.getString("services"));
                diagnosticCenter.setLat(object.getDouble("latitude"));
                diagnosticCenter.setLon(object.getDouble("longitude"));

                diagnosticCenters.add(diagnosticCenter);
            }
            System.out.println("name>>>>>>>>>>>dc " + diagnosticCenters.size());
            return diagnosticCenters;
        }
        catch (Exception ignore){
            System.out.println("name>>>>>>>>>>>dc " + ignore.getMessage());
            return null;
        }
    }

    private ArrayList<BloodBank> getBloodBanksFromJsonArray(JSONArray bloodBankArray){
        try {
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

            return bloodBanks;
        }
        catch (Exception ignore){
            return null;
        }
    }
}