package com.nmakademija.nmaakademija.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    public static NewsFragment getInstance() {
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        API.nmaService.getArticles().enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                View v = getView();
                if (v != null) {
                    final RecyclerView rv = (RecyclerView) getView().findViewById(R.id.recyclerView);

                    rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutCompat.VERTICAL));
                    rv.setItemAnimator(new DefaultItemAnimator());
                    rv.setAdapter(new ArticlesAdapter(response.body()));
                    rv.addOnItemTouchListener(new RecyclerTouchListener(
                            getContext(), rv, new ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Context context = view.getContext();
                            Intent intent = new Intent(context, ArticleActivity.class);
                            intent.putExtra(ArticleActivity.EXTRA_ARTICLE, ((ArticlesAdapter) rv.getAdapter()).getArticle(position));
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
            public void onFailure(Call<List<Article>> call, Throwable t) {
                View view = getView();
                if (view != null)
                    Snackbar.make(view, R.string.request_failed, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
