package com.iti.intake40.tripista.core.model;

import java.util.ArrayList;
import java.util.List;

public class Trip {
    String date;
    String time;
    String startPoint;
    String endPoint;
    String reminderRepeat; // daily / weekly / monthly
    String status; //upcomming / done / cancelled
    String type; //one way / round trip

    List<Note> notes = new ArrayList<>();

    public Trip(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public String getReminderRepeat() {
        return reminderRepeat;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public List<Note> getNotes() {
        return notes;
    }
}
