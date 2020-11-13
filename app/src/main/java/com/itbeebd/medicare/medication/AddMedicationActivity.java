

package com.itbeebd.medicare.medication;

import android.os.Bundle;
import android.view.View;
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

    private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");
    private final ArrayList<LocalDate> selectedDates = new ArrayList<>();
    private final LocalDate selectedDate = LocalDate.now();
    private final LocalDate today = LocalDate.now();
    private CalendarView addMedicationCalendarView;
    private TextView monthView;
    private TextView yearView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        addMedicationCalendarView = findViewById(R.id.addMedicationCalendar);
        monthView = findViewById(R.id.addMedicationCalendarMonthText);
        yearView = findViewById(R.id.addMedicationYearText);

        initDayBinder();
    }

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
        addMedicationCalendarView.setup(currentMonth, currentMonth.plusMonths(2), daysOfWeek[0]);
        addMedicationCalendarView.scrollToMonth(currentMonth);

        monthScrollListener();
    }

    private void monthScrollListener() {
        addMedicationCalendarView.setMonthScrollListener(calendarMonth -> {
            if (addMedicationCalendarView.getMaxRowCount() == 6) {
                yearView.setText(String.valueOf(calendarMonth.getYearMonth().getYear()));
                monthView.setText(monthFormatter.format(calendarMonth.getYearMonth()));
            } else {
                // In week mode, we show the header a bit differently.
                // We show indices with dates from different months since
                // dates overflow and cells in one index can belong to different
                // months/years.

                LocalDate firstDate = calendarMonth.getWeekDays().get(0).get(0).getDate();
                LocalDate lastDate = calendarMonth.getWeekDays()
                        .get(calendarMonth.getWeekDays().size() - 1)
                        .get(calendarMonth.getWeekDays().size() - 1)
                        .getDate();

                if (firstDate.getMonth() == lastDate.getMonth()) {
                    yearView.setText(String.valueOf(firstDate.getYear()));
                    monthView.setText(monthFormatter.format(firstDate));
                } else {
                    String monthName = monthFormatter.format(firstDate) + " - " + monthFormatter.format(lastDate);
                    monthView.setText(monthName);
                    if (firstDate.getYear() == lastDate.getYear()) {
                        yearView.setText(String.valueOf(firstDate.getYear()));
                    } else {
                        String yearName = firstDate.getYear() + " - " + lastDate.getYear();
                        yearView.setText(yearName);
                    }
                }
            }
            return null;
        });
    }
}