package com.itbeebd.medicare.utils;

import com.orm.SugarRecord;

public class MedicationDate extends SugarRecord {

    private String date;
    private Medication medication;

    public MedicationDate() {
    }

    public MedicationDate(String date, Medication medication) {
        this.date = date;
        this.medication = medication;
    }

    public String getDate() {
        return date;
    }

    public Medication getMedication() {
        return medication;
    }
}
