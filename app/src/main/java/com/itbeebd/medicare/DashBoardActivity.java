package com.itbeebd.medicare;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.allAdapters.SpecialistAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.ApiCalls;
import com.itbeebd.medicare.utils.Specialist;
import com.mikhaellopez.circularimageview.CircularImageView;

public class DashBoardActivity extends Fragment implements OnRecyclerObjectClickListener<Specialist> {

    private OnItemSelectedListener listener;
    private CircularImageView main_doctorImageView;
    private CardView hospitalCardView;
    private CardView diagnosticCardView;
    private RecyclerView allSpecialistView;
    private SpecialistAdapter specialistAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_dash_board, container, false);
        main_doctorImageView = view.findViewById(R.id.main_doctorImageViewId);
        hospitalCardView = view.findViewById(R.id.hospitalCardViewId);
        diagnosticCardView = view.findViewById(R.id.diagnosticCardViewId);
        allSpecialistView = view.findViewById(R.id.allSpecialistViewId);

        specialistAdapter = new SpecialistAdapter(getContext());

        hospitalCardView.setOnClickListener(v -> listener.onItemSelectedOnDashBoard(v));
        diagnosticCardView.setOnClickListener(v -> listener.onItemSelectedOnDashBoard(v));

        setUpAllSpecialist();

        return view;
    }

    private void setUpAllSpecialist() {
        new ApiCalls().getAllSpecialist((dataListFromApiCall, message) -> {
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

    }

    public interface OnItemSelectedListener {
        void onItemSelectedOnDashBoard(View view);
    }

// Fragments don’t subclass the Context class. Therefore you have to use the getActivity() method to get the parent activity.
}