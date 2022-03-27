package com.example.easyteamup.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.easyteamup.EventAdapter;
import com.example.easyteamup.EventDetail;
import com.example.easyteamup.R;
import com.example.easyteamup.classes.*;
import com.google.gson.Gson;

import java.util.*;

public class Events extends Fragment {

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
        ListView eventsListView = rootView.findViewById(R.id.eventsListView);
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
                String eventInfo = new Gson().toJson(allEvents.get(position - 1));
                intent.putExtra("eventInfo", eventInfo);
                startActivity(intent);
            }
        });

        return rootView;
    }

    /**
     * Get the list of events available from the database
     * @author Sherry Gao
     */
    private void getEventList() {
        // TODO: Connect with Database
        for(int i = 0; i < 5; i++) {
            Event e = new Event(i, "Run On the Beach", 1,0);
            allEvents.add(e);
        }
    }
}