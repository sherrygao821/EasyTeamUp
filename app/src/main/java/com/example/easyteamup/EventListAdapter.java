package com.example.easyteamup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Event adapter for the profile page
 * @author Lucy Shi
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.viewHolder>{

    ArrayList<EventModel> list;
    Context context;

    public EventListAdapter(ArrayList<EventModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.event_history_sample, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        EventModel model = list.get(position);

        holder.profile.setImageResource(model.getProfile());
        holder.evt_name.setText(model.getEvt_name());
        holder.evt_time.setText(model.getEvt_time());
        holder.evt_type.setText(model.getEvt_type());
        holder.evt_user_name.setText(model.getEvt_user_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView profile;
        TextView evt_name, evt_time, evt_type, evt_user_name;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile_image);
            evt_name = itemView.findViewById(R.id.evtName);
            evt_time = itemView.findViewById(R.id.evtTime);
            evt_type = itemView.findViewById(R.id.evtType);
            evt_user_name = itemView.findViewById(R.id.evtUserName);

        }
    }
}
