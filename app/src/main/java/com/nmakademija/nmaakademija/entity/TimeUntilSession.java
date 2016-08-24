package com.nmakademija.nmaakademija.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUntilSession {

    private long _SessionStart;
    private long _SessionEnd;
    private boolean labas = false;

    private final DateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.US);

    public TimeUntilSession(long StartTime, long EndTime) {
        _SessionStart = StartTime;
        _SessionEnd = EndTime;
    }

    public String returnTime() {
        long now = new Date().getTime();

        long timeLeft = Math.max(_SessionStart * 1000 - now, _SessionEnd * 1000 - now);

        String dateFormatted = formatter.format(timeLeft);

        return dateFormatted;
    }
}