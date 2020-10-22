package com.itbeebd.medicare.doctors;

import android.view.View;
import android.widget.TextView;

import com.itbeebd.medicare.R;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.ui.ViewContainer;

import org.jetbrains.annotations.NotNull;


public class DayViewContainer extends ViewContainer {
    private final TextView dateTxt;
    private CalendarDay day;

    public DayViewContainer(@NotNull View view) {
        super(view);
        dateTxt = view.findViewById(R.id.calendarDayText);
        /*
        dayViewContainer.getDayTxtView().setOnClickListener(view -> {
            if (calendarDay.getOwner() == DayOwner.THIS_MONTH) {
                if (selectedDate == calendarDay.getDate()) {
                    selectedDate = null;
                    calendarView.notifyDayChanged(calendarDay);
                } else {
                    LocalDate oldDate = selectedDate;
                    selectedDate = calendarDay.getDate();
                    calendarView.notifyDateChanged(calendarDay.getDate());
                    // oldDate?.let { binding.exTwoCalendar.notifyDateChanged(oldDate) }
                }
                // menuItem.isVisible = selectedDate != null
            }
        });

         */
    }

    public final TextView getDayTxtView() {
        return this.dateTxt;
    }

    public TextView getDateTxt() {
        return dateTxt;
    }

    public CalendarDay getDay() {
        return day;
    }

    public void setDay(CalendarDay day) {
        this.day = day;
    }
}
