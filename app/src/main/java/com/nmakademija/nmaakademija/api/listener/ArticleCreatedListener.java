package com.nmakademija.nmaakademija.api.listener;

import com.nmakademija.nmaakademija.entity.Article;

public interface ArticleCreatedListener {
    void onArticleCreated(Article article);
    void onArticleCreateFailed(Exception exception);
}