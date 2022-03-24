package com.example.easyteamup.classes;

/**
 * Author: Andy
 * Helper Class for Time
 */
public class TimeFormat {
    private int year;
    private int month;
    private int date;
    private int hour;
    private int minute;

    private String correctedMonth;
    private String correctedDate;
    private String correctedHour;
    private String correctedMin;

    public TimeFormat(){}

    public TimeFormat(int year, int month, int date, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public String toString() {

        if (getMonth() < 10) this.correctedMonth = "0"+ String.valueOf(getMonth());
        else this.correctedMonth = String.valueOf(getMonth());
        if (getDate() < 10) this.correctedDate = "0"+ String.valueOf(getDate());
        else this.correctedDate = String.valueOf(getDate());
        if (getHour() < 10) this.correctedHour = "0"+ String.valueOf(getHour());
        else this.correctedHour = String.valueOf(getHour());
        if (getMinute() < 10) this.correctedMin = "0"+ String.valueOf(getMinute());
        else this.correctedMin = String.valueOf(getMinute());

        return year +
                "-" + correctedMonth +
                "-" + correctedDate +
                " " + correctedHour +
                ":" + correctedMin;
    }

    public int getYear() {
        return year;
    }

    public boolean setYear(int year) {
        if (year < 2022) return false;
        this.year = year;
        return true;
    }

    public int getMonth() {
        return month;
    }

    public boolean setMonth(int month) {
        if (month<1 || month > 12) return false;
        this.month = month;
        return true;
    }

    public int getDate() {
        return date;
    }

    public boolean setDate(int date) {
        if (date < 1 || date > 31) return false;
        this.date = date;
        return true;
    }

    public int getHour() {
        return hour;
    }

    public boolean setHour(int hour) {
        if (hour < 0 || hour > 23) return false;
        this.hour = hour;
        return true;
    }

    public int getMinute() {
        return minute;
    }

    public boolean setMinute(int minute) {
        if (minute < 0 || minute > 59) return false;
        this.minute = minute;
        return true;
    }
}
