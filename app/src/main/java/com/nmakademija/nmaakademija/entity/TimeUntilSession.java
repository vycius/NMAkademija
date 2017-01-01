package com.nmakademija.nmaakademija.entity;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.nmakademija.nmaakademija.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUntilSession implements Parcelable {

    private final static long millisecondsInDay = 86400000;
    private Date sessionStart;
    private Date sessionEnd;
    private SimpleDateFormat dateFormat;

    public TimeUntilSession(Date StartTime, Date EndTime) {
        sessionStart = StartTime;
        sessionEnd = EndTime;
        dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
    }

    public Date getEndTime(){
        return sessionEnd;
    }

    public Date getStartTime() {
        return sessionStart;
    }

    public String returnTime(Context context) {
        long now = new Date().getTime();

        long timeLeft = isSession() ? sessionEnd.getTime() - now : sessionStart.getTime() - now;
        long daysLeft = timeLeft / millisecondsInDay;
        String timeLeftString = dateFormat.format(new Date(timeLeft));
        return context.getString(R.string.timer_date_format, daysLeft, timeLeftString);
    }

    public boolean isSession() {
        long now = new Date().getTime();
        return sessionEnd.getTime()>now;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.sessionStart != null ? this.sessionStart.getTime() : -1);
        dest.writeLong(this.sessionEnd != null ? this.sessionEnd.getTime() : -1);
        dest.writeSerializable(this.dateFormat);
    }

    protected TimeUntilSession(Parcel in) {
        long tmpSessionStart = in.readLong();
        this.sessionStart = tmpSessionStart == -1 ? null : new Date(tmpSessionStart);
        long tmpSessionEnd = in.readLong();
        this.sessionEnd = tmpSessionEnd == -1 ? null : new Date(tmpSessionEnd);
        this.dateFormat = (SimpleDateFormat) in.readSerializable();
    }

    public static final Parcelable.Creator<TimeUntilSession> CREATOR = new Parcelable.Creator<TimeUntilSession>() {
        @Override
        public TimeUntilSession createFromParcel(Parcel source) {
            return new TimeUntilSession(source);
        }

        @Override
        public TimeUntilSession[] newArray(int size) {
            return new TimeUntilSession[size];
        }
    };
}