package com.itbeebd.medicare.db;

import com.itbeebd.medicare.utils.Medication;
import com.itbeebd.medicare.utils.MedicationDate;
import com.itbeebd.medicare.utils.Patient;

import java.util.ArrayList;
import java.util.List;

public class Dao {
    public Dao(){}

    public void savePatientProfile(Patient patient){
        List<Patient> patient1 = null;
        try {
            System.out.println(">>>>>>>>. sugar = " + patient.getIs_blood_donor());
            patient1 = Patient.find(Patient.class,"PATIENTID = ?", String.valueOf(patient.getPatientId()));
        }
        catch (Exception exception){
            System.out.println(">>>>>>>>. sugar = " + exception.getMessage());
            patient.save();
            return;
        }

        if(patient1 != null && patient1.size() > 0){
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

    public Patient getPatientDetails(int id){
        try {
            List<Patient> patients = Patient.find(Patient.class,"PATIENTID = ?", String.valueOf(id));
            return patients != null? patients.get(0) : null;
        }
        catch (Exception exception){
            return null;
        }
    }

    public ArrayList<Medication> getAllMedicationByDate(String date){
        ArrayList<Medication> medications = new ArrayList<>();

        try {
            List<MedicationDate> medicationDateList = new ArrayList<>();
            medicationDateList = MedicationDate.find(MedicationDate.class,"DATE = ?", date);

            if(medicationDateList != null){
                for(int i = 0; i < medicationDateList.size(); i++){
                    medications.add(medicationDateList.get(i).getMedication());
                }
            }
        }
        catch (Exception ignore){ }

        return medications;
    }
}
