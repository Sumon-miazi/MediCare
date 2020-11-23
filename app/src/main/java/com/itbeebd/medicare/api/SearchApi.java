package com.itbeebd.medicare.api;

import android.content.Context;

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
    public void getNearByNameAndDistance(String name, int distance,final GetSearchedData getSearchedData) {

        System.out.println("getNearByNameAndDistance>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getNearByNameAndDistance(retrofitRequestBody.getNearByNameAndDistance(name, distance));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("getNearByNameAndDistance>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if(name.toLowerCase().equals("hospitals")){
                                getSearchedData.data(
                                        getHospitalsFromJsonArray(jsonArray),
                                        null,
                                        null,
                                        jsonObject.optString("message"));
                            }

                            else if(name.toLowerCase().equals("diagnostic centers")){
                                getSearchedData.data(
                                        null,
                                        getDiagnosticsFromJsonArray(jsonArray),
                                        null,
                                        jsonObject.optString("message"));
                            }

                            else if(name.toLowerCase().equals("blood banks")){
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
                        object.getDouble("lat"),
                        object.getDouble("long"));
                hospital.setImage(object.optString("image"));

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
                diagnosticCenter.setImage(object.getString("image"));
                diagnosticCenter.setAddress(object.getString("address"));
                diagnosticCenter.setPhone(object.getString("phone"));
                diagnosticCenter.setServices(object.getString("services"));
                diagnosticCenter.setLat(object.getDouble("lat"));
                diagnosticCenter.setLon(object.getDouble("long"));

                diagnosticCenters.add(diagnosticCenter);
            }

            return diagnosticCenters;
        }
        catch (Exception ignore){
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
                        object.getDouble("lat"),
                        object.getDouble("long"));
                bloodBank.setImage(object.optString("image"));

                bloodBanks.add(bloodBank);
            }

            return bloodBanks;
        }
        catch (Exception ignore){
            return null;
        }
    }
}