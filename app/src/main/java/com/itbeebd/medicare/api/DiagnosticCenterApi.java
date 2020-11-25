package com.itbeebd.medicare.api;

import android.content.Context;

import com.itbeebd.medicare.api.allInterfaces.GetData;
import com.itbeebd.medicare.api.allInterfaces.GetDataFromApiCall;
import com.itbeebd.medicare.api.allInterfaces.GetResponse;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.db.Dao;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.itbeebd.medicare.utils.TestReport;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiagnosticCenterApi extends BaseService{
    private Context context;

    public DiagnosticCenterApi(){}

    public DiagnosticCenterApi(Context context) {
        this.context = context;
    }

    public void signUpDiagnosticCenter(DiagnosticCenter diagnosticCenter, GetData<DiagnosticCenter> getData) {

        System.out.println("signUpDiagnosticCenter>>>>>>>>>>> called " + new RetrofitRequestBody().getApi_key());

        Call<ResponseBody> responseBodyCall = service.signUpDiagnosticCenter(
                getImageFile(diagnosticCenter.getImage()),
                diagnosticCenter.getUid(),
                diagnosticCenter.getName(),
                diagnosticCenter.getService(),
                diagnosticCenter.getAddress(),
                diagnosticCenter.getPhone(),
                diagnosticCenter.getEmail(),
                diagnosticCenter.getToken(),
                diagnosticCenter.getLat(),
                diagnosticCenter.getLon(),
                new RetrofitRequestBody().getApi_key());

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("signUpDiagnosticCenter>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            JSONObject userObj = jsonObject.getJSONObject("data");

                            DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
                            diagnosticCenter.setName(userObj.getString("name"));
                            diagnosticCenter.setImage(userObj.optString("image").equals("null")? null : userObj.optString("image"));
                            diagnosticCenter.setAddress(userObj.getString("address"));
                            diagnosticCenter.setEmail(userObj.getString("email"));
                            diagnosticCenter.setPhone(userObj.getString("phone"));
                            diagnosticCenter.setLat(userObj.getDouble("latitude"));
                            diagnosticCenter.setLon(userObj.getDouble("longitude"));
                            diagnosticCenter.setUid(userObj.getString("uid"));
                            diagnosticCenter.setDiagnosticId(userObj.getInt("id"));

                            ArrayList<String> services = new ArrayList<>();
                            JSONArray serviceArray = userObj.getJSONArray("services");
                            for(int j = 0; j < serviceArray.length(); j++){
                                services.add(serviceArray.getString(j));
                            }
                            diagnosticCenter.setServices(services);

                            CustomSharedPref.getInstance(context).setUserId(diagnosticCenter.getDiagnosticId());

                            new Dao().saveDiagnosticCenterProfile(diagnosticCenter);

                            getData.data(diagnosticCenter, jsonObject.optString("message"));

                        } else getData.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("signUpDiagnosticCenter>>>>>>>>>>> catch " + ignore.getMessage());

                        getData.data(null, ignore.getMessage());
                    }
                }
                else{
                    System.out.println("signUpDiagnosticCenter>>>>>>>>>>> res " + response.body());
                    getData.data(null, response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("signUpDiagnosticCenter>>>>>>>>>>> failed " + t.getMessage());
                getData.data(null, t.getMessage());
            }
        });
    }

    public void getDiagnosticCenterData(String uid, GetData<DiagnosticCenter> getData) {
        System.out.println("getBloodBankData>>>>>>>>>>> called ");
        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getDiagnosticCenterData(retrofitRequestBody.getPatientDetails(uid));
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

                            DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
                            diagnosticCenter.setName(userObj.getString("name"));
                            diagnosticCenter.setImage(userObj.optString("image").equals("null")? null : userObj.optString("image"));
                            diagnosticCenter.setAddress(userObj.getString("address"));
                            diagnosticCenter.setEmail(userObj.getString("email"));
                            diagnosticCenter.setPhone(userObj.getString("phone"));
                            diagnosticCenter.setLat(userObj.getDouble("latitude"));
                            diagnosticCenter.setLon(userObj.getDouble("longitude"));
                            diagnosticCenter.setUid(userObj.getString("uid"));
                            diagnosticCenter.setDiagnosticId(userObj.getInt("id"));

                            ArrayList<String> services = new ArrayList<>();
                            JSONArray serviceArray = userObj.getJSONArray("services");
                            for(int j = 0; j < serviceArray.length(); j++){
                                services.add(serviceArray.getString(j));
                            }
                            diagnosticCenter.setServices(services);

                            new Dao().saveDiagnosticCenterProfile(diagnosticCenter);

                            CustomSharedPref.getInstance(context).setUserId(diagnosticCenter.getDiagnosticId());

                            getData.data(diagnosticCenter, jsonObject.optString("message"));

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

    public void getAllDiagnosticCenter(final GetDataFromApiCall<DiagnosticCenter> getAllDiagnostic) {

        System.out.println("getAllDiagnosticCenter>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllDiagnosticCenter(retrofitRequestBody.getApiKey());
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("getAllDiagnosticCenter>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            JSONArray diagnosticJsonArray = jsonObject.getJSONArray("data");

                            ArrayList<DiagnosticCenter> diagnosticCenters = new ArrayList<>();

                            for (int i = 0; i < diagnosticJsonArray.length(); i++) {

                                JSONObject object = diagnosticJsonArray.getJSONObject(i);

                                DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
                                diagnosticCenter.setDiagnosticId(object.getInt("id"));
                                diagnosticCenter.setName(object.getString("name"));
                                diagnosticCenter.setImage(object.optString("image").equals("null")? null : object.optString("image"));
                                diagnosticCenter.setAddress(object.getString("address"));
                                diagnosticCenter.setPhone(object.getString("phone"));

                                diagnosticCenter.setLat(object.getDouble("latitude"));
                                diagnosticCenter.setLon(object.getDouble("longitude"));

                                ArrayList<String> services = new ArrayList<>();
                                JSONArray serviceArray = object.getJSONArray("services");
                                for(int j = 0; j < serviceArray.length(); j++){
                                    services.add(serviceArray.getString(j));
                                }
                                diagnosticCenter.setServices(services);

                                diagnosticCenters.add(diagnosticCenter);
                            }

                            // Collections.shuffle(questionArrayList);

                            getAllDiagnostic.data(diagnosticCenters, jsonObject.optString("message"));

                        } else getAllDiagnostic.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getAllDiagnosticCenter>>>>>>>>>>> catch " + ignore.getMessage());

                        getAllDiagnostic.data(null, ignore.getMessage());
                    }
                } else getAllDiagnostic.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("getAllDiagnosticCenter>>>>>>>>>>> failed " + t.getMessage());

                getAllDiagnostic.data(null, t.getMessage());
            }
        });
    }

    public void orderTest(TestReport testReport, GetResponse getResponse) {

        System.out.println("orderTest >>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.orderTest(retrofitRequestBody.orderTest(testReport));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("orderTest >>>>>>>>>>> " + jsonObject.toString());

                        getResponse.data(jsonObject.optString("status").equals("true"), jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("orderTest >>>>>>>>>>> catch " + ignore.getMessage());

                        getResponse.data(false, ignore.getMessage());
                    }
                } else getResponse.data(false, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("orderTest >>>>>>>>>>> failed " + t.getMessage());

                getResponse.data(false, t.getMessage());
            }
        });
    }
}
