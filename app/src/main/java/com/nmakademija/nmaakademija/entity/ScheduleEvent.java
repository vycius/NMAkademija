package com.nmakademija.nmaakademija.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScheduleEvent implements Parcelable {

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private Date startTime;
    private Date endTime;
    private String name;
    private String lecturer;
    private int section;
    private int id;

    //region Getters
    public String getStartTime() {
        return DATE_FORMAT.format(startTime);
    }

    public String getEndTime() {
        return DATE_FORMAT.format(endTime);
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

    public int getSectionId() {
        return section;
    }

    public int getId() {
        return id;
    }
    //endregion

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.startTime != null ? this.startTime.getTime() : -1);
        dest.writeLong(this.endTime != null ? this.endTime.getTime() : -1);
        dest.writeString(this.name);
        dest.writeString(this.lecturer);
        dest.writeInt(this.section);
        dest.writeInt(this.id);
    }

    protected ScheduleEvent(Parcel in) {
        long tmpStartTime = in.readLong();
        this.startTime = tmpStartTime == -1 ? null : new Date(tmpStartTime);
        long tmpEndTime = in.readLong();
        this.endTime = tmpEndTime == -1 ? null : new Date(tmpEndTime);
        this.name = in.readString();
        this.lecturer = in.readString();
        this.section = in.readInt();
        this.id = in.readInt();
    }

    public static final Creator<ScheduleEvent> CREATOR = new Creator<ScheduleEvent>() {
        @Override
        public ScheduleEvent createFromParcel(Parcel source) {
            return new ScheduleEvent(source);
        }

        @Override
        public ScheduleEvent[] newArray(int size) {
            return new ScheduleEvent[size];
        }
    };
}
