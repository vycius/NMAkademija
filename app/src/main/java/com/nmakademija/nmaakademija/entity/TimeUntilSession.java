package com.nmakademija.nmaakademija.entity;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUntilSession {

    private long sessionStart;
    private long sessionEnd;

    private SimpleDateFormat dateFormat;

    @SuppressLint("SimpleDateFormat")
    public TimeUntilSession(long StartTime, long EndTime) {
        sessionStart = StartTime * 1000;
        sessionEnd = EndTime * 1000;
        dateFormat = new SimpleDateFormat("HH:mm:ss");
    }

    public String returnTime() {
        long now = new Date().getTime();

        long timeLeft = Math.max(sessionStart - now, sessionEnd - now);
        String stringDaysLeft = Long.toString(timeLeft/(24*60*60*1000))+ "d ";
        return stringDaysLeft + dateFormat.format(new Date(timeLeft));
    }

    public boolean isSession(){
        long now = new Date().getTime();
        return (sessionStart - now) < 0;
    }
}