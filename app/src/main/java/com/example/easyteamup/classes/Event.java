package com.example.easyteamup.classes;

import java.util.*;

public class Event {
    private int evtId;
    private String evtName;
    private String hostId;
    private String evtDescription;
    private String evtSignUpDueDate;
    private String evtTime;
    private String evtLocation;
    private Map<String, Integer> evtTimeSlots;
    private List<String> evtParticipants;
    private int evtType;
    private boolean isActive;
    private boolean isPublic;

    public Event(int evtId, String evtName, String hostId, String evtDescription, String evtSignUpDueDate, String evtTime, String evtLocation, Map<String, Integer> evtTimeSlots, List<String> evtParticipants, int evtType, boolean isActive, boolean isPublic) {
        this.evtId = evtId;
        this.evtName = evtName;
        this.hostId = hostId;
        this.evtDescription = evtDescription;
        this.evtSignUpDueDate = evtSignUpDueDate;
        this.evtTime = evtTime;
        this.evtLocation = evtLocation;
        this.evtTimeSlots = evtTimeSlots;
        this.evtParticipants = evtParticipants;
        this.evtType = evtType;
        this.isActive = isActive;
        this.isPublic = isPublic;
    }

    public int getEvtId() {
        return evtId;
    }

    public void setEvtId(int evtId) {
        this.evtId = evtId;
    }

    public String getEvtName() {
        return evtName;
    }

    public void setEvtName(String evtName) {
        this.evtName = evtName;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getEvtDescription() {
        return evtDescription;
    }

    public void setEvtDescription(String evtDescription) {
        this.evtDescription = evtDescription;
    }

    public String getEvtSignUpDueDate() {
        return evtSignUpDueDate;
    }

    public void setEvtSignUpDueDate(String evtSignUpDueDate) {
        this.evtSignUpDueDate = evtSignUpDueDate;
    }

    public String getEvtTime() {
        return evtTime;
    }

    public void setEvtTime(String evtTime) {
        this.evtTime = evtTime;
    }

    public String getEvtLocation() {
        return evtLocation;
    }

    public void setEvtLocation(String evtLocation) {
        this.evtLocation = evtLocation;
    }

    public Map<String, Integer> getEvtTimeSlots() {
        return evtTimeSlots;
    }

    public void setEvtTimeSlots(Map<String, Integer> evtTimeSlots) {
        this.evtTimeSlots = evtTimeSlots;
    }

    public List<String> getEvtParticipants() {
        return evtParticipants;
    }

    public void setEvtParticipants(List<String> evtParticipants) {
        this.evtParticipants = evtParticipants;
    }

    public int getEvtType() {
        return evtType;
    }

    public void setEvtType(int evtType) {
        this.evtType = evtType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}