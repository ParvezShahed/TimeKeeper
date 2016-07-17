package com.time.code.timekeeper.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shahed on 14/07/2016.
 */
public class TimeCard {
    @SerializedName("id")
    Integer id;

    @SerializedName("username")
    String userName;

    @SerializedName("occurrence")
    String occurrence;

    @SerializedName("total_worked_hours")
    String totalWorkedHours;

    private ArrayList<TimeEntry> timeEntries;

    public Integer getId() {
        return id;
    }

    public String getmOccurrence() {
        return occurrence;
    }

    public String getmTotalWorkedHours() {
        return totalWorkedHours;
    }

    public void setTimeEntries(ArrayList<TimeEntry> timeEntries){
        this.timeEntries = timeEntries;
    }

    public ArrayList<TimeEntry> getTimeEntries(){
        return this.timeEntries;
    }

    public TimeCard(String userName, String occurrence ) {
        this.userName = userName;
        this.occurrence = occurrence;
    }
}
