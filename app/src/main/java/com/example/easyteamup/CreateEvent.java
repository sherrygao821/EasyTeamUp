package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.easyteamup.classes.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner newEvtTypePicker;
    CheckBox newEvtPrivate, newEvtPublic;
    EditText newEvtName, newEvtInvitations;
    Button evtDueDatePicker, evtTimeSlotDatePicker, evtTimeSlotTimePickerSubmit;
    ListView timeslotsLv;

    ArrayAdapter<String> timeslotsAdapter;

    private Context context;
    private Event event;
    private List<String> timeslotsString;

    private int evtDueYear;
    private int evtDueMonth;
    private int evtDueDay;
    private int evtDueHour;
    private int evtDueMinute;
    String evtDueTimeString = "";

    private int slotYear;
    private int slotMonth;
    private int slotDay;
    private int slotHour;
    private int slotMinute;
    String evtTimeSlotString = "";

    public CreateEvent() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // initialization
        event = new Event();
        context = this;
        timeslotsString = new ArrayList<>();

        newEvtTypePicker = findViewById(R.id.newEvtTypePicker);
        newEvtPrivate = findViewById(R.id.newEvtPrivate);
        newEvtPublic = findViewById(R.id.newEvtPublic);
        newEvtName = findViewById(R.id.newEvtName);
        newEvtInvitations = findViewById(R.id.newEvtInvitations);
        evtDueDatePicker = findViewById(R.id.evtDueDatePicker);
        timeslotsLv = findViewById(R.id.timeslotsLv);
        evtTimeSlotDatePicker = findViewById(R.id.evtTimeSlotDatePicker);
        evtTimeSlotTimePickerSubmit = findViewById(R.id.evtTimeSlotTimePickerSubmit);

        // event type listview adapter initialization
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.evtTypes, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        newEvtTypePicker.setAdapter(typeAdapter);

        // event time slots listview adapter initialization
        timeslotsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timeslotsString);
        timeslotsLv.setAdapter(timeslotsAdapter);
        timeslotsLv.setEnabled(false);

        // set button listeners
        newEvtTypePicker.setOnItemSelectedListener(this);
        newEvtPrivate.setOnClickListener(this::onCheckboxClicked);
        newEvtPublic.setOnClickListener(this::onCheckboxClicked);
        evtDueDatePicker.setOnClickListener(this::handleDueTime);
        evtTimeSlotDatePicker.setOnClickListener(this::handleTimeSlotDate);
        evtTimeSlotTimePickerSubmit.setOnClickListener(this::submitTimeSlotTime);
    }

    /**
     * submit new time slot and add to listview
     * @param view
     * @author Sherry Gao
     */
    public void submitTimeSlotTime(View view) {
        if(timeslotsString.contains(evtTimeSlotString)) {
            Toast.makeText(this, "You Already Added This Start Time", Toast.LENGTH_SHORT).show();
            return;
        }
        timeslotsString.add(evtTimeSlotString);
        timeslotsAdapter.notifyDataSetChanged();

        // adjust height for listview
        int itemCount = timeslotsAdapter.getCount();
        ViewGroup.LayoutParams params = timeslotsLv.getLayoutParams();
        params.height = (itemCount * 130);
        timeslotsLv.setLayoutParams(params);
        timeslotsLv.requestLayout();
    }

    /**
     * handle Event Time Slot Date/Time Picker
     * @param view
     * @author Sherry Gao
     */
    public void handleTimeSlotDate(View view) {
        // get current date
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        if(evtDueTimeString.equals("")) {
            Toast.makeText(this, "Please Select Event Sign Up Due Time First!", Toast.LENGTH_SHORT).show();
            return;
        }

        // initiate new DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if(i < evtDueYear || (i == evtDueYear && i1 < evtDueMonth) || (i == evtDueYear && i1 == evtDueMonth && i2 <= evtDueDay))
                    Toast.makeText(context, "Please Select A Valid Date", Toast.LENGTH_SHORT).show();
                else {
                    slotYear = i;
                    slotMonth = i1;
                    slotDay = i2;

                    // initiate time picker after selected a valid date
                    TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            slotHour = i;
                            slotMinute = i1;
                            evtTimeSlotString = String.valueOf(slotYear) + "-" + String.valueOf(slotMonth) + "-" + String.valueOf(slotDay) + " " + String.valueOf(slotHour) + ":" + String.valueOf(slotMinute);
                            evtTimeSlotDatePicker.setText(evtTimeSlotString);
                        }
                    }, hour, minute, true);

                    timePickerDialog.show();
                }
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    /**
     * handle Event Due Date/Time Picker
     * @param view
     * @author Sherry Gao
     */
    public void handleDueTime(View view) {

        // get current date
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        // initiate new DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if(i < year || (i == year && i1 < month) || (i == year && i1 == month && i2 <= day))
                    Toast.makeText(context, "Please Select A Valid Date", Toast.LENGTH_SHORT).show();
                else {
                    evtDueYear = i;
                    evtDueMonth = i1;
                    evtDueDay = i2;

                    // initiate time picker after selected a valid date
                    TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            evtDueHour = i;
                            evtDueMinute = i1;
                            evtDueTimeString = String.valueOf(evtDueYear) + "-" + String.valueOf(evtDueMonth) + "-" + String.valueOf(evtDueDay) + " " + String.valueOf(evtDueHour) + ":" + String.valueOf(evtDueMinute);
                            evtDueDatePicker.setText(evtDueTimeString);
                        }
                    }, hour, minute, true);

                    timePickerDialog.show();
                }
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    /**
     * handle event type selection
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        event.setEvtType(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * On click function for event category
     * @param view
     * @author Sherry Gao
     */
    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.newEvtPrivate:
                if(checked) {
                    event.setEvtType(0);
                    newEvtPublic.setChecked(false);
                }
                else {
                    event.setEvtType(1);
                    newEvtPublic.setChecked(true);
                }
                break;
            case R.id.newEvtPublic:
                if(checked) {
                    event.setEvtType(1);
                    newEvtPrivate.setChecked(false);
                }
                else {
                    event.setEvtType(0);
                    newEvtPrivate.setChecked(true);
                }
                break;
        }
    }
}