package com.itbeebd.medicare.doctors;

import android.view.View;
import android.widget.TextView;

import com.itbeebd.medicare.R;
import com.kizitonwose.calendarview.ui.ViewContainer;

import org.jetbrains.annotations.NotNull;


public class DayViewContainer extends ViewContainer {
    private final TextView textView;

    public DayViewContainer(@NotNull View view) {
        super(view);
        textView = view.findViewById(R.id.calendarDayText);
    }

    public final TextView getTextView() {
        return this.textView;
    }
}
