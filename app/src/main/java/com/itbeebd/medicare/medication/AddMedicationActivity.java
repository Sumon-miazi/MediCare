

package com.itbeebd.medicare.medication;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.doctors.customCalender.DayViewContainer;
import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.DayOwner;
import com.kizitonwose.calendarview.ui.DayBinder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

import kotlin.collections.ArraysKt;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;

public class AddMedicationActivity extends AppCompatActivity {

    private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM");
    private final ArrayList<LocalDate> selectedDates = new ArrayList<>();
    private final LocalDate selectedDate = LocalDate.now();
    private final LocalDate today = LocalDate.now();
    private CalendarView addMedicationCalendarView;
    private CheckBox weekMode;
    private TextView monthView;
    private TextView yearView;

    public static DayOfWeek[] daysOfWeekFromLocale() {

        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        DayOfWeek[] daysOfWeek = DayOfWeek.values();

        System.out.println(">>>>>first day of week " + firstDayOfWeek.toString());

        if (firstDayOfWeek != DayOfWeek.MONDAY) {
            int var3 = firstDayOfWeek.ordinal();
            DayOfWeek[] rhs = ArraysKt.sliceArray(daysOfWeek, new IntRange(var3, ArraysKt.getIndices(daysOfWeek).getLast()));
            DayOfWeek[] lhs = ArraysKt.sliceArray(daysOfWeek, RangesKt.until(0, firstDayOfWeek.ordinal()));
            daysOfWeek = ArraysKt.plus(rhs, lhs);
        }

        return daysOfWeek;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        addMedicationCalendarView = findViewById(R.id.addMedicationCalendar);
        weekMode = findViewById(R.id.weekModeCheckBox);
        monthView = findViewById(R.id.addMedicationCalendarMonthText);
        yearView = findViewById(R.id.addMedicationYearText);

        initDayBinder();
    }

    private void initDayBinder() {

        addMedicationCalendarView.setDayBinder(new DayBinder<DayViewContainer>() {

            @Override
            public DayViewContainer create(View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(DayViewContainer dayViewContainer, CalendarDay calendarDay) {
                TextView dateTxtView = dayViewContainer.getDayTxtView();
                dateTxtView.setText(String.valueOf(calendarDay.getDate().getDayOfMonth()));
                dateTxtView.setOnClickListener(view -> {
                    if (calendarDay.getOwner() == DayOwner.THIS_MONTH) {
                        if (selectedDates.contains(calendarDay.getDate())) {
                            selectedDates.remove(calendarDay.getDate());
                        } else {
                            selectedDates.add(calendarDay.getDate());
                        }
                        addMedicationCalendarView.notifyDayChanged(calendarDay);
                    }

                });

                if (calendarDay.getOwner() == DayOwner.THIS_MONTH) {
                    dateTxtView.setVisibility(View.VISIBLE);

                    if (calendarDay.getDate().isBefore(today)) {
                        dateTxtView.setTextColor(getResources().getColor(R.color.example_4_grey_past));
                    } else {
                        LocalDate date = calendarDay.getDate();
                        if (selectedDates.contains(date)) {
                            dateTxtView.setTextColor(getResources().getColor(R.color.example_2_white));
                            dateTxtView.setBackgroundResource(R.drawable.yello_round_bg);
                        } else if (today.equals(date)) {
                            dateTxtView.setTextColor(getResources().getColor(R.color.example_4_grey));
                            dateTxtView.setBackgroundResource(R.drawable.circle_bg_for_date);
                        } else {
                            dateTxtView.setTextColor(getResources().getColor(R.color.white));
                            dateTxtView.setBackground(null);
                        }
                    }

                } else {
                    dateTxtView.setVisibility(View.INVISIBLE);
                }
            }
        });

        DayOfWeek[] daysOfWeek = daysOfWeekFromLocale();
        
        YearMonth currentMonth = YearMonth.now();
        YearMonth firstMonth = currentMonth.minusMonths(10);
        YearMonth lastMonth = currentMonth.plusMonths(10);
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        addMedicationCalendarView.setup(currentMonth, currentMonth.plusMonths(2), daysOfWeek[0]);
        addMedicationCalendarView.scrollToMonth(currentMonth);
    }
}