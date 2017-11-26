package com.nmakademija.nmaakademija.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Article implements Parcelable, Comparable<Article> {

    private long id;
    private String title;
    private String content;
    private String titleImage;
    @Nullable
    private String creator;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public String getContent() {
        return content;
    }

    public String getCreator() { return creator; }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public void setCreator(@Nullable String creator) {
        this.creator = creator;
    }

    public Article() {
    }

    @Override
    public int compareTo(@NonNull Article article) {
        return Long.valueOf(article.getId()).compareTo(getId());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.titleImage);
        dest.writeString(this.creator);
    }

    protected Article(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.content = in.readString();
        this.titleImage = in.readString();
        this.creator = in.readString();
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
