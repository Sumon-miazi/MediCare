package com.itbeebd.medicare.utils;

import java.util.ArrayList;
import java.util.Map;

public class ReportFile {
    private int id;
    private int diagnosticCenterId;
    private int appointmentId;
    private ArrayList<Map<String, String>> files;
    private boolean isComplete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiagnosticCenterId() {
        return diagnosticCenterId;
    }

    public void setDiagnosticCenterId(int diagnosticCenterId) {
        this.diagnosticCenterId = diagnosticCenterId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public ArrayList<Map<String, String>> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<Map<String, String>> files) {
        this.files = files;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
