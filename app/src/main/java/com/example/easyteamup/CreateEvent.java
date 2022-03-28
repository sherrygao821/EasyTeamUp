package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.easyteamup.classes.Event;
import com.example.easyteamup.classes.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner newEvtTypePicker;
    CheckBox newEvtPrivate, newEvtPublic;
    EditText newEvtName, newEvtInvitations;
    Button evtDueTimePicker, evtDueDatePicker;

    private Context context;
    private Event event;
    private int evtDueYear;
    private int evtDueMonth;
    private int evtDueDay;
    private int evtDueHour;
    private int evtDueMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        event = new Event();
        context = this;

        newEvtTypePicker = findViewById(R.id.newEvtTypePicker);
        newEvtPrivate = findViewById(R.id.newEvtPrivate);
        newEvtPublic = findViewById(R.id.newEvtPublic);
        newEvtName = findViewById(R.id.newEvtName);
        newEvtInvitations = findViewById(R.id.newEvtInvitations);
        evtDueTimePicker = findViewById(R.id.evtDueTimePicker);
        evtDueDatePicker = findViewById(R.id.evtDueDatePicker);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.evtTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        newEvtTypePicker.setAdapter(adapter);

        newEvtTypePicker.setOnItemSelectedListener(this);
        newEvtPrivate.setOnClickListener(this::onCheckboxClicked);
        newEvtPublic.setOnClickListener(this::onCheckboxClicked);
        evtDueDatePicker.setOnClickListener(this::handleDueDate);
        evtDueTimePicker.setOnClickListener(this::handleDueTime);
    }

    /**
     * handle Event Due Date Picker
     * @param view
     * @author Sherry Gao
     */
    public void handleDueDate(View view) {

        // get current date
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // initiate new DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if(i < year || (i == year && i1 < month) || (i == year && i1 == month && i2 < day))
                    Toast.makeText(context, "Please Select A Valid Date", Toast.LENGTH_SHORT).show();
                else {
                    evtDueYear = i;
                    evtDueMonth = i1;
                    evtDueDay = i2;
                    String evtDueDateString = String.valueOf(evtDueYear) + "-" + String.valueOf(evtDueMonth) + "-" + String.valueOf(evtDueDay);
                    evtDueDatePicker.setText(evtDueDateString);
                }
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    /**
     * handle Event Due Time Picker
     * @param view
     * @author Sherry Gao
     */
    public void handleDueTime(View view) {

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                evtDueHour = i;
                evtDueMinute = i1;
                String evtDueTimeString = String.valueOf(evtDueHour) + ":" + String.valueOf(evtDueMinute);
                evtDueTimePicker.setText(evtDueTimeString);
            }
        }, hour, minute, true);

        timePickerDialog.show();
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