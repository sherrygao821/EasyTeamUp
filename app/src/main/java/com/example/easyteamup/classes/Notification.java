package com.example.easyteamup.classes;

public class Notification {

    private int eventID;
    private int from;
    private int to;
    //0:joined 1:withdraw 2:update(time terminated) 3:invitation
    private int type;

    public Notification(int eventID, int from, int to, int type) {
        this.eventID = eventID;
        this.from = from;
        this.to = to;
        this.type = type;
    }

    @Override
    public String toString() {
        if (this.type == 0){
            return this.from + " joined your event " + eventID;
        } else if (this.type == 1) {
            return this.from + " withdraw from your event " + eventID;
        } else if (this.type == 3) {
            return "Time Terminated for your event " + eventID;
        }
        return "type is invitation";

        /*
        return "Notification{" +
                "eventID=" + eventID +
                ", from=" + from +
                ", to=" + to +
                ", type=" + type +
                '}';

         */
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
