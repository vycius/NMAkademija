package com.nmakademija.nmaakademija.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScheduleEvent implements Parcelable {
    private Date startTime;
    private Date endTime;
    private String name;
    private String lecturer;
    private int section;
    private int id;

    private SimpleDateFormat dateFormater = new SimpleDateFormat("HH:mm", Locale.US);

    //region Getters
    public String getStartTime() {
        return dateFormater.format(startTime);
    }

    public String getEndTime() {
        return dateFormater.format(endTime);
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
        dest.writeSerializable(this.dateFormater);
    }

    public ScheduleEvent() {
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
        this.dateFormater = (SimpleDateFormat) in.readSerializable();
    }

    public static final Parcelable.Creator<ScheduleEvent> CREATOR = new Parcelable.Creator<ScheduleEvent>() {
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
