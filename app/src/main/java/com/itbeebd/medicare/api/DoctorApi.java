package com.itbeebd.medicare.api;

import android.content.Context;

import com.itbeebd.medicare.api.allInterfaces.GetData;
import com.itbeebd.medicare.api.allInterfaces.GetDataFromApiCall;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.db.Dao;
import com.itbeebd.medicare.utils.CustomDayOfWeek;
import com.itbeebd.medicare.utils.Doctor;
import com.itbeebd.medicare.utils.DoctorChamber;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorApi extends BaseService {

    private Context context;

    public DoctorApi(){

    }
    public DoctorApi(Context context) {
        this.context = context;
    }

    public void signUpDoctor(Doctor doctor, GetData<Doctor> getData) {

        System.out.println("signUpDoctor>>>>>>>>>>> called ");

        Call<ResponseBody> responseBodyCall = service.signUpDoctor(
                getImageFile(doctor.getImage()),
                doctor.getUid(),
                doctor.getName(),
                doctor.getBmdcRegNo(),
                doctor.getSpecialist(),
                doctor.getGender(),
                doctor.getAddress(),
                doctor.getAbout(),
                doctor.getEducationHistory(),
                doctor.getPhone(),
                doctor.getEmail(),
                doctor.getToken(),
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

                            Doctor doc = new Doctor();
                            doc.setDoctor_id(userObj.getInt("id"));
                            doc.setUid(userObj.getString("uid"));
                            doc.setAbout(userObj.getString("about"));
                            doc.setAddress(userObj.getString("address"));
                            doc.setPhone(userObj.getString("phone"));
                            doc.setGender(userObj.getString("gender"));
                            doc.setEmail(userObj.getString("email"));
                            doc.setEducationHistory(userObj.getString("educationHistory"));
                            doc.setSpecialist(userObj.getString("specialist"));
                            doc.setBmdcRegNo(userObj.getString("bmdcRegNo"));
                            doc.setImage(userObj.optString("image"));

                            CustomSharedPref.getInstance(context).setUserId(doc.getDoctorId());

                            new Dao().saveDoctorProfile(doc);

                            getData.data(doc, jsonObject.optString("message"));

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

    public void getDoctorData(String uid, GetData<Doctor> getData) {
        System.out.println("getDoctorData>>>>>>>>>>> called ");
        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getDoctorData(retrofitRequestBody.getPatientDetails(uid));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        System.out.println("getDoctorData>>>>>>>>>>> " + jsonObject.toString());
                        if (jsonObject.optString("status").equals("true")) {
                            JSONObject userObj = jsonObject.getJSONObject("data");

                            Doctor doctor = new Doctor();
                            doctor.setDoctor_id(userObj.getInt("id"));
                            doctor.setUid(userObj.getString("uid"));
                            doctor.setName(userObj.getString("name"));
                            doctor.setAbout(userObj.getString("about"));
                            doctor.setAddress(userObj.getString("address"));
                            doctor.setPhone(userObj.getString("phone"));
                            doctor.setBmdcRegNo(userObj.getString("bmdcRegNo"));
                            doctor.setSpecialist(userObj.getString("specialist"));
                            doctor.setEducationHistory(userObj.getString("educationHistory"));
                            doctor.setGender(userObj.getString("gender"));
                            doctor.setImage(userObj.optString("image"));

                            new Dao().saveDoctorProfile(doctor);

                            CustomSharedPref.getInstance(context).setUserId(doctor.getDoctorId());

                            getData.data(doctor,jsonObject.optString("message"));

                        } else getData.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getDoctorData>>>>>>>>>>> catch " + ignore.getMessage());
                        getData.data(null,  ignore.getMessage());
                    }
                } else getData.data(null,  response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("getDoctorData>>>>>>>>>>> failed " + t.getMessage());
                getData.data(null, t.getMessage());
            }
        });
    }

    public void getAllDoctorByHospitalId(int hospitalId, final GetDataFromApiCall<Doctor> getAllDoctor) {

        System.out.println("getAllDoctorByHospitalId>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllDoctorByHospitalId(retrofitRequestBody.getDataById(hospitalId));
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
                                        object.getString("educationHistory"),
                                        object.getString("address"),
                                        object.getString("phone"));
                                doctor.setImage(object.optString("image"));
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

    public void getAllDoctorBySpecialistId(int specialistId, final GetDataFromApiCall<Doctor> getAllDoctor) {

        System.out.println("getAllDoctorBySpecialistId>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllDoctorBySpecialistId(retrofitRequestBody.getDataById(specialistId));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("getAllDoctorBySpecialistId>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            JSONArray doctorJsonArray = jsonObject.getJSONArray("data");

                            ArrayList<Doctor> doctorArrayList = new ArrayList<>();

                            for (int i = 0; i < doctorJsonArray.length(); i++) {

                                JSONObject object = doctorJsonArray.getJSONObject(i);

                                Doctor doctor = new Doctor(object.getInt("id"),
                                        object.optInt("hospital_id"),
                                        object.getString("name"),
                                        object.optString("dob"),
                                        object.getString("educationHistory"),
                                        object.getString("address"),
                                        object.getString("phone"));
                                doctor.setImage(object.optString("image"));
                                doctorArrayList.add(doctor);
                            }

                            // Collections.shuffle(questionArrayList);

                            getAllDoctor.data(doctorArrayList, jsonObject.optString("message"));
                        } else getAllDoctor.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getAllDoctorBySpecialistId>>>>>>>>>>> catch " + ignore.getMessage());

                        getAllDoctor.data(null, ignore.getMessage());
                    }
                } else getAllDoctor.data(null, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("getAllDoctorBySpecialistId>>>>>>>>>>> failed " + t.getMessage());

                getAllDoctor.data(null, t.getMessage());
            }
        });
    }

    public void getAllDoctorChambersByDoctorId(int doctorId, final GetDataFromApiCall<DoctorChamber> getAllDoctorChamber) {

        System.out.println("getAllDoctorChambersByDoctorId>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllDoctorChambersByDoctorId(retrofitRequestBody.getDataById(doctorId));
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

                                doctorChamber.setImage(object.optString("image"));

                                JSONArray availableDays = object.getJSONArray("available_days");
                                ArrayList<CustomDayOfWeek> customDayOfWeekArrayList = new ArrayList<>();

                                for (int j = 0; j < availableDays.length(); j++) {
                                    JSONObject dayObj = availableDays.getJSONObject(j);
                                    ArrayList<String> times = new ArrayList<>();

                                    JSONArray timesList = dayObj.getJSONArray("available_times");
                                    System.out.println(">>>>>>>> times = " + timesList);

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

}
