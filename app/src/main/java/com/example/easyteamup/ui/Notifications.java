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
import android.widget.ListView;

import com.example.easyteamup.DatabaseHelper;
import com.example.easyteamup.EventDetail;
import com.example.easyteamup.NotiAdapter;
import com.example.easyteamup.R;
import com.example.easyteamup.classes.Notification;
import com.example.easyteamup.classes.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Notifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notifications extends Fragment {

    private List<Notification> notiList;

    public Notifications() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.notiList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_notifications, container, false);

        ListView notificationListView = rootView.findViewById(R.id.notilistview);

        /*
        DatabaseHelper db = new DatabaseHelper(getContext());

        List<Notification> temp = db.getNotification(1);

        for (Notification noti:temp){
            notiList.add(noti.toString());
        }
        */
        addNotiTest();
        notificationListView.setAdapter(new NotiAdapter(getContext(), R.layout.item_notification, notiList));


        return rootView;
    }

    private void addNotiTest(){
        this.notiList.add(new Notification(0,1,2,0));
        this.notiList.add(new Notification(0,1,2,1));
        this.notiList.add(new Notification(0,1,2,3));
    }
}