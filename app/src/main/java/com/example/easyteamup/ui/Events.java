package com.example.easyteamup.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.easyteamup.EventAdapter;
import com.example.easyteamup.R;
import com.example.easyteamup.classes.*;

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

        getEventList();

    }

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