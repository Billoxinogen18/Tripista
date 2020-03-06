package com.iti.intake40.tripista;

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
}
