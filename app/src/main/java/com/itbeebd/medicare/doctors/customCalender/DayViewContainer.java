package com.itbeebd.medicare.doctors.customCalender;

import android.view.View;
import android.widget.TextView;

import com.itbeebd.medicare.R;
import com.kizitonwose.calendarview.ui.ViewContainer;

import org.jetbrains.annotations.NotNull;


public class DayViewContainer extends ViewContainer {
    private final TextView dateTxt;

    public DayViewContainer(@NotNull View view) {
        super(view);
        dateTxt = view.findViewById(R.id.calendarDayText);
    }

    public final TextView getDayTxtView() {
        return this.dateTxt;
    }
}
