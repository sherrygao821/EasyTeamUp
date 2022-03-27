package com.example.easyteamup.classes;

public class Notification {

    private int eventID;
    private User from;
    private User to;
    //0:joined 1:withdraw 2:update(time terminated) 3:invitation
    private int type;

    public Notification(int eventID, User from, User to, int type) {
        this.eventID = eventID;
        this.from = from;
        this.to = to;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "eventID=" + eventID +
                ", from=" + from +
                ", to=" + to +
                ", type=" + type +
                '}';
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
