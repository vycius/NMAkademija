package com.nmakademija.nmaakademija.entity;

import java.util.Date;

public class TimeUntilSession {

    private long sessionStart;
    private long sessionEnd;

    public TimeUntilSession(long StartTime, long EndTime) {
        sessionStart = StartTime * 1000;
        sessionEnd = EndTime * 1000;
    }

    public String returnTime() {
        long now = new Date().getTime();

        long timeLeft = Math.max(sessionStart - now, sessionEnd - now);
        long temp = timeLeft/(24*60*60*1000);
        String stringTimeLeft = (temp < 10 ? "0" : "")+Long.toString(temp)+ "d ";
        timeLeft = timeLeft % (24*60*60*1000);
        temp = timeLeft/(60*60*1000);
        stringTimeLeft = stringTimeLeft + (temp < 10 ? "0" : "")+Long.toString(temp)+":";
        timeLeft = timeLeft % (60*60*1000);
        temp = timeLeft/(60*1000);
        stringTimeLeft = stringTimeLeft + (temp < 10 ? "0" : "")+Long.toString(temp)+":";
        timeLeft = timeLeft % (60*1000);
        temp = timeLeft/(1000);
        stringTimeLeft = stringTimeLeft + (temp < 10 ? "0" : "")+Long.toString(temp);

        return stringTimeLeft;
    }

    public boolean isSession(){
        long now = new Date().getTime();
        return (sessionStart - now) < 0;
    }
}