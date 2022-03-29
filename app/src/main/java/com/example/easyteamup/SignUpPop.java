package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class SignUpPop extends AppCompatActivity {

    TextView popEvtName;
    ImageView popClose;
    Button popSignUp;
    ListView selectTimeSlots;

    ArrayAdapter<String> timeslotsAdapter;

    DatabaseHelper db;

    private Event event;
    private List<String> selectedTimeSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_pop);

        // get event info send from home page
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String eventInfo = extras.getString("eventInfo");
            event = new Gson().fromJson(eventInfo, Event.class);
        }

        selectedTimeSlots = new ArrayList<>();

        popClose = findViewById(R.id.popClose);
        popSignUp = findViewById(R.id.popSignUp);
        popEvtName = findViewById(R.id.popEvtName);
        selectTimeSlots = findViewById(R.id.selectTimeSlots);

        // set time slots adapter
        List<String> timeslots = new ArrayList<>();
        Map<String, Integer> map = event.getEvtTimeSlots();
        for (Map.Entry<String,Integer> entry : map.entrySet()) {
            timeslots.add(entry.getKey());
        }

        timeslotsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timeslots);
        selectTimeSlots.setAdapter(timeslotsAdapter);

        selectTimeSlots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!selectedTimeSlots.contains(timeslots.get(position))) {
                    selectedTimeSlots.add(timeslots.get(position));
                }
            }
        });

        db = new DatabaseHelper(this);

        setEventInfo();

        popClose.setOnClickListener(this::closeOnClick);
        popSignUp.setOnClickListener(this::signUpOnClick);

    }

    /**
     * Assign event information detail to each textview
     * @author Sherry Gao
     */
    private void setEventInfo() {
        popEvtName.setText(event.getEvtName());
    }

    /**
     * Close the pop up
     * @param v
     * @author Sherry Gao
     */
    private void closeOnClick(View v) {
        switchActivity("Detail");
    }

    /**
     * Add user to event participant list
     * @param v
     * @author Sherry Gao
     */
    private void signUpOnClick(View v) {
        String userEmail = ((MyApplication) this.getApplication()).getUser().getEmail();
        Map<String, Integer> map = event.getEvtTimeSlots();
        for(String s : selectedTimeSlots) {
            if(map.containsKey(s)) {
                map.put(s, map.get(s) + 1);
            }
        }
        boolean result = db.signUpEvent(event.getEvtId(), userEmail, map);
        // sign up successfully
        if(result) {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            switchActivity("Home");
        }
        // sign up failed
        else {
            Toast.makeText(this, "This Event Does Not Exist!", Toast.LENGTH_SHORT).show();
            switchActivity("Home");
        }
    }

    /**
     * Switch back to another activity
     * @author Sherry Gao
     */
    private void switchActivity(String page) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}