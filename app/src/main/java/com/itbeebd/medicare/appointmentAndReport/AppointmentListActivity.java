package com.itbeebd.medicare.appointmentAndReport;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.AppointmentListAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

public class AppointmentListActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<String> {

    private RecyclerView nextAppointment;
    private RecyclerView oldAppointment;
    private AppointmentListAdapter nextAppointmentAdapter;
    private AppointmentListAdapter oldAppointmentAdapter;
    private ConstraintLayout nextAppointmentLayout;
    private ConstraintLayout oldAppointmentLayout;
    private ImageView nextHideIcon;
    private ImageView oldHideIcon;
    private ExpandableLayout nextAppointmentsExpandable;
    private ExpandableLayout oldAppointmentsExpandable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        nextAppointment = findViewById(R.id.nextAppointmentsViewId);
        oldAppointment = findViewById(R.id.oldAppointmentsViewId);
        nextAppointmentLayout = findViewById(R.id.nextAppointmentLayoutId);
        oldAppointmentLayout = findViewById(R.id.oldAppointmentLayoutId);
        nextHideIcon = findViewById(R.id.nextHideSickIcon);
        oldHideIcon = findViewById(R.id.previousHideSickIcon);
        nextAppointmentsExpandable = findViewById(R.id.nextExpandable_layout);
        oldAppointmentsExpandable = findViewById(R.id.oldExpandable_layout);

        nextAppointmentAdapter = new AppointmentListAdapter(this);
        oldAppointmentAdapter = new AppointmentListAdapter(this);

        nextAppointmentLayout.setOnClickListener(view -> {
            nextHideIcon.animate().rotation(nextHideIcon.getRotation() == 0? 180 : 0).start();
            //System.out.println(">>>>>>>>.nextHideIcon " + nextHideIcon.getRotation());
            nextAppointmentsExpandable.toggle();
        });

        oldAppointmentLayout.setOnClickListener(view -> {
            oldHideIcon.animate().rotation(oldHideIcon.getRotation() == 0? 180 : 0).start();
            //System.out.println(">>>>>>>>.oldHideIcon " + oldHideIcon.getRotation());
            oldAppointmentsExpandable.toggle();
        });
        setUpAdapters();
    }

    private void setUpAdapters(){
        ArrayList<String> data = new ArrayList<>();
        data.add("data");
        data.add("data");


        nextAppointmentAdapter.setItems(data);
        nextAppointmentAdapter.setListener(this);
        nextAppointment.setLayoutManager(new LinearLayoutManager(this));
        nextAppointment.setAdapter(nextAppointmentAdapter);

        ArrayList<String> data2 = new ArrayList<>();
        data2.add("data2");
        data2.add("data2");
        data2.add("data2");


        oldAppointmentAdapter.setItems(data2);
        oldAppointmentAdapter.setListener(this);
        oldAppointment.setLayoutManager(new LinearLayoutManager(this));
        oldAppointment.setAdapter(oldAppointmentAdapter);

    }

    @Override
    public void onItemClicked(String item, View view) {

    }
}