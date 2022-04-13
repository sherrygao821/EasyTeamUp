package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.easyteamup.classes.Time;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateEvent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner newEvtTypePicker;
    CheckBox newEvtPrivate, newEvtPublic;
    EditText newEvtName, newEvtInvitations, newEvtDescript, evtDurationHours, evtLocation;
    Button evtSubmit, evtDueDatePicker, evtTimeSlotDatePicker, evtTimeSlotTimePickerSubmit;
    ListView timeslotsLv;

    ArrayAdapter<String> timeslotsAdapter;

    DatabaseHelper db;

    private Context context;

    // new event parameters
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
    private List<String> timeslotsString;

    private boolean isPublic;
    private int evtType;
    private String evtName;
    private String evtDescript;
    private int hostId;
    private String location;

    private Event currEvt;
    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        // initialization
        context = this;
        timeslotsString = new ArrayList<>();
        db = new DatabaseHelper(this);

        // get info passed
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String eventInfo = extras.getString("eventInfo");
            currEvt = new Gson().fromJson(eventInfo, Event.class);
            isEdit = true;
        }
        else {
            isEdit = false;
        }

        newEvtTypePicker = findViewById(R.id.newEvtTypePicker);
        newEvtPrivate = findViewById(R.id.newEvtPrivate);
        newEvtPublic = findViewById(R.id.newEvtPublic);
        newEvtName = findViewById(R.id.newEvtName);
        newEvtInvitations = findViewById(R.id.newEvtInvitations);
        evtDueDatePicker = findViewById(R.id.evtDueDatePicker);
        timeslotsLv = findViewById(R.id.timeslotsLv);
        evtTimeSlotDatePicker = findViewById(R.id.evtTimeSlotDatePicker);
        evtTimeSlotTimePickerSubmit = findViewById(R.id.evtTimeSlotTimePickerSubmit);
        evtSubmit = findViewById(R.id.evtSubmit);
        newEvtDescript = findViewById(R.id.newEvtDescript);
        evtDurationHours = findViewById(R.id.evtDurationHours);
        evtLocation = findViewById(R.id.evtLocation);

        // event type listview adapter initialization
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.evtTypes, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        newEvtTypePicker.setAdapter(typeAdapter);

        // event time slots listview adapter initialization
        timeslotsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timeslotsString);
        timeslotsLv.setAdapter(timeslotsAdapter);
        timeslotsLv.setEnabled(false);

        newEvtPublic.setChecked(true);
        newEvtPrivate.setChecked(false);
        isPublic = true;

        // set button listeners
        newEvtTypePicker.setOnItemSelectedListener(this);
        newEvtPrivate.setOnClickListener(this::onCheckboxClicked);
        newEvtPublic.setOnClickListener(this::onCheckboxClicked);
        evtDueDatePicker.setOnClickListener(this::handleDueTime);
        evtTimeSlotDatePicker.setOnClickListener(this::handleTimeSlotDate);
        evtTimeSlotTimePickerSubmit.setOnClickListener(this::submitTimeSlotTime);
        evtSubmit.setOnClickListener(this::submitEvent);

        if(isEdit) {
            setCurrEvtInfo();
        }
    }

    /**
     * if in edit mode, set current
     */
    private void setCurrEvtInfo() {
        newEvtTypePicker.setSelection(currEvt.getEvtType());
        if(currEvt.isPublic()) {
            newEvtPublic.setChecked(true);
            newEvtPrivate.setChecked(false);
            isPublic = currEvt.isPublic();
        }
        newEvtName.setText(currEvt.getEvtName());
        newEvtDescript.setText(currEvt.getEvtDescription());
        evtDurationHours.setText(currEvt.getEvtDuration());
        evtLocation.setText(currEvt.getEvtLocation());
        evtDueDatePicker.setText(currEvt.getEvtSignUpDueDate());

        evtDueTimeString = currEvt.getEvtSignUpDueDate();
    }

    private boolean isAllFilledIn() {
        if(newEvtDescript.getText().toString().equals("")) return false;
        if(evtLocation.getText().toString().equals("")) return false;
        if(newEvtName.getText().toString().equals("")) return false;
        if(evtDurationHours.getText().toString().equals("")) return false;
        if(timeslotsString.size() == 0) return false;
        if(evtDueTimeString.equals("")) return false;

        return true;
    }

    /**
     * submit new event
     * @param view
     * @author Sherry Gao
     */
    public void submitEvent(View view) {
        if(!isAllFilledIn()) {
            Toast.makeText(this, "Please Fill In Every Field!", Toast.LENGTH_SHORT).show();
            return;
        }
        // collect all event construction params
        hostId = ((MyApplication) this.getApplication()).getUser().getUserId();
        evtDescript = newEvtDescript.getText().toString();
        location = evtLocation.getText().toString();
        evtName = newEvtName.getText().toString();
        String evtDuration = evtDurationHours.getText().toString();

        Map<String, Integer> timeSlotsMap = new HashMap<>();
        for(String s : timeslotsString) {
            timeSlotsMap.put(s, 0);
        }

        int hostId = ((MyApplication) this.getApplication()).getUser().getUserId();
        List<Integer> participants = new ArrayList<>();
        participants.add(hostId);

        List<Integer> inviteesList = new ArrayList<>();
        String inviteList = newEvtInvitations.getText().toString();
        // if user entered invitees
        if(!inviteList.equals("")) {
            String[] invitees = inviteList.split(";");
            for (String s : invitees) {
                int userId = db.getUserIdByEmail(s);
                if(userId != -1)
                    inviteesList.add(userId);
            }
        }

        // save info back to db
        boolean finished = false;

        if(isEdit) {
            currEvt.setEvtType(evtType);
            currEvt.setEvtName(evtName);
            currEvt.setEvtDescription(evtDescript);
            currEvt.setEvtDuration(evtDuration);
            currEvt.setEvtLocation(location);
            currEvt.setPublic(isPublic);
            currEvt.setEvtSignUpDueDate(evtDueTimeString);
            currEvt.setEvtTimeSlots(timeSlotsMap);

            if (db.addEvent(currEvt, inviteesList, isEdit))
                finished = true;
        }
        else {
            Event event;
            event = new Event(evtName, hostId, evtDescript, evtDueTimeString, location, timeSlotsMap, participants, evtType, true, isPublic, evtDuration);

            if (db.addEvent(event, inviteesList, isEdit))
                finished = true;
        }

        if(finished) {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "Try Again Please!", Toast.LENGTH_SHORT).show();
    }

    /**
     * submit new time slot and add to listview
     * @param view
     * @author Sherry Gao
     */
    public void submitTimeSlotTime(View view) {
        if(evtTimeSlotString.equals("")) {
            Toast.makeText(this, "Select A Time First!", Toast.LENGTH_SHORT).show();
            return;
        }
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
        int month = calendar.get(Calendar.MONTH);
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
                            evtTimeSlotString = String.valueOf(slotYear) + "-" + String.valueOf(slotMonth + 1) + "-" + String.valueOf(slotDay) + "-" + String.valueOf(slotHour) + ":" + String.valueOf(slotMinute);
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
        int month = calendar.get(Calendar.MONTH);
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
                            evtDueTimeString = String.valueOf(evtDueYear) + "-" + String.valueOf(evtDueMonth + 1) + "-" + String.valueOf(evtDueDay) + "-" + String.valueOf(evtDueHour) + ":" + String.valueOf(evtDueMinute);
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
        evtType = i;
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
                    isPublic = false;
                    newEvtPublic.setChecked(false);
                }
                else {
                    isPublic = true;
                    newEvtPublic.setChecked(true);
                }
                break;
            case R.id.newEvtPublic:
                if(checked) {
                    isPublic = true;
                    newEvtPrivate.setChecked(false);
                }
                else {
                    isPublic = false;
                    newEvtPrivate.setChecked(true);
                }
                break;
        }
    }
}