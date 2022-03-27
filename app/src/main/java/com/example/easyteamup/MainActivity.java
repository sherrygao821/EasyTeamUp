package com.example.easyteamup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.easyteamup.ui.Events;
import com.example.easyteamup.ui.Notifications;
import com.example.easyteamup.ui.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Events events;
    private Notifications notifications;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isLoggedIn = ((MyApplication) this.getApplication()).isLoggedIn();

        // check log in status
        if(!isLoggedIn) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            return;
        }

        // initialization of variables
        events = new Events();
        notifications = new Notifications();
        profile = new Profile();

        setNavBar();
    }

    /**
     * Implementation of navigation bar fragment change
     * @author Sherry Gao
     */
    private void setNavBar() {
        FragmentTransaction initialTransaction = getSupportFragmentManager().beginTransaction();
        initialTransaction.replace(R.id.navFrame, events).commitNow();

        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);

        navView.setOnItemSelectedListener( item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.events:
                    selectedFragment = events;
                    break;
                case R.id.notifications:
                    selectedFragment = notifications;
                    break;
                case R.id.profile:
                    selectedFragment = profile;
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.navFrame, selectedFragment).commitNow();
            return true;
        });
    }
}