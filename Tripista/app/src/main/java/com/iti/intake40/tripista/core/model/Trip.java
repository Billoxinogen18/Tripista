package com.iti.intake40.tripista.core.model;

import java.util.ArrayList;
import java.util.List;

public class Trip {

    /*
    private fields
     */
    private String tripId;
    private String title;
    private String date;
    private String time;
    private String startPoint;
    private String endPoint;
    private String backStartPoint;
    private String backEndPoint;
    private int status; //upcomming / done / cancelled
    private int type; //one way / round trip
    private String latLng;
    private String backTime;
    private String backDate;
    private List<Note> notes = new ArrayList<>();

    /*
    enums
     */
    public enum Type {
        ONE_WAY,
        ROUND_TRIP
    }

    public enum Status {
        UPCOMMING,
        DONE,
        CANCELLED
    }

    /*
    constructors
     */

    public Trip() {
    }

    public Trip(String tripId, String title, String date, String time, String startPoint, String endPoint, String backStartPoint, String backEndPoint, int status, int type, String latLng, String backTime, String backDate, List<Note> notes) {
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

    /*
    getters and setters
     */

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
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

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
