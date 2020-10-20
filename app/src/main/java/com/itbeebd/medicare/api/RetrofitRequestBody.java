package com.itbeebd.medicare.api;

import java.util.HashMap;
import java.util.Map;

public class RetrofitRequestBody {
    private String api_key = "jVO6EI4kLdaZ6EIXnfJnV54XJaZ6VOT";

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


    String getApi_key() {
        return this.api_key;
    }
}
