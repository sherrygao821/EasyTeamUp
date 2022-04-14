package com.example.easyteamup.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.easyteamup.DatabaseHelper;
import com.example.easyteamup.EventDetail;
import com.example.easyteamup.InviteAdapter;
import com.example.easyteamup.MyApplication;
import com.example.easyteamup.NotiAdapter;
import com.example.easyteamup.R;
import com.example.easyteamup.classes.Notification;
import com.example.easyteamup.classes.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class Notifications extends Fragment {

    private List<Notification> notiList;
    private List<Notification> invList;
    private DatabaseHelper db;

    public Notifications() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.notiList = new ArrayList<>();
        this.invList = new ArrayList<>();
        this.db = new DatabaseHelper(this.getContext());
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

        //TODO: REMOVE FOR TESTING
        //addNotiTest();
        //addInvTest();
        //END TESTING

        //CORRECT VERSION:
        notiList = db.getNotification((((MyApplication) this.getActivity().getApplication()).getUser()));
        invList = db.getInvitations((((MyApplication) this.getActivity().getApplication()).getUser()));



        notificationListView.setAdapter(new NotiAdapter(getContext(), R.layout.item_notification, notiList));
        ListView invListView = rootView.findViewById(R.id.invlistview);
        invListView.setAdapter(new InviteAdapter(getContext(),R.layout.item_noti_inv,invList));

        return rootView;
    }

    private void addNotiTest(){
        this.notiList.add(new Notification(0,1,2,0));
        this.notiList.add(new Notification(0,1,2,1));
        this.notiList.add(new Notification(0,1,2,3));
    }

    private void addInvTest(){
        this.invList.add(new Notification(0,1,2,2));
    }


}