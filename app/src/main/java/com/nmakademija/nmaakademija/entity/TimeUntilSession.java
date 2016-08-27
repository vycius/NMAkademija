package com.nmakademija.nmaakademija.entity;

import android.content.Context;

import com.nmakademija.nmaakademija.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUntilSession {

    private Date sessionStart;
    private Date sessionEnd;

    private SimpleDateFormat dateFormat;

    private final long millisecondsInDay = 86400000;

    public TimeUntilSession(Date StartTime, Date EndTime) {
        sessionStart = StartTime;
        sessionEnd = EndTime;
        dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
    }

    public String returnTime(Context context) {
        long now = new Date().getTime();

        long timeLeft = Math.max(sessionStart.getTime() - now, sessionEnd.getTime() - now);
        long daysLeft = timeLeft / millisecondsInDay;
        String timeLeftString = dateFormat.format(new Date(timeLeft));
        return context.getString(R.string.timer_date_format, daysLeft, timeLeftString);
    }

    public boolean isSession() {
        long now = new Date().getTime();
        return (sessionStart.getTime() - now) < 0;
    }
}