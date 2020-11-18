package com.itbeebd.medicare.api;

import com.itbeebd.medicare.utils.Appointment;
import com.itbeebd.medicare.utils.BloodBank;
import com.itbeebd.medicare.utils.BloodRequest;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.itbeebd.medicare.utils.Doctor;
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

    Map<String, Object> signUpBloodBank(BloodBank bloodBank) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", bloodBank.getUid());
        map.put("name", bloodBank.getName());
        map.put("about", bloodBank.getAbout());
        map.put("address", bloodBank.getAddress());
        map.put("phone", bloodBank.getPhone());
        map.put("email", bloodBank.getEmail());
        map.put("token", bloodBank.getToken());
        map.put("lat", bloodBank.getLat());
        map.put("long", bloodBank.getLon());
        map.put("api_key", this.api_key);
        return map;
    }


    Map<String, Object> signUpDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", diagnosticCenter.getUid());
        map.put("name", diagnosticCenter.getName());
        map.put("services", diagnosticCenter.getServices());
        map.put("address", diagnosticCenter.getAddress());
        map.put("phone", diagnosticCenter.getPhone());
        map.put("email", diagnosticCenter.getEmail());
        map.put("token", diagnosticCenter.getToken());
        map.put("lat", diagnosticCenter.getLat());
        map.put("long", diagnosticCenter.getLon());
        map.put("api_key", this.api_key);
        return map;
    }

    Map<String, Object> getAllBloodRequestOfABloodBankById(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("api_key", this.api_key);
        return map;
    }


    Map<String, Object> signUpDoctor(Doctor doctor) {
        Map<String, Object> map = new HashMap<>();
        map.put("uid", doctor.getUid());
        map.put("name", doctor.getName());
        map.put("bmdcRegNo", doctor.getBmdcRegNo());
        map.put("specialist", doctor.getSpecialist());
        map.put("gender", doctor.getGender());
        map.put("about", doctor.getAbout());
        map.put("educationHistory", doctor.getEducationHistory());
        map.put("address", doctor.getAddress());
        map.put("email", doctor.getEmail());
        map.put("phone", doctor.getPhone());
        map.put("token", doctor.getToken());
        map.put("api_key", this.api_key);
        return map;
    }
    String getApi_key() {
        return this.api_key;
    }
}
