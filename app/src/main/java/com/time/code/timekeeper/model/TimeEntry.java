package com.time.code.timekeeper.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by shahed on 14/07/2016.
 */

public class TimeEntry {

    @SerializedName("id")
    String id;

    @SerializedName("time")
    String time;

    @SerializedName("timecard_id")
    Integer timeCardId;

    public String getId() {
        return id;
    }
    public String getTime() {
        return time;
    }

    public Integer getTimeCardId() {
        return timeCardId;
    }

    public TimeEntry(String time, Integer timeCardId ) {
        this.time = time;
        this.timeCardId = timeCardId;
    }
}
