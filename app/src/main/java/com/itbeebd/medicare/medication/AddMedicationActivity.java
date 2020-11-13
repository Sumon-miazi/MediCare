

package com.itbeebd.medicare.medication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.itbeebd.medicare.R;
import com.itbeebd.medicare.doctors.customCalender.DayViewContainer;
import com.itbeebd.medicare.utils.Medication;
import com.itbeebd.medicare.utils.MedicationDate;
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
    private final LocalDate today = LocalDate.now();
    private CalendarView addMedicationCalendarView;
    private TextView monthView;
    private TextView yearView;
    private ArrayList<String> times = new ArrayList<>();
    private TextInputEditText medicineName;
    private Spinner medicineTypeSpinner;
    private Spinner medicineNoteSpinner;
    private Spinner medicineAmountSpinner;
    private CheckBox morningCheckBox,noonCheckBox,nightCheckBox;
    private Button addMedicineBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        addMedicationCalendarView = findViewById(R.id.addMedicationCalendar);
        monthView = findViewById(R.id.addMedicationCalendarMonthText);
        yearView = findViewById(R.id.addMedicationYearText);
        medicineName = findViewById(R.id.medicineNameEditTxtId);
        medicineTypeSpinner = findViewById(R.id.medicineTypeSpinnerId);
        medicineNoteSpinner = findViewById(R.id.medicineNoteSpinnerId);
        medicineAmountSpinner = findViewById(R.id.medicineAmountSpinnerId);
        morningCheckBox = findViewById(R.id.morningCheckBoxId);
        noonCheckBox = findViewById(R.id.noonCheckBoxId);
        nightCheckBox = findViewById(R.id.nightCheckBoxId);
        addMedicineBtn = findViewById(R.id.addMedicineBtnId);


        initDayBinder();
        initMedicineTypeSpinner();
        initMedicineNoteSpinner();
        initMedicineAmountSpinner();

        morningCheckBox.setOnCheckedChangeListener(this::checkedChangedListener);
        noonCheckBox.setOnCheckedChangeListener(this::checkedChangedListener);
        nightCheckBox.setOnCheckedChangeListener(this::checkedChangedListener);

        addMedicineBtn.setOnClickListener(view -> addNewMedicine());
    }

    private void initMedicineTypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.blood_for_list, R.layout.single_spinner_item);
        adapter.setDropDownViewResource(R.layout.single_spinner_item);
        medicineTypeSpinner.setAdapter(adapter);
    }

    private void initMedicineNoteSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.blood_for_list, R.layout.single_spinner_item);
        adapter.setDropDownViewResource(R.layout.single_spinner_item);
        medicineNoteSpinner.setAdapter(adapter);
    }

    private void initMedicineAmountSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.blood_for_list, R.layout.single_spinner_item);
        adapter.setDropDownViewResource(R.layout.single_spinner_item);
        medicineAmountSpinner.setAdapter(adapter);
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
                            dateTxtView.setTextColor(getResources().getColor(R.color.textColor));
                            dateTxtView.setBackgroundResource(R.drawable.circle_bg_for_date);
                        } else {
                            dateTxtView.setTextColor(getResources().getColor(R.color.textColor));
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

    private void checkedChangedListener(CompoundButton c, boolean b){
        System.out.println(">>>>>>>>> " + c.getText().toString());
        if(b) times.add(c.getText().toString());
        else times.remove(c.getText().toString());
        System.out.println(times.toString());
    }

    private void addNewMedicine(){
        if(selectedDates.isEmpty()){
            Toast.makeText(this, "Select date first", Toast.LENGTH_SHORT).show();
            return;
        }

        if(medicineName.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter medicine name first", Toast.LENGTH_SHORT).show();
            return;
        }
        /*
        private String medicineName;
        private String medicineType;
        private String time;
        private String note;
        private String medicationAmount;
         */

        Medication medication = new Medication(
                medicineName.getText().toString(),
                medicineTypeSpinner.getSelectedItem().toString(),
                times.toString(),
                medicineAmountSpinner.getSelectedItem().toString(),
                medicineNoteSpinner.getSelectedItem().toString());

        medication.save();

        for(int i = 0; i < selectedDates.size(); i++){
            MedicationDate medicationDate = new MedicationDate(selectedDates.get(i).toString(), medication);
            medicationDate.save();
        }

        Toast.makeText(this,"Medicine added successfully", Toast.LENGTH_SHORT).show();
    }
}