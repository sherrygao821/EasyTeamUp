package com.example.easyteamup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easyteamup.classes.Event;
import com.example.easyteamup.classes.Notification;

import java.util.List;

public class InviteAdapter extends ArrayAdapter<Notification> {

    private Context context;
    private int resourceLayout;
    private DatabaseHelper db;

    public InviteAdapter(@NonNull Context context, int resource, List<Notification> inv) {
        super(context, resource, inv);
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
        db.addNoti(new Notification(0,1,2,2));

        db.addTempEvent(new Event(0,"new event",0,0));

        Notification n = getItem(position);


        if (n != null) {
            TextView fromUser = (TextView) v.findViewById(R.id.notiFromUser);
            TextView description = (TextView) v.findViewById(R.id.notiDescription);
            TextView notiEvtName = (TextView) v.findViewById(R.id.notiEventName);


            if (n.getType() == 2){

                if (fromUser != null){
                    fromUser.setText(db.getUserNameById(n.getFrom()));
                }

                if (description != null) {
                    description.setText(R.string.invitetoevent);
                }

                if (notiEvtName != null) {
                    notiEvtName.setText(db.getEvtNamebyID(n.getEventID()));
                }

            }

        }

        ImageButton buttonAccept = (ImageButton) v.findViewById(R.id.acceptInvButton);
        ImageButton buttonDecline = (ImageButton) v.findViewById(R.id.declineInvButton);

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.addNoti(new Notification(n.getEventID(),n.getTo(),n.getFrom(),1));

                //TODO: ADD To PARTICIPANTS & SIGNUP
                //db.signUpEvent(n.getEventID(), )

                //remove this notification
                db.deleteNoti(n.getNotiID());
            }
        });

        buttonDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteNoti(n.getNotiID());
            }
        });

        return v;
    }
}
