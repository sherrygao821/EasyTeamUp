package com.example.easyteamup.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyteamup.EventListAdapter;
import com.example.easyteamup.EventModel;
import com.example.easyteamup.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * Event history fragment of the profile page
 * @author Lucy Shi
 */
public class EventHistoryFragment extends Events {

    RecyclerView recyclerView;
    ArrayList<EventModel> list;

    public EventHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_event_history, container, false);

//        recyclerView = view.findViewById(R.id.eventHistoryRV);
//        list = new ArrayList<>();
//        list.add(new EventModel(R.drawable.profile, "Beach", "TBD", "sports", "Gaga"));
//        list.add(new EventModel(R.drawable.profile, "Beach", "TBD", "sports", "Gaga"));
//
//        EventListAdapter adapter = new EventListAdapter(list, getContext());
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);

//        return view;
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}