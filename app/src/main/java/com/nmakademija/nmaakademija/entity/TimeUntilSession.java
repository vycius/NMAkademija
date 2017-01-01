package com.nmakademija.nmaakademija.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class TimeUntilSession implements Parcelable {

    private Date startTime;
    private Date endTime;

    public boolean isSession() {
        long now = new Date().getTime();
        return startTime.getTime() < now;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public TimeUntilSession() {
    }

    protected TimeUntilSession(Parcel in) {
        long tmpStartTime = in.readLong();
        this.startTime = tmpStartTime == -1 ? null : new Date(tmpStartTime);
        long tmpEndTime = in.readLong();
        this.endTime = tmpEndTime == -1 ? null : new Date(tmpEndTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.startTime != null ? this.startTime.getTime() : -1);
        dest.writeLong(this.endTime != null ? this.endTime.getTime() : -1);
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
