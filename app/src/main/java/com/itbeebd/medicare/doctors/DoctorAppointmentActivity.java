package com.itbeebd.medicare.doctors;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.medicare.R;
import com.itbeebd.medicare.allAdapters.AppointmentTimeGridAdapter;
import com.itbeebd.medicare.allAdapters.DoctorChamberAdapter;
import com.itbeebd.medicare.allAdapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.medicare.api.DoctorApi;
import com.itbeebd.medicare.api.UserApi;
import com.itbeebd.medicare.db.CustomSharedPref;
import com.itbeebd.medicare.doctors.customCalender.DayViewContainer;
import com.itbeebd.medicare.doctors.customCalender.MonthViewContainer;
import com.itbeebd.medicare.utils.Appointment;
import com.itbeebd.medicare.utils.CustomDayOfWeek;
import com.itbeebd.medicare.utils.Doctor;
import com.itbeebd.medicare.utils.DoctorChamber;
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

public class DoctorAppointmentActivity extends AppCompatActivity implements OnRecyclerObjectClickListener {

    private final LocalDate today = LocalDate.now();
    private CalendarView calendarView;
    private RecyclerView appointmentTimeGridRecycler;
    private RecyclerView appointmentChamberRecycler;
    private ConstraintLayout chambersListInAppointment;
    private AppointmentTimeGridAdapter appointmentTimeGridAdapter;
    private DoctorChamberAdapter doctorChamberListAdapter;

    private ArrayList<DoctorChamber> doctorChambers;
    private DoctorChamber selectedChamber;
    private LocalDate selectedDate = null;
    private String selectedTime = null;

    private ArrayList<String> timeTable;
    private ArrayList<String> dayArrayList;
    private Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);

        chambersListInAppointment = findViewById(R.id.chambersListInAppointmentId);
        appointmentChamberRecycler = findViewById(R.id.appointmentChamberRecyclerId);
        calendarView = findViewById(R.id.calendarView);
        appointmentTimeGridRecycler = findViewById(R.id.appointmentTimeGridRecyclerId);
        chambersListInAppointment.setVisibility(View.GONE);

        appointmentTimeGridAdapter = new AppointmentTimeGridAdapter(this);
        doctorChamberListAdapter = new DoctorChamberAdapter(this);

        if (getIntent().hasExtra("appointment")) {
            System.out.println(">>>>>>>>>>>>>> get intent code-block");
            doctorChambers = getIntent().getParcelableArrayListExtra("appointment");

            if (doctorChambers != null) initChamberRecyclerView();

        } else if (getIntent().hasExtra("doctorObj")) {
            doctor = getIntent().getParcelableExtra("doctorObj");
            callApiToGetDoctorChambers();
        }
        // initDayBinder();
        // initTimeRecyclerView();
    }

    private void initChamberRecyclerView() {
        doctorChamberListAdapter.setItems(doctorChambers);
        doctorChamberListAdapter.setListener(this);
        appointmentChamberRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        appointmentChamberRecycler.setAdapter(doctorChamberListAdapter);
        chambersListInAppointment.setVisibility(View.VISIBLE);


        if (doctorChambers.size() > 0) {
            selectedChamber = doctorChambers.get(0);
            setUpCalenderAndTimeForChamberClicked(doctorChambers.get(0));
        }

    }

    private void setUpCalenderAndTimeForChamberClicked(DoctorChamber doctorChamber) {

        getAllDaysFromChamber(doctorChamber);
        initDayBinder();

        if (doctorChamber.getCustomDayOfWeekArrayList().size() != 0) {
            timeTable = doctorChambers.get(0).getCustomDayOfWeekArrayList().get(0).getTimes();
            initTimeRecyclerView();
        }

    }

    private void callApiToGetDoctorChambers() {
        new DoctorApi().getAllDoctorChambersByDoctorId(doctor.getDoctorId(), (doctorChambers, message) -> {
            if (doctorChambers != null) {
                this.doctorChambers = doctorChambers;
                if (!doctorChambers.isEmpty()) {
                    //  timeTable = doctorChambers.get(0).getCustomDayOfWeekArrayList().get(0).getTimes();
                    initChamberRecyclerView();
                }
            } else {
                this.doctorChambers = new ArrayList<>();
                // Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
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
                            clearTimeRecyclerView();
                        } else {
                            LocalDate oldDate = selectedDate;
                            selectedDate = calendarDay.getDate();
                            selectedTime = null;
                            calendarView.notifyDateChanged(calendarDay.getDate());
                            if (oldDate != null) calendarView.notifyDateChanged(oldDate);
                            getAllTimesFromDay(calendarDay.getDate().getDayOfWeek().toString());
                        }
                        // menuItem.isVisible = selectedDate != null
                    }
                });

                if (calendarDay.getOwner() == DayOwner.THIS_MONTH) {
                    dateTxtView.setVisibility(View.VISIBLE);
                    if (calendarDay.getDate().isBefore(today) || !(dayArrayList.contains(calendarDay.getDate().getDayOfWeek().toString()))) {
                        // System.out.println(">>>>>> day of week " + calendarDay.getDate().getDayOfWeek());
                        dateTxtView.setTextColor(getResources().getColor(R.color.example_4_grey_past));
                        if (today.equals(calendarDay.getDate()))
                            dateTxtView.setBackgroundResource(R.drawable.circle_bg_for_date);

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

    private void initTimeRecyclerView() {
        if (timeTable == null) return;
        appointmentTimeGridAdapter.setItems(timeTable);
        appointmentTimeGridAdapter.setListener(this);
        appointmentTimeGridRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        appointmentTimeGridRecycler.setAdapter(appointmentTimeGridAdapter);
    }

    @Override
    public void onItemClicked(Object item, View view) {
        if (item instanceof DoctorChamber) {
            selectedChamber = (DoctorChamber) item;
            selectedDate = null;
            selectedTime = null;
            for(int i = 0; i < doctorChambers.size(); i++){
                if(doctorChambers.get(i).equals(selectedChamber))
                    doctorChambers.get(i).setClicked(1);
                else doctorChambers.get(i).setClicked(0);
            }

            doctorChamberListAdapter.setItems(doctorChambers);
            doctorChamberListAdapter.notifyDataSetChanged();

            System.out.println(">>>>>>>>>>>> Chamber clicked " + ((DoctorChamber) item).getName());
            clearTimeRecyclerView();
            setUpCalenderAndTimeForChamberClicked(selectedChamber);
        } else if (item instanceof String) {
            selectedTime = (String) item;
            System.out.println("time clicked " + item);
        }
    }

    private void getAllDaysFromChamber(DoctorChamber doctorChamber) {
        if (dayArrayList != null) dayArrayList.clear();
        else dayArrayList = new ArrayList<>();
        ArrayList<CustomDayOfWeek> temp = doctorChamber.getCustomDayOfWeekArrayList();
        for (int i = 0; i < temp.size(); i++) {
            dayArrayList.add(temp.get(i).getDay().toUpperCase());
        }
    }

    private void getAllTimesFromDay(String day) {
        System.out.println("<<<<<<<<<< day clicked " + day);
        clearTimeRecyclerView();
        ArrayList<CustomDayOfWeek> dayOfWeeks = selectedChamber.getCustomDayOfWeekArrayList();
        for (int i = 0; i < dayOfWeeks.size(); i++) {
            if (day.toLowerCase().equals(dayOfWeeks.get(i).getDay().toLowerCase())) {
                appointmentTimeGridAdapter.setItems(dayOfWeeks.get(i).getTimes());
                appointmentTimeGridAdapter.notifyDataSetChanged();
                break;
            }
        }

    }

    private void clearTimeRecyclerView() {
        appointmentTimeGridAdapter.clear();
        appointmentTimeGridAdapter.notifyDataSetChanged();
    }

    public void bookNewAppointment(View view) {
        System.out.println(">>>>>>>>chamber = " + selectedChamber.getName());
        System.out.println(">>>>>>>>date = " + selectedDate);
        System.out.println(">>>>>>>>time = " + selectedTime);

        if(selectedChamber == null){
            Toast.makeText(this, "Please select a chamber first",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(selectedDate == null){
            Toast.makeText(this, "Please select a date first",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(selectedTime == null){
            Toast.makeText(this, "Please select a time first",Toast.LENGTH_SHORT).show();
            return;
        }

        Appointment appointment = new Appointment();
        appointment.setPatient_id(CustomSharedPref.getInstance(this).getUserId());
        appointment.setDoctor_id(selectedChamber.getDoctor_id());
        appointment.setDoctor_chamber_id(selectedChamber.getId());
        appointment.setHospital_id(selectedChamber.getHospital_id());
        appointment.setDateTime(selectedDate, selectedTime);
        appointment.setStatus(1);

        new UserApi().bookNewAppointment(appointment, (status, message) -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }
}