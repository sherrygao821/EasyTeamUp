package com.example.easyteamup.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.easyteamup.CreateEvent;
import com.example.easyteamup.EventAdapter;
import com.example.easyteamup.EventDetail;
import com.example.easyteamup.R;
import com.example.easyteamup.classes.*;
import com.google.gson.Gson;

import java.util.*;

public class Events extends Fragment {

    ListView eventsListView;
    ImageView addEventButton;

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

        // Add adapter to the home page list view
        eventsListView = rootView.findViewById(R.id.eventsListView);
        eventsListView.addHeaderView(new View(getActivity()));
        eventsListView.addFooterView(new View(getActivity()));
        getEventList();
        eventsListView.setAdapter(new EventAdapter(getActivity(), R.layout.item_event, allEvents));

        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Log.d("EVENT_CLICK", String.valueOf(position));

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
        // TODO: Connect with Database
        for(int i = 0; i < 5; i++) {
            Event e = new Event(i, "Run On the Beach " + String.valueOf(i), 0, "I really want to run on the beach, but I could not find someone who also wants to run on the beach.", "11:59PM 3/31", null, "Santa Monica Beach", new HashMap<String, Integer>(), new ArrayList<String>(), 0, true, true);
            allEvents.add(e);
        }
    }
}