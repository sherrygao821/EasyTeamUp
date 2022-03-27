package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyteamup.classes.Event;
import com.google.gson.Gson;

import java.util.List;

public class EventDetail extends AppCompatActivity {

    private Event event;

    TextView evtDetailUserName, evtDeadline, evtDetailType, evtDetailDescript, evtDetailLoc, evtDetailNoP, evtDetailEmail;
    ImageView evtDetailUserPic;
    Button signUpButton;


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
        signUpButton = findViewById(R.id.signUpButton);

        setEventInfo();

        signUpButton.setOnClickListener(this::onClick);
    }


    /**
     * On click function for the sign up button to initiate sign up pop up
     * @param v
     * @author Sherry Gao
     */
    private void onClick(View v) {

        List<String> participants = event.getEvtParticipants();
        String userEmail = (((MyApplication) this.getApplication()).getUser().getEmail());

        // user already signed up
        if(participants.contains(userEmail)) {
            Toast.makeText(this, "You Have Already Signed Up!", Toast.LENGTH_SHORT).show();
        }
        // open the sign up pop up
        else {
            Intent intent = new Intent(this, SignUpPop.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);

            String eventString = new Gson().toJson(event);
            intent.putExtra("eventInfo", eventString);

            startActivity(intent);
        }
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