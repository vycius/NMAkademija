package com.nmakademija.nmaakademija.entity;

import java.io.Serializable;

public class Article implements Serializable {
    private int id;
    private String title;
    private String description;
    private String content;
    private String titleImage;

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
}
