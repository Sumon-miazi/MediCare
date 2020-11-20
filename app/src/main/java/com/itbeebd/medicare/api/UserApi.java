package com.itbeebd.medicare.api;

import android.content.Context;

import com.itbeebd.medicare.api.allInterfaces.GetData;
import com.itbeebd.medicare.api.allInterfaces.GetPatientInfo;
import com.itbeebd.medicare.api.allInterfaces.GetResponse;
import com.itbeebd.medicare.api.allInterfaces.GetUserDataAndType;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.db.Dao;
import com.itbeebd.medicare.utils.Appointment;
import com.itbeebd.medicare.utils.BloodBank;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.itbeebd.medicare.utils.Doctor;
import com.itbeebd.medicare.utils.Patient;

import org.json.JSONObject;

import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserApi extends BaseService{
    private Context context;

    public UserApi(){}

    public UserApi(Context context) {
        this.context = context;
    }

    public void signUpPatient(Patient patient,
                              Date lastBloodDonationDate,
                              GetPatientInfo getPatientInfo) {

        System.out.println("signUpPatient>>>>>>>>>>> called ");

        Call<ResponseBody> responseBodyCall = service.signUpPatient(
                getImageFile(patient.getImage()),
                patient.getUid(),
                patient.getName(),
                patient.getGender(),
                patient.getIs_blood_donor(),
                lastBloodDonationDate,
                patient.getDob(),
                patient.getWeight(),
                patient.getBlood_group(),
                patient.getAddress(),
                patient.getPhone(),
                patient.getToken(),
                new RetrofitRequestBody().getApi_key()
        );
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
                            patientInfo.setImage(userObj.optString("image"));

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
                                    patient.setImage(userObj.optString("image"));
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
                                    bloodBank.setImage(userObj.optString("image"));

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
                            patient.setImage(userObj.optString("image"));
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

    public void getNextAppointment(int id, GetData<Appointment> getData) {
        System.out.println("getNextAppointment>>>>>>>>>>> called ");
        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getNextAppointment(retrofitRequestBody.getDataById(id));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        System.out.println("getNextAppointment>>>>>>>>>>> " + jsonObject.toString());
                        if (jsonObject.optString("status").equals("true")) {

                            JSONObject object = jsonObject.getJSONObject("data");
                            JSONObject doctorObj = object.getJSONObject("doctor");
                            JSONObject chamber = object.getJSONObject("doctor_chamber");
                            JSONObject hospital = chamber.getJSONObject("hospital");

                            Appointment appointment = new Appointment();
                            appointment.setDoctor_id(object.getInt("doctor_id"));
                            appointment.setDoctor_chamber_id(object.getInt("doctor_chamber_id"));
                            appointment.setStatus(object.getInt("status"));
                            appointment.setAppointmentDateAndTime(object.getString("appointmentTime"));
                            appointment.setName(doctorObj.getString("name"));
                            appointment.setImage(doctorObj.getString("image"));
                            appointment.setDegree(doctorObj.getString("specialist"));
                            appointment.setHospitalName(hospital.getString("name"));
                            appointment.setAddress(hospital.getString("address"));

                            getData.data(appointment, jsonObject.optString("message"));

                        } else getData.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getNextAppointment>>>>>>>>>>> catch " + ignore.getMessage());
                        getData.data(null,  ignore.getMessage());
                    }
                } else getData.data(null,  response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("getNextAppointment>>>>>>>>>>> failed " + t.getMessage());
                getData.data(null, t.getMessage());
            }
        });
    }
}
