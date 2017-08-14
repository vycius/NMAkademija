package com.nmakademija.nmaakademija.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

@SuppressWarnings("WeakerAccess")
public class Article implements Parcelable, Comparable<Article> {

    public int id;
    public String title;
    public String description;
    public String content;
    public String titleImage;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public
    @Nullable
    String getDescription() {
        return description;
    }

    public
    @Nullable
    String getTitleImage() {
        return titleImage;
    }

    public
    @Nullable
    String getContent() {
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

    @Override
    public int compareTo(@NonNull Article article) {
        return Integer.valueOf(article.getId()).compareTo(getId());
    }

}
