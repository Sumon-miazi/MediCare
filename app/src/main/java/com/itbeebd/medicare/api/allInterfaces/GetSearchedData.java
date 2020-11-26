package com.itbeebd.medicare.api.allInterfaces;

import com.itbeebd.medicare.utils.BloodBank;
import com.itbeebd.medicare.utils.DiagnosticCenter;
import com.itbeebd.medicare.utils.Hospital;
import com.itbeebd.medicare.utils.Pharmacy;

import java.util.ArrayList;

public interface GetSearchedData {
    void data(ArrayList<Hospital> hospitals, ArrayList<DiagnosticCenter> diagnosticCenters, ArrayList<Pharmacy> pharmacies, ArrayList<BloodBank> bloodBanks, String message);
}
