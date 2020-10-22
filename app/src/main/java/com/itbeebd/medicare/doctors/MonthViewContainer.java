package com.itbeebd.medicare.doctors;

import android.view.View;
import android.widget.TextView;

import com.itbeebd.medicare.R;
import com.kizitonwose.calendarview.ui.ViewContainer;

import org.jetbrains.annotations.NotNull;

public class MonthViewContainer extends ViewContainer {
    private final TextView headerText;

    public MonthViewContainer(@NotNull View view) {
        super(view);
        headerText = view.findViewById(R.id.exTwoHeaderText);
    }

    public final TextView getTextView() {
        return this.headerText;
    }
}

