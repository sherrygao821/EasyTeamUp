package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyteamup.classes.Event;
import com.google.gson.Gson;

public class EventDetail extends AppCompatActivity {

    private Event event;

    TextView evtDetailUserName, evtDeadline, evtDetailType, evtDetailDescript, evtDetailLoc, evtDetailNoP, evtDetailEmail;
    ImageView evtDetailUserPic;


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

        evtDetailUserName = findViewById(R.id.evtDetailUserName);
        evtDeadline = findViewById(R.id.evtDeadline);
        evtDetailType = findViewById(R.id.evtDetailType);
        evtDetailDescript = findViewById(R.id.evtDetailDescript);
        evtDetailLoc = findViewById(R.id.evtDetailLoc);
        evtDetailNoP = findViewById(R.id.evtDetailNoP);
        evtDetailEmail = findViewById(R.id.evtDetailEmail);
        evtDetailUserPic = findViewById(R.id.evtDetailUserPic);

        setEventInfo();
    }

    /**
     * Assign event information detail to each textview
     * @author Sherry Gao
     */
    private void setEventInfo() {

        String email = ((MyApplication) this.getApplication()).getUser().getEmail();
        Log.d("GLOBAL_VARIABLE", new Gson().toJson(((MyApplication) this.getApplication()).getUser()));

        evtDetailUserName.setText(email);
        evtDeadline.setText(event.getEvtSignUpDueDate());
        evtDetailType.setText(String.valueOf(event.getEvtType()));
        evtDetailDescript.setText(event.getEvtDescription());
        evtDetailLoc.setText(event.getEvtLocation());
        evtDetailNoP.setText(String.valueOf(event.getEvtParticipants().size()));
        evtDetailEmail.setText(email);
//        evtDetailUserPic.setImageResource();
    }
}