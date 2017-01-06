package com.nmakademija.nmaakademija.api.listener;


import com.nmakademija.nmaakademija.entity.Article;

import java.util.ArrayList;

public interface ArticlesLoadedListener {

    void onArticlesLoaded(ArrayList<Article> articles);

    void onArticlesLoadingFailed(Exception exception);

}
