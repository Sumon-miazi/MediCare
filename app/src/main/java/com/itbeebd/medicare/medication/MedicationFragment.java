package com.itbeebd.medicare.medication;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.kizitonwose.calendarview.utils.Size;

import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

public class MedicationFragment extends Fragment implements OnRecyclerObjectClickListener<Medication> {

    private CalendarView medicationCalendarView;
    private RecyclerView medicationRecyclerView;
    private Button addNewMedicineBtn;
    private MedicationAdapter medicationAdapter;
    private final LocalDate today = LocalDate.now();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd");
    private final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE");
    private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM");
    private LocalDate selectedDate = LocalDate.now();

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

    private void setDaySize() {
        DisplayMetrics dm = new DisplayMetrics();
        Object var10000 = this.requireContext().getSystemService(Context.WINDOW_SERVICE);
        if (var10000 == null) {
            throw new NullPointerException("null cannot be cast to non-null type android.view.WindowManager");
        } else {
            WindowManager wm = (WindowManager) var10000;
            wm.getDefaultDisplay().getMetrics(dm);
            int dayWidth = dm.widthPixels / 5;
            int dayHeight = (int) ((double) dayWidth * 1.25D);
            medicationCalendarView.setDaySize(new Size(dayWidth, dayHeight));
        }
    }

    private void initDayBinder() {
        setDaySize();
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

                    assert firstDay != null;
                    if (firstDay.getDay() == calendarDay.getDay()) {
                        medicationCalendarView.smoothScrollToDate(calendarDay.getDate());
                    } else {
                        assert lastDay != null;
                        if (lastDay.getDay() == calendarDay.getDay()) {
                            medicationCalendarView.smoothScrollToDate(calendarDay.getDate().minusDays(2L));
                        }
                    }


                    if (selectedDate != calendarDay.getDate()) {
                        LocalDate oldDate = selectedDate;
                        selectedDate = calendarDay.getDate();
                        medicationCalendarView.notifyDateChanged(calendarDay.getDate());
                        if (oldDate != null) medicationCalendarView.notifyDateChanged(oldDate);
                        //   getAllTimesFromDay(calendarDay.getDate().getDayOfWeek().toString());
                    }


                });

                LocalDate date = calendarDay.getDate();

                dayViewContainer.getMonthTxt().setText(monthFormatter.format(date));
                dayViewContainer.getDateTxt().setText(dateFormatter.format(date));
                dayViewContainer.getDayTxt().setText(dayFormatter.format(date));

                System.out.println(">>>>>>> selected day " + selectedDate.toString());
                System.out.println(">>>>>>> date " + date.toString());
                if (date.toString().equals(selectedDate.toString())) {
                    System.out.println(">>>>>>> date clicked if");
                    dayViewContainer.getDateTxt().setTextColor(getResources().getColor(R.color.example_7_yellow));
                    dayViewContainer.getSelectedView().setVisibility(View.VISIBLE);
                } else {
                    System.out.println(">>>>>>> date clicked else");
                    dayViewContainer.getDateTxt().setTextColor(getResources().getColor(R.color.example_7_white));
                    dayViewContainer.getSelectedView().setVisibility(View.INVISIBLE);
                }

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