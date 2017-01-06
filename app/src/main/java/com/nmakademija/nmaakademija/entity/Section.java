package com.nmakademija.nmaakademija.entity;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("WeakerAccess")
public class Section implements Parcelable {
    public int id;
    public String name, supervisor, image;

    public String getSupervisor() {
        return supervisor;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
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
        dest.writeString(this.image);
    }

    public Section() {
    }

    protected Section(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.supervisor = in.readString();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Section> CREATOR = new Parcelable.Creator<Section>() {
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
