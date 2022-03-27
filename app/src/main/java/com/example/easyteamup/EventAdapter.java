package com.example.easyteamup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easyteamup.classes.Event;

import java.util.*;

public class EventAdapter extends ArrayAdapter<Event> {

    private Context context;
    private int resourceLayout;

    public EventAdapter(@NonNull Context context, int resource, List<Event> events) {
        super(context, resource, events);
        this.resourceLayout = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(resourceLayout, null);
        }

        Event e = getItem(position);

        if (e != null) {
            TextView evtUserName = (TextView) v.findViewById(R.id.evtUserName);
            ImageView evtUserPic = (ImageView) v.findViewById(R.id.evtUserPic);
            TextView evtType = (TextView) v.findViewById(R.id.evtType);
            TextView evtName = (TextView) v.findViewById(R.id.evtName);

            if (evtUserName != null) {
                evtUserName.setText(String.valueOf(e.getHostId()));
            }

//            if (evtUserPic != null) {
//                evtUserPic.setImageResource(e.get);
//            }

            if (evtType != null) {
                evtType.setText(String.valueOf(e.getEvtType()));
            }

            if(evtName != null) {
                evtName.setText(String.valueOf(e.getEvtName()));
            }
        }

        return v;
    }
}
