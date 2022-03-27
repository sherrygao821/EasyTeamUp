package com.example.easyteamup.classes;

/**
 * Author: Andy
 * Methods: getStartTimeString() and getEndTimeString()
 */
public class Time {
    private TimeFormat start;
    private TimeFormat end;

    public Time(TimeFormat start, TimeFormat end) {
        this.start = start;
        this.end = end;
    }

    public String getStartTimeString(){
        return this.start.toString();
    }

    public String getEndTimeString(){
        return this.end.toString();
    }

    public TimeFormat getStart() {
        return start;
    }

    public void setStart(TimeFormat start) {
        this.start = start;
    }

    public TimeFormat getEnd() {
        return end;
    }

    public void setEnd(TimeFormat end) {
        this.end = end;
    }
}
