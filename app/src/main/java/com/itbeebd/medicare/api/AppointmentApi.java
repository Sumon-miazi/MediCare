package com.itbeebd.medicare.api;

import android.content.Context;

import com.itbeebd.medicare.api.allInterfaces.GetData;
import com.itbeebd.medicare.api.allInterfaces.GetDataFromApiCall;
import com.itbeebd.medicare.api.allInterfaces.GetResponse;
import com.itbeebd.medicare.utils.Appointment;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.itbeebd.medicare.utils.ReportFile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                            appointment.setAppointment_id(object.getInt("id"));
                            appointment.setDoctor_id(object.getInt("doctor_id"));
                            appointment.setDoctor_chamber_id(object.getInt("doctor_chamber_id"));
                            appointment.setStatus(object.getInt("status"));
                            appointment.setAppointmentDateAndTime(object.getString("appointmentTime"));
                            appointment.setName(doctorObj.getString("name"));
                            appointment.setImage(doctorObj.optString("image").equals("null")? null : doctorObj.optString("image"));
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

    public void getAllAppointment(int id, GetDataFromApiCall<ArrayList<Appointment>> getData) {
        System.out.println("getAllAppointment>>>>>>>>>>> called ");
        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllAppointment(retrofitRequestBody.getDataById(id));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        jsonObject = new JSONObject(response.body().string());
                        System.out.println("getAllAppointment>>>>>>>>>>> " + jsonObject.toString());
                        if (jsonObject.optString("status").equals("true")) {

                            ArrayList<ArrayList<Appointment>> appointments = new ArrayList<>();

                            ArrayList<Appointment> futureAppointments = getAppointmentFromJson(jsonObject.getJSONArray("data"));
                            ArrayList<Appointment> oldAppointments = getAppointmentFromJson(jsonObject.getJSONArray("oldData"));

                            appointments.add(futureAppointments);
                            appointments.add(oldAppointments);

                            getData.data(appointments, jsonObject.optString("message"));

                        } else getData.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getAllAppointment>>>>>>>>>>> catch " + ignore.getMessage());
                        getData.data(null,  ignore.getMessage());
                    }
                } else getData.data(null,  response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("getAllAppointment>>>>>>>>>>> failed " + t.getMessage());
                getData.data(null, t.getMessage());
            }
        });
    }

    private ArrayList<Appointment> getAppointmentFromJson(JSONArray jsonArray){
        try {
            ArrayList<Appointment> appointments = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                JSONObject doctorObj = object.getJSONObject("doctor");
                JSONObject chamber = object.getJSONObject("doctor_chamber");
                JSONObject hospital = chamber.getJSONObject("hospital");

                Appointment appointment = new Appointment();
                appointment.setAppointment_id(object.optInt("id"));
                appointment.setDoctor_id(object.getInt("doctor_id"));
                appointment.setDoctor_chamber_id(object.getInt("doctor_chamber_id"));
                appointment.setStatus(object.getInt("status"));
                appointment.setAppointmentDateAndTime(object.getString("appointmentTime"));
                appointment.setImage(doctorObj.optString("image").equals("null")? null : doctorObj.optString("image"));
                appointment.setName(doctorObj.getString("name"));
                appointment.setDegree(doctorObj.getString("specialist"));
                appointment.setHospitalName(hospital.getString("name"));
                appointment.setAddress(hospital.getString("address"));

                appointments.add(appointment);
            }
            return appointments;
        }
        catch (Exception ignore){
            return  null;
        }
    }

    public void getAnAppointmentReports(int id, GetDataFromApiCall<ReportFile> getData) {
        System.out.println("getAnAppointmentReports>>>>>>>>>>> called ");
        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAnAppointmentReports(retrofitRequestBody.getDataById(id));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        System.out.println("getAnAppointmentReports>>>>>>>>>>> " + jsonObject.toString());
                        if (jsonObject.optString("status").equals("true")) {

                            JSONArray reportJsonObj = jsonObject.getJSONArray("data");
                            ArrayList<ReportFile> reportFiles = new ArrayList<>();
                            for(int i = 0; i < reportJsonObj.length(); i++){

                                JSONObject reportObject = reportJsonObj.getJSONObject(i);
                                ReportFile reportFile = new ReportFile();

                                reportFile.setId(reportObject.getInt("id"));
                                reportFile.setDiagnosticCenterId(reportObject.getInt("diagnostic_center_id"));
                                reportFile.setAppointmentId(reportObject.optInt("appointment_id"));
                                reportFile.setComplete(reportObject.getInt("complete") == 1);

                                JSONArray jsonArray = reportObject.getJSONArray("file");

                                ArrayList<Map<String, String>> files = new ArrayList<>();

                                for(int j = 0; j < jsonArray.length(); j++){
                                    JSONObject object = jsonArray.getJSONObject(j);
                                    Map<String, String> file = new HashMap<>();
                                    file.put("title", object.getString("original_name"));
                                    file.put("file", object.getString("download_link"));

                                    files.add(file);
                                }

                                reportFile.setFiles(files);


                                JSONObject diagnosticCenterObj = reportObject.getJSONObject("diagnostic_center");

                                DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
                                diagnosticCenter.setName(diagnosticCenterObj.getString("name"));
                                diagnosticCenter.setImage(diagnosticCenterObj.optString("image").equals("null")? null : diagnosticCenterObj.optString("image"));
                                diagnosticCenter.setAddress(diagnosticCenterObj.getString("address"));
                                diagnosticCenter.setEmail(diagnosticCenterObj.getString("email"));
                                diagnosticCenter.setPhone(diagnosticCenterObj.getString("phone"));
                                diagnosticCenter.setLat(diagnosticCenterObj.getDouble("latitude"));
                                diagnosticCenter.setLon(diagnosticCenterObj.getDouble("longitude"));
                                diagnosticCenter.setUid(diagnosticCenterObj.getString("uid"));
                                diagnosticCenter.setDiagnosticId(diagnosticCenterObj.getInt("id"));

                                ArrayList<String> services = new ArrayList<>();
                                JSONArray serviceArray = diagnosticCenterObj.getJSONArray("services");
                                for(int j = 0; j < serviceArray.length(); j++){
                                    services.add(serviceArray.getJSONObject(j).getString("name"));
                                }
                                diagnosticCenter.setServices(services);
                                reportFile.setDiagnosticCenter(diagnosticCenter);

                                reportFiles.add(reportFile);
                            }
                            getData.data(reportFiles, jsonObject.optString("message"));

                        } else getData.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getAnAppointmentReports>>>>>>>>>>> catch " + ignore.getMessage());
                        getData.data(null,  ignore.getMessage());
                    }
                } else getData.data(null,  response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("getAnAppointmentReports>>>>>>>>>>> failed " + t.getMessage());
                getData.data(null, t.getMessage());
            }
        });
    }

    public void getAllReports(int id, GetDataFromApiCall<ReportFile> getData) {
        System.out.println("getAllReports>>>>>>>>>>> called ");
        final RetrofitRequestBody retrofitRequestBody = new RetrofitRequestBody();
        Call<ResponseBody> responseBodyCall = service.getAllReports(retrofitRequestBody.getDataById(id));
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject jsonObject = null;
                if (response.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        System.out.println("getAllReports>>>>>>>>>>> " + jsonObject.toString());
                        if (jsonObject.optString("status").equals("true")) {

                            JSONArray reportJsonObj = jsonObject.getJSONArray("data");
                            ArrayList<ReportFile> reportFiles = new ArrayList<>();
                            for(int i = 0; i < reportJsonObj.length(); i++){

                                JSONObject reportObject = reportJsonObj.getJSONObject(i);
                                ReportFile reportFile = new ReportFile();

                                reportFile.setId(reportObject.getInt("id"));
                                reportFile.setDiagnosticCenterId(reportObject.getInt("diagnostic_center_id"));
                                reportFile.setAppointmentId(reportObject.optInt("appointment_id"));
                                reportFile.setComplete(reportObject.getInt("complete") == 1);

                                JSONArray jsonArray = reportObject.getJSONArray("file");

                                ArrayList<Map<String, String>> files = new ArrayList<>();

                                for(int j = 0; j < jsonArray.length(); j++){
                                    JSONObject object = jsonArray.getJSONObject(j);
                                    Map<String, String> file = new HashMap<>();
                                    file.put("title", object.getString("original_name"));
                                    file.put("file", object.getString("download_link"));

                                    files.add(file);
                                }

                                reportFile.setFiles(files);


                                JSONObject diagnosticCenterObj = reportObject.getJSONObject("diagnostic_center");

                                DiagnosticCenter diagnosticCenter = new DiagnosticCenter();
                                diagnosticCenter.setName(diagnosticCenterObj.getString("name"));
                                diagnosticCenter.setImage(diagnosticCenterObj.optString("image").equals("null")? null : diagnosticCenterObj.optString("image"));
                                diagnosticCenter.setAddress(diagnosticCenterObj.getString("address"));
                                diagnosticCenter.setEmail(diagnosticCenterObj.getString("email"));
                                diagnosticCenter.setPhone(diagnosticCenterObj.getString("phone"));
                                diagnosticCenter.setLat(diagnosticCenterObj.getDouble("latitude"));
                                diagnosticCenter.setLon(diagnosticCenterObj.getDouble("longitude"));
                                diagnosticCenter.setUid(diagnosticCenterObj.getString("uid"));
                                diagnosticCenter.setDiagnosticId(diagnosticCenterObj.getInt("id"));

                                ArrayList<String> services = new ArrayList<>();
                                JSONArray serviceArray = diagnosticCenterObj.getJSONArray("services");
                                for(int j = 0; j < serviceArray.length(); j++){
                                    services.add(serviceArray.getJSONObject(j).getString("name"));
                                }
                                diagnosticCenter.setServices(services);
                                reportFile.setDiagnosticCenter(diagnosticCenter);

                                reportFiles.add(reportFile);
                            }
                            getData.data(reportFiles, jsonObject.optString("message"));

                        } else getData.data(null, jsonObject.optString("message"));

                    } catch (Exception ignore) {
                        System.out.println("getAllReports>>>>>>>>>>> catch " + ignore.getMessage());
                        getData.data(null,  ignore.getMessage());
                    }
                } else getData.data(null,  response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("getAllReports>>>>>>>>>>> failed " + t.getMessage());
                getData.data(null, t.getMessage());
            }
        });
    }
}
