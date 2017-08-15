package com.nmakademija.nmaakademija.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Section implements Parcelable {

    private int id;
    private String name;
    private String supervisor;

    public String getSupervisor() {
        return supervisor;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Section() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.supervisor);
    }

    protected Section(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.supervisor = in.readString();
    }

    public static final Creator<Section> CREATOR = new Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel source) {
            return new Section(source);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
        }
    };
}
