package com.itbeebd.medicare.doctors;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.AppointmentTimeGridAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.dataClasses.DoctorChamber;
import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.CalendarMonth;
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
    private AppointmentTimeGridAdapter appointmentTimeGridAdapter;
    private LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);

        calendarView = findViewById(R.id.calendarView);
        appointmentTimeGridRecycler = findViewById(R.id.appointmentTimeGridRecyclerId);
        appointmentTimeGridAdapter = new AppointmentTimeGridAdapter(this);

        initDayBinder();
        //initRecyclerView();
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
                TextView textView = dayViewContainer.getView().findViewById(R.id.calendarDayText);
                // textView.setText(calendarDay.getDate().getDayOfMonth());
                textView.setText(String.valueOf(calendarDay.getDate().getDayOfMonth()));
            }
        });

        YearMonth currentMonth = YearMonth.now();
        YearMonth firstMonth = currentMonth.minusMonths(10);
        YearMonth lastMonth = currentMonth.plusMonths(10);
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        calendarView.setup(firstMonth, lastMonth, firstDayOfWeek);
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
                //TextView textView = monthViewContainer.getView().findViewById(R.id.calendarDayText);
                // textView.setText(calendarDay.getDate().getDayOfMonth());
                String calenderHeaderTxt = calendarMonth.getYearMonth().getMonth().name() + ", " + calendarMonth.getYear();
                monthViewContainer.getTextView().setText(calenderHeaderTxt);
                // textView.setText(calendarMonth.getMonth());
            }

        });
    }

    @Override
    public void onItemClicked(DoctorChamber item, View view) {

    }
}