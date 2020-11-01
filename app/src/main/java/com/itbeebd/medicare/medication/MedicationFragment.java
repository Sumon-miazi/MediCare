package com.itbeebd.medicare.medication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.MedicationAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.doctors.customCalender.MonthViewContainer;
import com.itbeebd.medicare.utils.Medication;
import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.CalendarMonth;
import com.kizitonwose.calendarview.ui.DayBinder;
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder;

import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

import kotlin.jvm.internal.Intrinsics;

public class MedicationFragment extends Fragment implements OnRecyclerObjectClickListener<Medication> {

    private CalendarView medicationCalendarView;
    private RecyclerView medicationRecyclerView;
    private Button addNewMedicineBtn;
    private MedicationAdapter medicationAdapter;
    private final LocalDate today = LocalDate.now();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd");
    private final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE");
    private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM");
    private LocalDate selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medication, container, false);
        medicationCalendarView = view.findViewById(R.id.medicationCalendarViewId);
        medicationRecyclerView = view.findViewById(R.id.medicationRecyclerViewId);
        addNewMedicineBtn = view.findViewById(R.id.addNewMedicineBtnId);

        medicationAdapter = new MedicationAdapter(getContext());

        addNewMedicineBtn.setOnClickListener(v -> addNewMedicine());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initDayBinder();
        setUpMedicationRecyclerView();
    }


    private void initDayBinder() {
        medicationCalendarView.setDayBinder(new DayBinder<MedicationDayViewContainer>() {

            @Override
            public MedicationDayViewContainer create(View view) {
                return new MedicationDayViewContainer(view);
            }

            @Override
            public void bind(MedicationDayViewContainer dayViewContainer, CalendarDay calendarDay) {

                ConstraintLayout dateView = dayViewContainer.getDayViewLayout();

                dateView.setOnClickListener(view -> {

                    CalendarDay firstDay = medicationCalendarView.findFirstVisibleDay();
                    CalendarDay lastDay = medicationCalendarView.findLastVisibleDay();

                    if (Intrinsics.areEqual(firstDay, calendarDay.getDay())) {
                        medicationCalendarView.smoothScrollToDate(calendarDay.getDate(), null);
                    } else if (Intrinsics.areEqual(lastDay, calendarDay.getDay())) {
                        LocalDate localDate = calendarDay.getDate().minusDays(4L);
                        Intrinsics.checkNotNullExpressionValue(localDate, "day.date.minusDays(4)");
                        medicationCalendarView.smoothScrollToDate(localDate, null);
                    }

                    if (selectedDate == calendarDay.getDate()) {
                        LocalDate oldDate = selectedDate;
                        selectedDate = calendarDay.getDate();
                        medicationCalendarView.notifyDateChanged(calendarDay.getDate());
                        if (oldDate != null) medicationCalendarView.notifyDateChanged(oldDate);
                    }
                });

                LocalDate date = calendarDay.getDate();


                dayViewContainer.getMonthTxt().setText(monthFormatter.format(date));
                dayViewContainer.getDateTxt().setText(dateFormatter.format(date));
                dayViewContainer.getDayTxt().setText(dayFormatter.format(date));

                if (date == selectedDate) {
                    dayViewContainer.getDateTxt().setTextColor(getResources().getColor(R.color.example_7_yellow));
                    dayViewContainer.getSelectedView().setVisibility(View.VISIBLE);
                } else {
                    dayViewContainer.getDateTxt().setTextColor(getResources().getColor(R.color.example_7_white));
                    dayViewContainer.getSelectedView().setVisibility(View.INVISIBLE);
                }

/*
                if (calendarDay.getOwner() == DayOwner.THIS_MONTH) {
                    dateTxtView.setVisibility(View.VISIBLE);
                    if (calendarDay.getDate().isBefore(today)) {
                        dateTxtView.setTextColor(getResources().getColor(R.color.example_4_grey_past));
                    } else {
                        LocalDate date = calendarDay.getDate();

                        if (selectedDate != null && selectedDate.equals(date)) {
                            dateTxtView.setTextColor(getResources().getColor(R.color.example_2_white));
                            dateTxtView.setBackgroundResource(R.drawable.date_selected_bg);
                        } else if (today.equals(date)) {
                            dateTxtView.setTextColor(getResources().getColor(R.color.example_4_grey));
                            dateTxtView.setBackgroundResource(R.drawable.circle_bg_for_date);
                        } else {
                            dateTxtView.setTextColor(getResources().getColor(R.color.example_2_black));
                            dateTxtView.setBackground(null);
                        }


                    }

                } else {
                    dateTxtView.setVisibility(View.INVISIBLE);
                }

 */


            }
        });

        YearMonth currentMonth = YearMonth.now();
        //YearMonth firstMonth = currentMonth.minusMonths(10);
        //YearMonth lastMonth = currentMonth.plusMonths(10);
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        // Value for firstDayOfWeek does not matter since inDates and outDates are not generated.
        medicationCalendarView.setup(currentMonth, currentMonth.plusMonths(2), firstDayOfWeek);
        medicationCalendarView.scrollToMonth(YearMonth.from(LocalDate.now()));


        //  initMonthBinder();
    }

    private void initMonthBinder() {

        medicationCalendarView.setMonthHeaderBinder(new MonthHeaderFooterBinder<MonthViewContainer>() {
            @NotNull
            @Override
            public MonthViewContainer create(@NotNull View view) {
                return new MonthViewContainer(view);
            }

            @Override
            public void bind(@NotNull MonthViewContainer monthViewContainer, @NotNull CalendarMonth calendarMonth) {
                String calenderHeaderTxt = calendarMonth.getYearMonth().getMonth().name().toLowerCase() + ", " + calendarMonth.getYear();
                monthViewContainer.getTextView().setText(calenderHeaderTxt);
            }

        });
    }


    private void setUpMedicationRecyclerView() {
        ArrayList<Medication> medications = new ArrayList<>();
        medications.add(new Medication());
        medications.add(new Medication());
        medications.add(new Medication());
        medications.add(new Medication());
        medications.add(new Medication());
        medications.add(new Medication());
        medications.add(new Medication());

        medicationAdapter.setItems(medications);
        medicationAdapter.setListener(this);
        medicationRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        medicationRecyclerView.setAdapter(medicationAdapter);
    }


    @Override
    public void onItemClicked(Medication item, View view) {

    }

    private void addNewMedicine() {

    }
}