package com.nmakademija.nmaakademija.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.nmakademija.nmaakademija.utils.DateUtils;

import java.util.Date;

public class TimeUntilSession implements Parcelable {

    private String startTime;
    private String endTime;

    public boolean isSession() {
        long now = new Date().getTime();
        return getStartTime().getTime() < now;
    }

    public Date getEndTime() {
        return DateUtils.parseTZDate(endTime);
    }

    public Date getStartTime() {
        return DateUtils.parseTZDate(startTime);
    }

    public TimeUntilSession() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
    }

    protected TimeUntilSession(Parcel in) {
        this.startTime = in.readString();
        this.endTime = in.readString();
    }

    public static final Creator<TimeUntilSession> CREATOR = new Creator<TimeUntilSession>() {
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
