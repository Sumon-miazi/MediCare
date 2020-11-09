package com.itbeebd.medicare.api;

import com.itbeebd.medicare.utils.Patient;

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

    Map<String, Object> signUpPatient(Patient patient) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", patient.getName());
        map.put("uid", patient.getUid());
        map.put("api_key", this.api_key);
        return map;
    }

    Map<String, Object> getPatientDetails(String uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("api_key", this.api_key);
        return map;
    }

    String getApi_key() {
        return this.api_key;
    }
}
