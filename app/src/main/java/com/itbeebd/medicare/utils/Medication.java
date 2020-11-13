package com.itbeebd.medicare.utils;

import com.orm.SugarRecord;

public class Medication extends SugarRecord {
    private String medicineName;
    private String medicineType;
    private String time;
    private String note;
    private String medicationAmount;

    public Medication(){

    }

    public Medication(String medicineName, String medicineType, String time, String note, String medicationAmount) {
        this.medicineName = medicineName;
        this.medicineType = medicineType;
        this.time = time;
        this.note = note;
        this.medicationAmount = medicationAmount;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMedicationAmount() {
        return medicationAmount;
    }

    public void setMedicationAmount(String medicationAmount) {
        this.medicationAmount = medicationAmount;
    }
}
