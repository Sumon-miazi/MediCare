package com.itbeebd.medicare;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.mikhaellopez.circularimageview.CircularImageView;

public class DashBoardActivity extends Fragment {

    private OnItemSelectedListener listener;
    private CircularImageView main_doctorImageView;
    private CardView hospitalCardView;
    private CardView diagnosticCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_dash_board, container, false);
        main_doctorImageView = view.findViewById(R.id.main_doctorImageViewId);
        hospitalCardView = view.findViewById(R.id.hospitalCardViewId);
        diagnosticCardView = view.findViewById(R.id.diagnosticCardViewId);

        hospitalCardView.setOnClickListener(v -> listener.onItemSelectedOnDashBoard(v));
        diagnosticCardView.setOnClickListener(v -> listener.onItemSelectedOnDashBoard(v));

        return view;
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

    public interface OnItemSelectedListener {
        void onItemSelectedOnDashBoard(View view);
    }

// Fragments don’t subclass the Context class. Therefore you have to use the getActivity() method to get the parent activity.
}