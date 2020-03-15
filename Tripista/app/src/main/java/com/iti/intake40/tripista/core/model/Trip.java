package com.iti.intake40.tripista.core.model;

import java.util.ArrayList;
import java.util.List;

public class Trip {
    String tripId;
    String title;
    String date;
    String time;
    String startPoint;
    String endPoint;
    String backStartPoint;
    String backEndPoint;
    String status; //upcomming / done / cancelled
    String type; //one way / round trip
    String latLng;
    String backTime;
    String backDate;


    List<Note> notes = new ArrayList<>();

    public Trip() {
    }

    public Trip(String tripId, String title, String date, String time, String startPoint, String endPoint, String backStartPoint, String backEndPoint, String status, String type, String latLng, String backTime, String backDate, List<Note> notes) {
        this.tripId = tripId;
        this.title = title;
        this.date = date;
        this.time = time;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.backStartPoint = backStartPoint;
        this.backEndPoint = backEndPoint;
        this.status = status;
        this.type = type;
        this.latLng = latLng;
        this.backTime = backTime;
        this.backDate = backDate;
        this.notes = notes;
    }

    public Trip(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }

    public String getBackStartPoint() {
        return backStartPoint;
    }

    public void setBackStartPoint(String backStartPoint) {
        this.backStartPoint = backStartPoint;
    }

    public String getBackEndPoint() {
        return backEndPoint;
    }

    public void setBackEndPoint(String backEndPoint) {
        this.backEndPoint = backEndPoint;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }


    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
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

    public String getBackTime() {
        return backTime;
    }

    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }
}