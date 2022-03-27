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

public class SignUpPop extends AppCompatActivity {

    TextView popEvtName;
    ImageView popClose;
    Button popSignUp;

    DatabaseHelper db;

    private Event event;

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

        popClose = findViewById(R.id.popClose);
        popSignUp = findViewById(R.id.popSignUp);
        popEvtName = findViewById(R.id.popEvtName);
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
        // TODO:assign time slots listview info
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
        boolean result = db.signUpEvent(event.getEvtId(), userEmail);
        // sign up successfully
        if(result) {
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            switchActivity("Detail");
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
        if(page.equals("Detail")) {
            Intent intent = new Intent(getApplicationContext(), EventDetail.class);
            String eventString = new Gson().toJson(event);
            intent.putExtra("eventInfo", eventString);
            startActivity(intent);
        }
        else if(page.equals("Home")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}