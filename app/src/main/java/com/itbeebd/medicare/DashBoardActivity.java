package com.itbeebd.medicare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.itbeebd.medicare.allAdapters.SpecialistAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiUrls;
import com.itbeebd.medicare.api.AppointmentApi;
import com.itbeebd.medicare.api.HospitalApi;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.doctors.DoctorListActivity;
import com.itbeebd.medicare.utils.Specialist;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class DashBoardActivity extends Fragment implements OnRecyclerObjectClickListener<Specialist> {

    private OnItemSelectedListener listener;
    private CircularImageView main_doctorImageView;
    private CardView hospitalCardView;
    private CardView diagnosticCardView;
    private CardView bloodBankCardView;
    private RecyclerView allSpecialistView;
    private TextView appointmentDateHint;
    private TextView appointmentTimeHint;
    private TextView hospitalName;
    private TextView doctorName;
    private TextView doctorDegree;
    private TextView chamberAddress;
    private ImageView showAllAppointment;
    private SpecialistAdapter specialistAdapter;
    private ConstraintLayout nextAppointmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_dash_board, container, false);
        main_doctorImageView = view.findViewById(R.id.main_doctorImageViewId);
        hospitalCardView = view.findViewById(R.id.hospitalCardViewId);
        diagnosticCardView = view.findViewById(R.id.diagnosticCardViewId);
        bloodBankCardView = view.findViewById(R.id.bloodBankCardViewId);
        allSpecialistView = view.findViewById(R.id.allSpecialistViewId);
        nextAppointmentView = view.findViewById(R.id.constraintLayout2);
        appointmentDateHint = view.findViewById(R.id.appointmentDateHintId);
        appointmentTimeHint = view.findViewById(R.id.appointmentTimeHintId);
        showAllAppointment = view.findViewById(R.id.calenderIconId);
        doctorName = view.findViewById(R.id.textView5);
        doctorDegree = view.findViewById(R.id.textView4);
        hospitalName = view.findViewById(R.id.hospitalName);
        chamberAddress = view.findViewById(R.id.textView6);

        specialistAdapter = new SpecialistAdapter(getContext());

        hospitalCardView.setOnClickListener(v -> listener.onItemSelectedOnDashBoard(v));
        diagnosticCardView.setOnClickListener(v -> listener.onItemSelectedOnDashBoard(v));
        bloodBankCardView.setOnClickListener(v -> listener.onItemSelectedOnDashBoard(v));

        setUpAllSpecialist();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getNextAppointment();
    }

    private void getNextAppointment() {
        new AppointmentApi().getNextAppointment(CustomSharedPref.getInstance(getContext()).getUserId(), (appointment, message) -> {
            if(appointment != null){
                ArrayList<String> dateAndTime = appointment.getDateFormat();
                nextAppointmentView.setVisibility(View.VISIBLE);
                doctorName.setText(appointment.getName());
                doctorDegree.setText(appointment.getDegree());
                chamberAddress.setText(appointment.getAddress());
                hospitalName.setText(appointment.getHospitalName());
                appointmentDateHint.setText(dateAndTime.get(0));
                appointmentTimeHint.setText(dateAndTime.get(1));

                if(appointment.getImage() != null){
                    Glide.with(this)
                            .load(ApiUrls.BASE_IMAGE_URL + appointment.getImage())
                            .into(main_doctorImageView);
                }
            }
            else {
                nextAppointmentView.setVisibility(View.GONE);
            }
        });
    }

    private void setUpAllSpecialist() {
        new HospitalApi().getAllSpecialist((dataListFromApiCall, message) -> {
            if(dataListFromApiCall != null){
                specialistAdapter.setItems(dataListFromApiCall);
                specialistAdapter.setListener(this);
                allSpecialistView.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.HORIZONTAL, false));
                allSpecialistView.setAdapter(specialistAdapter);
            }
            else Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implemenet MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onItemClicked(Specialist item, View view) {
        Intent intent = new Intent(getContext(), DoctorListActivity.class);
        intent.putExtra("specialistId", item.getId());
        intent.putExtra("source", "DashBoardActivity");
        startActivity(intent);
    }

    public interface OnItemSelectedListener {
        void onItemSelectedOnDashBoard(View view);
    }

// Fragments don’t subclass the Context class. Therefore you have to use the getActivity() method to get the parent activity.
}