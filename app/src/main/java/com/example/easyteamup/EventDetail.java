package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyteamup.classes.Event;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventDetail extends AppCompatActivity {

    private Event event;

    TextView evtDetailUserName, evtDeadline, evtDetailType, evtDetailDescript, evtDetailLoc, evtDetailNoP, evtDetailEmail, evtDetailDuration;
    ImageView evtDetailUserPic;
    Button signUpButton, withdrawButton, determineTimeButton, EditEventButton;
    ListView showTimeSlots;

    DatabaseHelper db;

    ArrayAdapter<String> timeslotsAdapter;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // get event info send from home page
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String eventInfo = extras.getString("eventInfo");
            event = new Gson().fromJson(eventInfo, Event.class);
        }

        setPageInfo();

        db = new DatabaseHelper(this);

        // initialize time slots list for adapter
        List<String> timeslots = new ArrayList<>();
        Map<String, Integer> map = event.getEvtTimeSlots();
        for (Map.Entry<String,Integer> entry : map.entrySet()) {
            timeslots.add(entry.getKey());
        }

        timeslotsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timeslots);
        showTimeSlots.setAdapter(timeslotsAdapter);
        showTimeSlots.setEnabled(false);

        setEventInfo();
        checkEventOptions();
    }

    /**
     * determine the best time for this event if host
     * @param v
     */
    public void determineTime(View v) {
        String time = db.determineTimeSlots(event.getEvtId());
        determineTimeButton.setText("Event Time is " + time);
    }

    /**
     * go to edit event page
     * @param v
     */
    public void editEvent(View v) {
        Intent intent = new Intent(this, CreateEvent.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        String eventInfo = new Gson().toJson(event);
        intent.putExtra("eventInfo", eventInfo);
        startActivity(intent);
    }

    /**
     * withdraw with this event if signed up
     * @param v
     */
    public void withdrawEvent(View v) {
        if(db.withdrawEvent(event.getEvtId(), userId)) {
            Toast.makeText(this, "Successfully Withdraw From The Event!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Try Again Please!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * On click function for the sign up button to initiate sign up pop up
     * @param v
     * @author Sherry Gao
     */
    private void onClick(View v) {
        Intent intent = new Intent(this, SignUpPop.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);

        String eventString = new Gson().toJson(event);
        intent.putExtra("eventInfo", eventString);

        startActivity(intent);
    }

    /**
     * Assign event information detail to each textview
     * @author Sherry Gao
     */
    private void setEventInfo() {
        evtDetailUserName.setText(String.valueOf(event.getHostEmail()));
        evtDeadline.setText("Sign Up Due At: " + event.getEvtSignUpDueDate());
        String[] resources = this.getResources().getStringArray(R.array.evtTypes);
        evtDetailType.setText(String.valueOf(resources[event.getEvtType()]));
        evtDetailDescript.setText(event.getEvtDescription());
        evtDetailLoc.setText(event.getEvtLocation());
        evtDetailNoP.setText(String.valueOf(event.getEvtParticipants().size()));
        evtDetailEmail.setText(String.valueOf(event.getHostEmail()));
        evtDetailDuration.setText(String.valueOf(event.getEvtDuration()));
    }

    /**
     * check whether the user is the host of the event/sign up status
     * @author Sherry Gao
     */
    private void checkEventOptions() {
        List<Integer> participants = event.getEvtParticipants();
        int userId = (((MyApplication) this.getApplication()).getUser().getUserId());
        if(userId == event.getHostId()) {
            determineTimeButton.setOnClickListener(this::determineTime);
            EditEventButton.setOnClickListener(this::editEvent);
            withdrawButton.setClickable(false);
            signUpButton.setClickable(false);
            withdrawButton.setVisibility(View.GONE);
            signUpButton.setVisibility(View.GONE);
        }
        else if(participants.contains(userId)) {
            withdrawButton.setOnClickListener(this::withdrawEvent);
            signUpButton.setClickable(false);
            determineTimeButton.setClickable(false);
            signUpButton.setVisibility(View.GONE);
            determineTimeButton.setVisibility(View.GONE);
            EditEventButton.setVisibility(View.GONE);
            EditEventButton.setClickable(false);
        }
        else {
            signUpButton.setOnClickListener(this::onClick);
            withdrawButton.setClickable(false);
            withdrawButton.setVisibility(View.GONE);
            determineTimeButton.setClickable(false);
            determineTimeButton.setVisibility(View.GONE);
            EditEventButton.setVisibility(View.GONE);
            EditEventButton.setClickable(false);
        }

        String evtDeterminedTimeSlot = db.getDeterminedTimeSlot(event.getEvtId());
        if(!evtDeterminedTimeSlot.equals("")) {
            determineTimeButton.setText("Event Time is " + evtDeterminedTimeSlot);
            determineTimeButton.setClickable(false);
        }
    }

    /**
     * link page ui elements to java file
     * @author Sherry Gao
     */
    private void setPageInfo() {
        userId = (((MyApplication) this.getApplication()).getUser().getUserId());
        evtDetailUserName = findViewById(R.id.evtDetailUserName);
        evtDeadline = findViewById(R.id.evtDeadline);
        evtDetailType = findViewById(R.id.evtDetailType);
        evtDetailDescript = findViewById(R.id.evtDetailDescript);
        evtDetailLoc = findViewById(R.id.evtDetailLoc);
        evtDetailNoP = findViewById(R.id.evtDetailNoP);
        evtDetailEmail = findViewById(R.id.evtDetailEmail);
        evtDetailUserPic = findViewById(R.id.evtDetailUserPic);
        signUpButton = findViewById(R.id.signUpButton);
        showTimeSlots = findViewById(R.id.showTimeSlots);
        withdrawButton = findViewById(R.id.withdrawButton);
        determineTimeButton = findViewById(R.id.determineTimeButton);
        EditEventButton = findViewById(R.id.EditEventButton);
        evtDetailDuration = findViewById(R.id.evtDetailDuration);
    }
}