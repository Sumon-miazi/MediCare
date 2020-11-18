package com.itbeebd.medicare.db;

import com.itbeebd.medicare.utils.BloodBank;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.itbeebd.medicare.utils.Doctor;
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


    public void saveBloodBankProfile(BloodBank bloodBank){
        List<BloodBank> bloodBanks = null;
        try {
            bloodBanks = BloodBank.find(BloodBank.class, "BLOODBANKID = ?", String.valueOf(bloodBank.getBloodBankId()));
        }
        catch (Exception exception){
            System.out.println(">>>>>>>>. saveBloodBankProfile = " + exception.getMessage());
            bloodBank.save();
            return;
        }

        if(bloodBanks != null && bloodBanks.size() > 0){
            bloodBanks.get(0).setName(bloodBank.getName());
            bloodBanks.get(0).setAddress(bloodBank.getAddress());
            bloodBanks.get(0).setPhone(bloodBank.getPhone());
            bloodBanks.get(0).setAbout(bloodBank.getAbout());
            bloodBanks.get(0).save();
        }
        else bloodBank.save();
    }

    public void saveDoctorProfile(Doctor doctor){
        List<Doctor> doctors = null;
        try {
            doctors = Doctor.find(Doctor.class,"DOCTORID = ?", String.valueOf(doctor.getDoctorId()));
        }
        catch (Exception exception){
            System.out.println(">>>>>>>>. saveDoctorProfile = " + exception.getMessage());
            doctor.save();
            return;
        }

        if(doctors != null && doctors.size() > 0){
            doctors.get(0).setName(doctor.getName());
            doctors.get(0).setGender(doctor.getGender());
            doctors.get(0).setAddress(doctor.getAddress());
            doctors.get(0).setAbout(doctor.getAbout());
            doctors.get(0).setBmdcRegNo(doctor.getBmdcRegNo());
            doctors.get(0).setEmail(doctor.getEmail());
            doctors.get(0).setPhone(doctor.getPhone());
            doctors.get(0).setEducationHistory(doctor.getEducationHistory());
            doctors.get(0).setSpecialist(doctor.getSpecialist());
            doctors.get(0).setDoctor_id(doctor.getDoctorId());

            doctors.get(0).save();

        }
        else doctor.save();
    }

    public void saveDiagnosticCenterProfile(DiagnosticCenter diagnosticCenter){
        List<DiagnosticCenter> diagnosticCenters = null;
        try {
            diagnosticCenters = DiagnosticCenter.find(DiagnosticCenter.class,"DIAGNOSTICID = ?", String.valueOf(diagnosticCenter.getDiagnosticId()));
        }
        catch (Exception exception){
            System.out.println(">>>>>>>>. saveDiagnosticCenterProfile = " + exception.getMessage());
            diagnosticCenter.save();
            return;
        }

        if(diagnosticCenters != null && diagnosticCenters.size() > 0){
            diagnosticCenters.get(0).setName(diagnosticCenter.getName());
            diagnosticCenters.get(0).setServices(diagnosticCenter.getServices());
            diagnosticCenters.get(0).setAddress(diagnosticCenter.getAddress());
            diagnosticCenters.get(0).setEmail(diagnosticCenter.getEmail());
            diagnosticCenters.get(0).setPhone(diagnosticCenter.getPhone());
            diagnosticCenters.get(0).setLat(diagnosticCenter.getLat());
            diagnosticCenters.get(0).setLon(diagnosticCenter.getLon());

            diagnosticCenters.get(0).save();

        }
        else diagnosticCenter.save();
    }


}
