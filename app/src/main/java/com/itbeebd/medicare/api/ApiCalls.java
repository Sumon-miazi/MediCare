package com.itbeebd.medicare.api;

import android.content.Context;

import com.itbeebd.medicare.api.allInterfaces.GetData;
import com.itbeebd.medicare.api.allInterfaces.GetDataFromApiCall;
import com.itbeebd.medicare.api.allInterfaces.GetPatientInfo;
import com.itbeebd.medicare.api.allInterfaces.GetResponse;
import com.itbeebd.medicare.api.allInterfaces.GetUserDataAndType;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.db.Dao;
import com.itbeebd.medicare.utils.Appointment;
import com.itbeebd.medicare.utils.BloodBank;
import com.itbeebd.medicare.utils.BloodDonationRequest;
import com.itbeebd.medicare.utils.BloodDonor;
import com.itbeebd.medicare.utils.BloodRequest;
import com.itbeebd.medicare.utils.CustomDayOfWeek;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.itbeebd.medicare.utils.Doctor;
import com.itbeebd.medicare.utils.DoctorChamber;
import com.itbeebd.medicare.utils.Hospital;
import com.itbeebd.medicare.utils.Patient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCalls {

    private Context context;

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiUrls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private final RetrofitService service = retrofit.create(RetrofitService.class);

    public ApiCalls(){}

    public ApiCalls(Context context){ this.context = context;}

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



    public void signUpPatient(Patient patient,
                              Date lastBloodDonationDate,
                              GetPatientInfo getPatientInfo) {

        System.out.println("signUpPatient>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.signUpPatient(retrofitRequestBody.signUpPatient(patient, lastBloodDonationDate));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("signUpPatient>>>>>>>>>>> " + jsonObject.toString());

                        if (jsonObject.optString("status").equals("true")) {
                            JSONObject userObj = jsonObject.getJSONObject("data");

                            userObj.optString("dob");
                            Patient patientInfo = new Patient(
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
                            CustomSharedPref.getInstance(context).setUserId(patientInfo.getPatientId());
                            new Dao().savePatientProfile(patientInfo);
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

    public void checkUserExistOrNot(String uid, GetUserDataAndType getUserDataAndType) {
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
                        System.out.println("checkUserExistOrNot>>>>>>>>>>> json= " + jsonObject.toString());
                        if (jsonObject.optString("status").equals("true")) {
                            JSONObject userObj = jsonObject.getJSONObject("data");

                            String userType = jsonObject.optString("userType");
                            System.out.println(">>>>>>>>> " + userType);
                            Patient patient = null;
                            Doctor doctor = null;
                            BloodBank bloodBank = null;
                            DiagnosticCenter diagnosticCenter = null;

                            switch (userType) {
                                case "patient":
                                    patient = new Patient(
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
                                    new Dao().savePatientProfile(patient);
                                    CustomSharedPref.getInstance(context).setUserId(patient.getPatientId());
                                    break;
                                case "doctor":

                                    break;
                                case "bloodBank":
                                    bloodBank = new BloodBank(
                                            userObj.getString("name"),
                                            userObj.getString("address"),
                                            userObj.getString("about"),
                                            userObj.getString("phone"),
                                            userObj.getString("email")
                                    );

                                    bloodBank.setBloodBankId(userObj.getInt("id"));
                                    CustomSharedPref.getInstance(context).setUserId(bloodBank.getBloodBankId());
                                    new Dao().saveBloodBankProfile(bloodBank);
                                    break;
                                case "diagnosticCenter":

                                    break;
                            }

                            getUserDataAndType.data(patient, doctor, bloodBank, diagnosticCenter, jsonObject.optString("message"), userType);

                        } else getUserDataAndType.data(null, null, null, null, jsonObject.optString("message"), null);

                    } catch (Exception ignore) {
                        System.out.println("checkUserExistOrNot>>>>>>>>>>> catch " + ignore.getMessage());
                        getUserDataAndType.data(null, null,  null, null, ignore.getMessage(), null);
                    }
                } else {
                    System.out.println("checkUserExistOrNot>>>>>>>>>>> res " + response.body());
                    getUserDataAndType.data(null,null, null, null,  response.message(), null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("checkUserExistOrNot>>>>>>>>>>> fail " + t.getMessage());
                getUserDataAndType.data(null, null, null, null,t.getMessage(), null);
            }
        });
    }

    public void getUserData(String uid, GetData<Patient> getData) {
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
                            Patient patient = patient = new Patient(
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
                                new Dao().savePatientProfile(patient);

                            CustomSharedPref.getInstance(context).setUserId(patient.getPatientId());

                            getData.data(patient,jsonObject.optString("message"));

                        } else getData.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getUserData>>>>>>>>>>> catch " + ignore.getMessage());
                        getData.data(null,  ignore.getMessage());
                    }
                } else getData.data(null,  response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("getUserData>>>>>>>>>>> failed " + t.getMessage());
                getData.data(null, t.getMessage());
            }
        });
    }

    public void getDoctorData(String uid, GetData<Doctor> getData) {
        System.out.println("getDoctorData>>>>>>>>>>> called ");
        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getPatientDetails(retrofitRequestBody.getPatientDetails(uid));
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
                            doctor.setHospital_id(userObj.getInt("hospital_id"));
                            doctor.setName(userObj.getString("name"));
                            doctor.setAbout(userObj.getString("about"));
                            doctor.setAddress(userObj.getString("address"));
                            doctor.setPhone(userObj.getString("phone"));
                            doctor.setToken(userObj.getString("token"));
                            doctor.setBmdcRegNo(userObj.getString("bmdcRegNo"));
                            doctor.setSpecialist(userObj.getString("specialist"));
                            doctor.setEducationHistory(userObj.getString("educationHistory"));
                            doctor.setGender(userObj.getString("gender"));

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


    public void bookNewAppointment(Appointment appointment, GetResponse getResponse) {

        System.out.println("bookNewAppointment >>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.bookNewAppointment(retrofitRequestBody.bookNewAppointment(appointment));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());

                        System.out.println("bookNewAppointment >>>>>>>>>>> " + jsonObject.toString());

                        getResponse.data(jsonObject.optString("status").equals("true"), jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("bookNewAppointment >>>>>>>>>>> catch " + ignore.getMessage());

                        getResponse.data(false, ignore.getMessage());
                    }
                } else getResponse.data(false, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                System.out.println("bookNewAppointment >>>>>>>>>>> failed " + t.getMessage());

                getResponse.data(false, t.getMessage());
            }
        });
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
/* "data":[
{"id":2,
"patient_id":2,
"lastDonate":"2020-12-19T00:00:00.000000Z",
"currentlyAvailable":1,

"patient":{"id":2,
"uid":"Knv98LIRMPfPpsfnJpAfagBeffw1",
"name":"Sumon",
"gender":"male",
"is_blood_donor":1,
"dob":null,
"weight":0,
"blood_group":"O+",
"address":"Cumilla",
"phone":"+8801311200000",
"token":'"}}]
int id, String name, String lastDonateDate,  String bloodGroup, String address, String phone*/

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
                                        object.getDouble("lat"),
                                        object.getDouble("lon"));

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



    public void signUpBloodBank(BloodBank bloodBank, GetData<BloodBank> getData) {

        System.out.println("signUpBloodBank>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.signUpBloodBank(retrofitRequestBody.signUpBloodBank(bloodBank));
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

    public void getAllBloodRequestOfABloodBankById(int id,final GetDataFromApiCall<BloodRequest> getDataFromApiCall) {

        System.out.println("getAllBloodRequestOfABloodBankById>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllBloodRequestOfABloodBankById(retrofitRequestBody.getAllBloodRequestOfABloodBankById(id));
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


    public void signUpDoctor(Doctor doctor, GetData<Doctor> getData) {

        System.out.println("signUpDoctor>>>>>>>>>>> called ");

        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.signUpDoctor(retrofitRequestBody.signUpDoctor(doctor));
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
}
