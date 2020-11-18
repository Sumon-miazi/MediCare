package com.itbeebd.medicare.api.allInterfaces;

import com.itbeebd.medicare.utils.BloodBank;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.itbeebd.medicare.utils.Doctor;
import com.itbeebd.medicare.utils.Patient;

public interface GetUserDataAndType {
    void data(Patient patient, Doctor doctor, BloodBank bloodBank, DiagnosticCenter diagnosticCenter, String message, String userType);
}
