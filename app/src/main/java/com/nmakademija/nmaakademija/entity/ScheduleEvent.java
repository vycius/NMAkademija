package com.nmakademija.nmaakademija.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.nmakademija.nmaakademija.utils.DateUtils;

import java.util.Date;

@SuppressWarnings("WeakerAccess")
public class ScheduleEvent implements Parcelable {

    public String startTime;
    public String endTime;
    public String name;
    public String lecturer;
    public int section;
    public int id;


    //region Getters
    public String getStartTime() {
        return DateUtils.formatTime(DateUtils.parseTZDate(startTime));
    }

    public String getEndTime() {
        return DateUtils.formatTime(DateUtils.parseTZDate(endTime));
    }

    public String getName() {
        return name;
    }

    public String getLecturerName() {
        return lecturer;
    }

    public Date getDate() {
        return DateUtils.parseTZDate(startTime);
    }

    public int getSectionId() {
        return section;
    }

    public int getId() {
        return id;
    }
    //endregion

    public ScheduleEvent() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.name);
        dest.writeString(this.lecturer);
        dest.writeInt(this.section);
        dest.writeInt(this.id);
    }

    protected ScheduleEvent(Parcel in) {
        this.startTime = in.readString();
        this.endTime = in.readString();
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
