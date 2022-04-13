package com.example.easyteamup.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.easyteamup.CreateEvent;
import com.example.easyteamup.DatabaseHelper;
import com.example.easyteamup.EventAdapter;
import com.example.easyteamup.EventDetail;
import com.example.easyteamup.R;
import com.example.easyteamup.classes.*;
import com.google.gson.Gson;

import java.util.*;

public class Events extends Fragment {

    ListView eventsListView;
    ImageView addEventButton;

    DatabaseHelper db;

    private List<Event> allEvents;

    public Events() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.allEvents = new ArrayList<>();
    }

    /**
     * Set Adapter for the home page list view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return rootView
     * @author Sherry Gao
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        db = new DatabaseHelper(getActivity());

        // Add adapter to the home page list view
        eventsListView = rootView.findViewById(R.id.eventsListView);
        eventsListView.addHeaderView(new View(getActivity()));
        eventsListView.addFooterView(new View(getActivity()));
        getEventList();
        eventsListView.setAdapter(new EventAdapter(getActivity(), R.layout.item_event, allEvents));

        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // initiate Event Detail Page w Event Data
                Intent intent = new Intent(getActivity(), EventDetail.class);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                String eventInfo = new Gson().toJson(allEvents.get(position - 1));
                intent.putExtra("eventInfo", eventInfo);
                startActivity(intent);
            }
        });

        addEventButton = rootView.findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(this::onClick);

        return rootView;
    }

    /**
     * Switch to create event activity
     * @param v
     * @author Sherry Gao
     */
    private void onClick(View v) {
        Intent intent = new Intent(getActivity(), CreateEvent.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    /**
     * Get the list of events available from the database
     * @author Sherry Gao
     */
    private void getEventList() {
        allEvents = db.getAllActivePublicEvents();

        if(allEvents.size() == 0)
            Toast.makeText(getActivity(), "No Available Events! Please Check Back Later or Create One!", Toast.LENGTH_SHORT).show();

        for(Event e : allEvents) {
            int userId = e.getHostId();
            String hostEmail = db.getUserEmail(userId);
            e.setHostEmail(hostEmail);
        }
    }
}