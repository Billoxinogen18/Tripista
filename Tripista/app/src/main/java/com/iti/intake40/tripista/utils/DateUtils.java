package com.iti.intake40.tripista.utils;

public class DateUtils {
    public static String[] getDate(String date) {
        return date.split("-");
    }

    public static String[] getTime(String time) {
        return time.split(":");
    }
}
