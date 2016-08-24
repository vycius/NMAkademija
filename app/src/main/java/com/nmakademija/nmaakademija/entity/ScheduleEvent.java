package com.nmakademija.nmaakademija.entity;

import android.text.TextUtils;

import com.nmakademija.nmaakademija.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dekedro on 16.8.22.
 */
public class ScheduleEvent implements ScheduleItem {
    private long startTime;
    private long endTime;
    private String name;
    private String lecturer;

    private SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm");

    //region Getters
    public String getStartTime() {
        return dateFormater.format(new Date(startTime * Constants.MILISECONDS_IN_SECOND));
    }

    public String getEndTime() {
        return dateFormater.format(new Date(endTime * Constants.MILISECONDS_IN_SECOND));
    }

    public String getName() {
        return name;
    }

    public String getLecturerName() {
        return lecturer;
    }

    public Date getDate() {
        return new Date(this.startTime * 1000l);
    }
    //endregion
}
