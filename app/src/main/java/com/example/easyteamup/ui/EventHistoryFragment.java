package com.example.easyteamup.ui;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.easyteamup.DatabaseHelper;
import com.example.easyteamup.EventAdapter;
import com.example.easyteamup.EventDetail;
import com.example.easyteamup.EventListAdapter;
import com.example.easyteamup.EventModel;
import com.example.easyteamup.MyApplication;
import com.example.easyteamup.R;
import com.example.easyteamup.classes.Event;
import com.example.easyteamup.classes.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * Event history fragment of the profile page
 * @author Lucy Shi
 */
public class EventHistoryFragment extends Fragment {

    ListView eventsListView;
    DatabaseHelper db;
    private List<Event> allEvents;

    public EventHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.allEvents = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event_history, container, false);
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
                intent.putExtra("isTest", false);
                startActivity(intent);
            }
        });

        return rootView;
    }

    /**
     * Get the list of event history from the database
     * @author Lucy Shi
     */
    private void getEventList() {
        int myId = ((MyApplication) getActivity().getApplication()).getUser().getUserId();
        allEvents = db.getMyEventHistory(myId);

        if(allEvents.size() == 0)
            makeText(getActivity(), "No Available Events! Please Join an Event or Create One!", Toast.LENGTH_SHORT).show();

        for(Event e : allEvents) {
            int userId = e.getHostId();
            String hostEmail = db.getUserEmail(userId);
            e.setHostEmail(hostEmail);
        }
    }
}