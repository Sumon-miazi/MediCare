package com.itbeebd.medicare.medication;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.medicare.R;
import com.kizitonwose.calendarview.ui.ViewContainer;

import org.jetbrains.annotations.NotNull;

public class MedicationDayViewContainer extends ViewContainer {
    private final TextView monthTxt;
    private final TextView dateTxt;
    private final TextView dayTxt;
    private final View selectedView;
    private final ConstraintLayout dayViewLayout;

    public MedicationDayViewContainer(@NotNull View view) {
        super(view);
        monthTxt = view.findViewById(R.id.monthText);
        dateTxt = view.findViewById(R.id.dateText);
        dayTxt = view.findViewById(R.id.dayText);
        selectedView = view.findViewById(R.id.selectedView);
        dayViewLayout = view.findViewById(R.id.dayViewLayout);
    }

    public TextView getMonthTxt() {
        return monthTxt;
    }

    public TextView getDateTxt() {
        return dateTxt;
    }

    public TextView getDayTxt() {
        return dayTxt;
    }

    public View getSelectedView() {
        return selectedView;
    }

    public ConstraintLayout getDayViewLayout() {
        return dayViewLayout;
    }
}
