package com.example.easyteamup.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyteamup.EventListAdapter;
import com.example.easyteamup.EventModel;
import com.example.easyteamup.R;

import java.util.ArrayList;

/**
 * My event fragment of the profile page
 * @author Lucy Shi
 */
public class MyEventFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<EventModel> list;

    public MyEventFragment() {
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
        View view = inflater.inflate(R.layout.fragment_my_event, container, false);

        recyclerView = view.findViewById(R.id.myEventRV);
        list = new ArrayList<>();
        list.add(new EventModel(R.drawable.ic_user, "Beach", "TBD", "sports", "Gaga"));
        list.add(new EventModel(R.drawable.ic_user, "Beach", "TBD", "sports", "Gaga"));

        EventListAdapter adapter = new EventListAdapter(list, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}