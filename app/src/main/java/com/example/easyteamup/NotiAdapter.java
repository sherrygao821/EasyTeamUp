package com.example.easyteamup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easyteamup.classes.Event;
import com.example.easyteamup.classes.Notification;

import java.util.List;

public class NotiAdapter extends ArrayAdapter<Notification> {

    private Context context;
    private int resourceLayout;
    private DatabaseHelper db;

    public NotiAdapter(@NonNull Context context, int resource, List<Notification> noti) {
        super(context, resource, noti);
        this.resourceLayout = resource;
        this.context = context;
        this.db = new DatabaseHelper(this.context);

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

        //TODO: REMOVE TESTING
        //db.addNoti(new Notification(0,1,2,0));
//        db.addNoti(new Notification(0,1,2,1));
//        db.addNoti(new Notification(0,1,2,3));
//        db.addTempEvent(new Event(0,"new event",0,0));

        //TESTING ENDS


        Notification n = getItem(position);


        if (n != null) {
            TextView fromUser = (TextView) v.findViewById(R.id.notiFromUser);
            TextView description = (TextView) v.findViewById(R.id.notiDescription);
            TextView notiEvtName = (TextView) v.findViewById(R.id.notiEventName);

            if (n.getType() != 2){
                if (fromUser != null){
                    if (n.getType() == 3){
                         fromUser.setText(R.string.timeTerminated);
                    } else {
                        Log.d("noti", String.valueOf(n.getFrom()));
                         fromUser.setText(db.getUserNameById(n.getFrom()));
                    }
                }

                if(description != null){
                    if (n.getType() == 0) description.setText(R.string.withdrawDescription);
                    else if (n.getType() == 1) description.setText(R.string.notijoinevent);
                    else if (n.getType() == 3) description.setText(R.string.foryourevent);
                }

                if(notiEvtName != null){
                    notiEvtName.setText(db.getEvtNamebyID(n.getEventID()));
                }

            }

        }



        return v;
    }
}
