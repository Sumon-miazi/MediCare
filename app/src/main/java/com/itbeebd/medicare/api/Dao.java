package com.itbeebd.medicare.api;

import com.itbeebd.medicare.utils.Patient;

import java.util.List;

public class Dao {
    public Dao(){}

    public void savePatientProfile(Patient patient){
        List<Patient> patient1 = null;
        try {
            patient1 = Patient.find(Patient.class,"patient_id = ?", String.valueOf(patient.getPatientId()));
        }
        catch (Exception ignore){
            patient.save();
            return;
        }

        if(patient1 != null){
            patient1.get(0).setName(patient.getName());
            patient1.get(0).setUid(patient.getUid());
            patient1.get(0).setGender(patient.getGender());
            patient1.get(0).setDob(patient.getDob());
            patient1.get(0).setWeight(patient.getWeight());
            patient1.get(0).setBlood_group(patient.getBlood_group());
            patient1.get(0).setIs_blood_donor(patient.getIs_blood_donor());
            patient1.get(0).setAddress(patient.getAddress());
            patient1.get(0).setPhone(patient.getPhone());
            patient1.get(0).setToken(patient.getToken());

            patient1.get(0).save();
        }
        else patient.save();
    }

    private Patient getPatientDetails(int id){
        return Patient.findById(Patient.class, id);
    }
}
