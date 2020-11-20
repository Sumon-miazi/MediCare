package com.itbeebd.medicare.api;

import android.content.Context;

import com.itbeebd.medicare.api.allInterfaces.GetData;
import com.itbeebd.medicare.api.allInterfaces.GetResponse;
import com.itbeebd.medicare.utils.Appointment;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentApi extends BaseService{
    private Context context;

    public AppointmentApi(){}

    public AppointmentApi(Context context) {
        this.context = context;
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
