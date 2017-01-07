package com.nmakademija.nmaakademija.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@SuppressWarnings("WeakerAccess")
public class Academic implements Parcelable, Comparable<Academic> {

    public int id;
    public String name;
    public String email;
    public String phone;
    public String bio;
    public String image;
    public int section;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getImage() {
        return image;
    }

    public String getBio() {
        return bio;
    }

    public int getSection() {
        return section;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.phone);
        dest.writeString(this.bio);
        dest.writeString(this.image);
        dest.writeInt(this.section);
    }

    public Academic() {
    }

    protected Academic(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.bio = in.readString();
        this.image = in.readString();
        this.section = in.readInt();
    }

    public static final Creator<Academic> CREATOR = new Creator<Academic>() {
        @Override
        public Academic createFromParcel(Parcel source) {
            return new Academic(source);
        }

        @Override
        public Academic[] newArray(int size) {
            return new Academic[size];
        }
    };

    @Override
    public int compareTo(@NonNull Academic academic) {
        return getName().compareToIgnoreCase(academic.getName());
    }
}
