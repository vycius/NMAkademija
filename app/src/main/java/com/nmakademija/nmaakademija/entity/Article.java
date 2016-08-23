package com.nmakademija.nmaakademija.entity;

/**
 * Created by NMA on 2016.08.22.
 */
public class Article {
    private String title;
    private String description;
    private String imageUrl;

    public Article(String s) {
        title = s;
        description = s;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
