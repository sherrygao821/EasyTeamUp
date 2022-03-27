package com.example.easyteamup;

/**
 * Event model for the profile page
 * @author Lucy Shi
 */

public class EventModel {

    int profile;
    String evt_name, evt_time, evt_type, evt_user_name;

    public EventModel(int profile, String evt_name, String evt_time, String evt_type, String evt_user_name) {
        this.profile = profile;
        this.evt_name = evt_name;
        this.evt_time = evt_time;
        this.evt_type = evt_type;
        this.evt_user_name = evt_user_name;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getEvt_name() {
        return evt_name;
    }

    public void setEvt_name(String evt_name) {
        this.evt_name = evt_name;
    }

    public String getEvt_time() {
        return evt_time;
    }

    public void setEvt_time(String evt_time) {
        this.evt_time = evt_time;
    }

    public String getEvt_type() {
        return evt_type;
    }

    public void setEvt_type(String evt_type) {
        this.evt_type = evt_type;
    }

    public String getEvt_user_name() {
        return evt_user_name;
    }

    public void setEvt_user_name(String evt_user_name) {
        this.evt_user_name = evt_user_name;
    }
}
