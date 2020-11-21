package com.itbeebd.medicare.appointmentAndReport;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.itbeebd.medicare.R;

public class OptionsBottomSheetFragment extends BottomSheetDialogFragment {

    private OptionsBottomSheetFragment.OnItemSelectedListener listener;
    private ConstraintLayout report;
    private ConstraintLayout cancel;
    private ConstraintLayout delete;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.appointment_bottom_sheet, container, false);
        report = view.findViewById(R.id.constraintLayout3);
        cancel = view.findViewById(R.id.constraintLayout2);
        delete = view.findViewById(R.id.constraintLayout1);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews();
    }

    private void setUpViews() {
        report.setOnClickListener(view -> {
            dismissAllowingStateLoss();
            listener.onItemSelectedOnBottomSheet(view);
        });

        cancel.setOnClickListener(view -> {
            dismissAllowingStateLoss();
            listener.onItemSelectedOnBottomSheet(view);
        });

        delete.setOnClickListener(view -> {
            dismissAllowingStateLoss();
            listener.onItemSelectedOnBottomSheet(view);
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OptionsBottomSheetFragment.OnItemSelectedListener) {
            listener = (OptionsBottomSheetFragment.OnItemSelectedListener) context;
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
        void onItemSelectedOnBottomSheet(View view);
    }

}
