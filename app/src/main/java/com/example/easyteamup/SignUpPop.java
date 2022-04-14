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

            if(!extras.getBoolean("isTest")) {
                // set time slots adapter
                selectedTimeSlots = new ArrayList<>();
                selectTimeSlots = findViewById(R.id.selectTimeSlots);
                List<String> timeslots = new ArrayList<>();
                Map<String, Integer> map = event.getEvtTimeSlots();
                for (Map.Entry<String,Integer> entry : map.entrySet()) {
                    timeslots.add(entry.getKey());
                }

                timeslotsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, timeslots);
                selectTimeSlots.setAdapter(timeslotsAdapter);

                selectTimeSlots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(!selectedTimeSlots.contains(timeslots.get(position))) {
                            selectedTimeSlots.add(timeslots.get(position));
                        }
                        else {
                            selectedTimeSlots.remove(timeslots.get(position));
                        }
                    }
                });

                popSignUp = findViewById(R.id.popSignUp);
                popEvtName = findViewById(R.id.popEvtName);

                setEventInfo();

                popSignUp.setOnClickListener(this::signUpOnClick);
            }
        }

        db = new DatabaseHelper(this);

    }

    /**
     * Assign event information detail to each textview
     * @author Sherry Gao
     */
    private void setEventInfo() {
        popEvtName.setText("Event Name: " + event.getEvtName());
    }

    /**
     * Add user to event participant list
     * @param v
     * @author Sherry Gao
     */
    private void signUpOnClick(View v) {
        int userId = ((MyApplication) this.getApplication()).getUser().getUserId();
        Map<String, Integer> map = event.getEvtTimeSlots();
        for(String s : selectedTimeSlots) {
            if(map.containsKey(s)) {
                map.put(s, map.get(s) + 1);
            }
        }

        boolean res = signUpEvent(event.getEvtId(), userId, map);
        if(res) {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            switchActivity("Home");
        }
        else {
            Toast.makeText(this, "This Event Does Not Exist!", Toast.LENGTH_SHORT).show();
            switchActivity("Home");
        }
    }

    public boolean signUpEvent(int evtId, int userId, Map<String, Integer> map) {

        boolean result = db.signUpEvent(evtId, userId, map);
        return result;
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