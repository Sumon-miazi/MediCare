package com.itbeebd.medicare.medication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.MedicationAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.utils.Medication;
import com.kizitonwose.calendarview.CalendarView;

import java.util.ArrayList;

public class MedicationFragment extends Fragment implements OnRecyclerObjectClickListener<Medication> {

    private CalendarView medicationCalendarView;
    private RecyclerView medicationRecyclerView;
    private Button addNewMedicineBtn;
    private MedicationAdapter medicationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medication, container, false);
        medicationCalendarView = view.findViewById(R.id.medicationCalendarViewId);
        medicationRecyclerView = view.findViewById(R.id.medicationRecyclerViewId);
        addNewMedicineBtn = view.findViewById(R.id.addNewMedicineBtnId);

        medicationAdapter = new MedicationAdapter(getContext());

        addNewMedicineBtn.setOnClickListener(v -> addNewMedicine());

        return view;
    }

    private void setUpMedicationRecyclerView() {
        ArrayList<Medication> medications = new ArrayList<>();
        medications.add(new Medication());
        medications.add(new Medication());
        medications.add(new Medication());
        medications.add(new Medication());
        medications.add(new Medication());
        medications.add(new Medication());
        medications.add(new Medication());

        medicationAdapter.setItems(medications);
        medicationAdapter.setListener(this);
        medicationRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        medicationRecyclerView.setAdapter(medicationAdapter);
    }


    @Override
    public void onItemClicked(Medication item, View view) {

    }

    private void addNewMedicine() {

    }
}