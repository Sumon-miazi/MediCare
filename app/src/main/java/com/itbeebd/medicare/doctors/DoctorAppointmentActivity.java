package com.itbeebd.medicare.doctors;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.AppointmentTimeGridAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.dataClasses.DoctorChamber;
import com.itbeebd.medicare.doctors.customCalender.DayViewContainer;
import com.itbeebd.medicare.doctors.customCalender.MonthViewContainer;
import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.CalendarMonth;
import com.kizitonwose.calendarview.model.DayOwner;
import com.kizitonwose.calendarview.ui.DayBinder;
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder;

import org.jetbrains.annotations.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

public class DoctorAppointmentActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<DoctorChamber> {

    private final LocalDate today = LocalDate.now();
    private CalendarView calendarView;
    private RecyclerView appointmentTimeGridRecycler;
    private RecyclerView appointmentChamberRecycler;
    private ConstraintLayout chambersListInAppointment;
    private AppointmentTimeGridAdapter appointmentTimeGridAdapter;
    private LocalDate selectedDate;
    private ArrayList<DoctorChamber> doctorChambers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);

        calendarView = findViewById(R.id.calendarView);
        appointmentTimeGridRecycler = findViewById(R.id.appointmentTimeGridRecyclerId);
        chambersListInAppointment = findViewById(R.id.chambersListInAppointmentId);
        appointmentChamberRecycler = findViewById(R.id.appointmentChamberRecyclerId);


        appointmentTimeGridAdapter = new AppointmentTimeGridAdapter(this);
        appointmentTimeGridAdapter = new AppointmentTimeGridAdapter(this);

        if (getIntent().hasExtra("appointment")) {
            doctorChambers = getIntent().getParcelableArrayListExtra("appointment");
        }
        initDayBinder();
        initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<DoctorChamber> doctorChambers = new ArrayList<>();
        doctorChambers.add(new DoctorChamber());
        doctorChambers.add(new DoctorChamber());
        doctorChambers.add(new DoctorChamber());
        doctorChambers.add(new DoctorChamber());
        doctorChambers.add(new DoctorChamber());
        doctorChambers.add(new DoctorChamber());
        doctorChambers.add(new DoctorChamber());

        appointmentTimeGridAdapter.setItems(doctorChambers);
        appointmentTimeGridAdapter.setListener(this);
        appointmentTimeGridRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        appointmentTimeGridRecycler.setAdapter(appointmentTimeGridAdapter);
    }

    private void initDayBinder() {
        calendarView.setDayBinder(new DayBinder<DayViewContainer>() {

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
                        if (selectedDate == calendarDay.getDate()) {
                            selectedDate = null;
                            calendarView.notifyDayChanged(calendarDay);
                        } else {
                            LocalDate oldDate = selectedDate;
                            selectedDate = calendarDay.getDate();
                            calendarView.notifyDateChanged(calendarDay.getDate());
                            if (oldDate != null) calendarView.notifyDateChanged(oldDate);
                        }
                        // menuItem.isVisible = selectedDate != null
                    }
                });

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
            }
        });

        YearMonth currentMonth = YearMonth.now();
        //YearMonth firstMonth = currentMonth.minusMonths(10);
        //YearMonth lastMonth = currentMonth.plusMonths(10);
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        calendarView.setup(currentMonth, currentMonth.plusMonths(12), firstDayOfWeek);
        calendarView.scrollToMonth(currentMonth);

        initMonthBinder();
    }

    private void initMonthBinder() {

        calendarView.setMonthHeaderBinder(new MonthHeaderFooterBinder<MonthViewContainer>() {
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

    @Override
    public void onItemClicked(DoctorChamber item, View view) {

    }
}