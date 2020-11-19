package com.itbeebd.medicare.utils;

public class TestReport {
    private int id;
    private int diagnostic_center_id;
    private int appointment_id;
    private int patient_id;
    private String title;
    private String file;
    private boolean complete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiagnostic_center_id() {
        return diagnostic_center_id;
    }

    public void setDiagnostic_center_id(int diagnostic_center_id) {
        this.diagnostic_center_id = diagnostic_center_id;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}