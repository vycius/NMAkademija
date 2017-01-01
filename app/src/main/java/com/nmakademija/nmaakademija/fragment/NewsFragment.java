package com.nmakademija.nmaakademija.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.nmakademija.nmaakademija.api.API;
import com.nmakademija.nmaakademija.entity.Article;
import com.nmakademija.nmaakademija.listener.ClickListener;
import com.nmakademija.nmaakademija.listener.RecyclerTouchListener;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.Error;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    public static NewsFragment getInstance() {
        return new NewsFragment();
    }

    public static final String EXTRA_ARTICLES = "articles";

    ArrayList<Article> articles;

    private AppEvent appEvent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            articles = savedInstanceState.getParcelableArrayList(EXTRA_ARTICLES);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(EXTRA_ARTICLES, articles);

        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        appEvent = AppEvent.getInstance(getContext());
        appEvent.trackCurrentScreen(getActivity(), "open_news");

        if (articles == null) {
            getData();
        } else {
            setData(articles);
        }
    }

    private void setData(List<Article> articles) {
        if (isAdded()) {
            final RecyclerView rv = (RecyclerView) getActivity().findViewById(R.id.recyclerView);

            rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutCompat.VERTICAL));
            rv.setItemAnimator(new DefaultItemAnimator());
            rv.setAdapter(new ArticlesAdapter(articles));
            rv.addOnItemTouchListener(new RecyclerTouchListener(
                    getContext(), rv, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ArticleActivity.class);
                    Article article = ((ArticlesAdapter) rv.getAdapter()).getArticle(position);
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

    private void getData() {
        API.nmaService.getArticles().enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                articles = new ArrayList<>(response.body());

                setData(articles);
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Error.getData(getView(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getData();
                    }
                });
            }
        });
    }

}
