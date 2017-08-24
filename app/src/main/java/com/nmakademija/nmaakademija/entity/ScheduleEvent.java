package com.nmakademija.nmaakademija.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.nmakademija.nmaakademija.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleEvent implements Parcelable {

    private String startTime;
    private String endTime;
    private String name;
    private String lecturer;
    private int section;
    private int id;
    private String description;
    private List<Integer> ratings;

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

    public String getLecturer() {
        return lecturer;
    }

    public Date getStartDate() {
        return DateUtils.parseTZDate(startTime);
    }

    public Date getEndDate() {
        return DateUtils.parseTZDate(endTime);
    }

    public int getSection() {
        return section;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public float getRating() {
        int count = 0, sum = 0;
        for (int i = 0; i < ratings.size(); i++) {
            int x = ratings.get(i);
            count += x;
            sum += (i + 1) * x;
        }
        return (float) sum / count;
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
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(name);
        dest.writeString(lecturer);
        dest.writeInt(section);
        dest.writeInt(id);
        dest.writeString(description);
        if (ratings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ratings);
        }
    }

    protected ScheduleEvent(Parcel in) {
        startTime = in.readString();
        endTime = in.readString();
        name = in.readString();
        lecturer = in.readString();
        section = in.readInt();
        id = in.readInt();
        description = in.readString();
        if (in.readByte() == 0x01) {
            ratings = new ArrayList<>();
            in.readList(ratings, Integer.class.getClassLoader());
        } else {
            ratings = null;
        }
    }

    @SuppressWarnings("unused")
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
