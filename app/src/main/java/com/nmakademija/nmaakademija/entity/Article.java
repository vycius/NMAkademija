package com.nmakademija.nmaakademija.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {
    private int id;
    private String title;
    private String description;
    private String content;
    private String titleImage;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.content);
        dest.writeString(this.titleImage);
    }

    public Article() {
    }

    protected Article(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.content = in.readString();
        this.titleImage = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
