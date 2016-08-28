package com.nmakademija.nmaakademija.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScheduleEvent {
    private Date startTime;
    private Date endTime;
    private String name;
    private String lecturer;

    private SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm", Locale.US);

    //region Getters
    public String getStartTime() {
        return dateFormater.format(startTime);
    }

    public String getEndTime() {
        return dateFormater.format(endTime);
    }

    public String getName() {
        return name;
    }

    public String getLecturerName() {
        return lecturer;
    }

    public Date getDate() {
        return startTime;
    }
    //endregion
}
