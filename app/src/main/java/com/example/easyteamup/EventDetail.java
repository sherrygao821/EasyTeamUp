package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.easyteamup.classes.Event;
import com.google.gson.Gson;

public class EventDetail extends AppCompatActivity {

    private Event event;

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

        setEventInfo();
    }

    private void setEventInfo() {

    }
}