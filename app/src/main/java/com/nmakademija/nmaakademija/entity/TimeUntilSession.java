package com.nmakademija.nmaakademija.entity;

import android.content.Context;

import com.nmakademija.nmaakademija.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUntilSession {

    private long sessionStart;
    private long sessionEnd;

    private SimpleDateFormat dateFormat;

    private final int millisecondsInSecond = 1000;
    private final long millisecondsInDay = 86400000;

    public TimeUntilSession(long StartTime, long EndTime) {
        sessionStart = StartTime * millisecondsInSecond;
        sessionEnd = EndTime * millisecondsInSecond;
        dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
    }

    public String returnTime(Context context) {
        long now = new Date().getTime();

        long timeLeft = Math.max(sessionStart - now, sessionEnd - now);
        long daysLeft = timeLeft / millisecondsInDay;
        String timeLeftString = dateFormat.format(new Date(timeLeft));
        return context.getString(R.string.timeUntilSessionDateFormat, daysLeft, timeLeftString);
    }

    public boolean isSession() {
        long now = new Date().getTime();
        return (sessionStart - now) < 0;
    }
}