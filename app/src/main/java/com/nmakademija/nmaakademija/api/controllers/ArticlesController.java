package com.nmakademija.nmaakademija.api.controllers;


import com.google.firebase.database.FirebaseDatabase;
import com.nmakademija.nmaakademija.api.listener.ApiReferenceListener;
import com.nmakademija.nmaakademija.api.listener.ArticlesLoadedListener;
import com.nmakademija.nmaakademija.entity.Article;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ArticlesController extends FirebaseController {

    public ArticlesController(ArticlesLoadedListener listener) {
        WeakReference<ArticlesLoadedListener> weakListener = new WeakReference<>(listener);
        databaseReference = FirebaseDatabase.getInstance().getReference("articles");
        eventListener = new ApiReferenceListener<Article, ArticlesLoadedListener>(Article.class,
                weakListener) {

            @Override
            public void onFailed(ArticlesLoadedListener listener, Exception ex) {
                listener.onArticlesLoadingFailed(ex);
            }

            @Override
            public void onLoaded(ArrayList<Article> items, ArticlesLoadedListener listener) {
                listener.onArticlesLoaded(items);
            }

            @Override
            public void onUpdated(ArrayList<Article> items, ArticlesLoadedListener listener) {
                listener.onArticlesUpdatedLoaded(items);
            }

            @Override
            public ArrayList<Article> order(ArrayList<Article> items) {
                return items;
            }
        };

    }

}
