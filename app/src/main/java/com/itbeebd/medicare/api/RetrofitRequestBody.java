package com.itbeebd.medicare.api;

import com.itbeebd.medicare.utils.Appointment;
import com.itbeebd.medicare.utils.BloodRequest;
import com.itbeebd.medicare.utils.Patient;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RetrofitRequestBody {
    private final String api_key = "jVO6EI4kLdaZ6EIXnfJnV54XJaZ6VOT";

    public RetrofitRequestBody() {

    }

    Map<String, Object> getApiKey() {
        Map<String, Object> map = new HashMap<>();
        map.put("api_key", this.api_key);
        return map;
    }


    Map<String, Object> getAllDoctorByHospitalId(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("hospital_id", id);
        map.put("api_key", this.api_key);
        return map;
    }

    Map<String, Object> getAllDoctorChambersByDoctorId(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("doctor_id", id);
        map.put("api_key", this.api_key);
        return map;
    }

    Map<String, Object> signUpPatient(Patient patient, Date lastBloodDonationDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", patient.getName());
        map.put("uid", patient.getUid());
        map.put("gender", patient.getGender());
        map.put("is_blood_donor", patient.getIs_blood_donor());
        map.put("lastDonate", lastBloodDonationDate);
        map.put("dob", patient.getDob());
        map.put("weight", patient.getWeight());
        map.put("blood_group", patient.getBlood_group());
        map.put("address", patient.getAddress());
        map.put("phone", patient.getPhone());
        map.put("token", patient.getToken());
        map.put("api_key", this.api_key);
        return map;
    }


    Map<String, Object> getPatientDetails(String uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("api_key", this.api_key);
        return map;
    }


    Map<String, Object> bookNewAppointment(Appointment appointment) {
        Map<String, Object> map = new HashMap<>();
        map.put("patient_id", appointment.getPatient_id());
        map.put("doctor_id", appointment.getDoctor_id());
        map.put("doctor_chamber_id", appointment.getDoctor_chamber_id());
        map.put("hospital_id", appointment.getHospital_id());
        map.put("year", appointment.getYear());
        map.put("month", appointment.getMonth());
        map.put("day", appointment.getDay());
        map.put("hour", appointment.getHour());
        map.put("minute", appointment.getMinute());
        map.put("status", appointment.getStatus());
        map.put("api_key", this.api_key);
        return map;
    }

    Map<String, Object> addBloodDonor(int id, Date lastDonate, boolean currentlyAvailable) {
        Map<String, Object> map = new HashMap<>();
        map.put("patient_id", id);
        map.put("lastDonate", lastDonate);
        map.put("currentlyAvailable", currentlyAvailable);
        map.put("api_key", this.api_key);
        return map;
    }

    Map<String, Object> getBloodDonor(String bloodGroup) {
        Map<String, Object> map = new HashMap<>();
        map.put("bloodGroup", bloodGroup);
        map.put("api_key", this.api_key);
        return map;
    }

    Map<String, Object> addNewBloodRequest(BloodRequest bloodRequest) {
        Map<String, Object> map = new HashMap<>();
        map.put("patient_id", bloodRequest.getUserId());
        map.put("bloodFor", bloodRequest.getBloodFor());
        map.put("city", bloodRequest.getCity());
        map.put("hospital", bloodRequest.getHospital());
        map.put("amount", bloodRequest.getAmount());
        map.put("date", bloodRequest.getBloodNeededDateTime());
        map.put("bloodGroup", bloodRequest.getBloodGroup());
        map.put("api_key", this.api_key);
        return map;
    }

    String getApi_key() {
        return this.api_key;
    }
}
