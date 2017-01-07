package com.nmakademija.nmaakademija.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmakademija.nmaakademija.ArticleActivity;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.ArticlesAdapter;
import com.nmakademija.nmaakademija.adapter.DividerItemDecoration;
import com.nmakademija.nmaakademija.api.FirebaseRealtimeApi;
import com.nmakademija.nmaakademija.api.listener.ArticlesLoadedListener;
import com.nmakademija.nmaakademija.entity.Article;
import com.nmakademija.nmaakademija.listener.ClickListener;
import com.nmakademija.nmaakademija.listener.RecyclerTouchListener;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.Error;

import java.util.ArrayList;

public class NewsFragment extends BaseSceeenFragment implements ArticlesLoadedListener {

    public static NewsFragment getInstance() {
        return new NewsFragment();
    }

    private AppEvent appEvent;
    private RecyclerView articlesRecyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        articlesRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        appEvent = AppEvent.getInstance(getContext());
        appEvent.trackCurrentScreen(getActivity(), "open_news");

        loadArticles();
    }

    private void loadArticles() {
        FirebaseRealtimeApi.getArticles(this);
    }


    @Override
    public void onArticlesLoaded(ArrayList<Article> articles) {
        if (isAdded()) {
            articlesRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutCompat.VERTICAL));
            articlesRecyclerView.setItemAnimator(new DefaultItemAnimator());
            articlesRecyclerView.setAdapter(new ArticlesAdapter(articles));
            articlesRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(
                    getContext(), articlesRecyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ArticleActivity.class);
                    Article article = ((ArticlesAdapter) articlesRecyclerView.getAdapter()).getArticle(position);
                    appEvent.trackArticleClicked(article.getId());
                    intent.putExtra(ArticleActivity.EXTRA_ARTICLE, article);
                    context.startActivity(intent);
                }

                @Override
                public void onLongClick(View view, int position) {
                    this.onClick(view, position);
                }
            }));
        }
    }

    @Override
    public void onArticlesLoadingFailed(Exception exception) {
        if (isAdded()) {
            Error.getData(getView(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadArticles();
                }
            });

        }
    }
}
