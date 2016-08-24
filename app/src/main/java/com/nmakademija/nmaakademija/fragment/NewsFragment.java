package com.nmakademija.nmaakademija.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.ArticlesAdapter;
import com.nmakademija.nmaakademija.entity.Article;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    public static NewsFragment getInstance() {
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if(view !=null) {
            RecyclerView rv = (RecyclerView) view.findViewById(R.id.recyclerView);

            ArrayList<Article> articles = new ArrayList<>();

            articles.add(new Article("ssdjaisnduiadn"));
            articles.add(new Article("ssdjaisnduiadn"));
            articles.add(new Article("ssdjaisnduiadn"));
            articles.add(new Article("ssdjaisnduiadn"));
            articles.add(new Article("ssdjaisnduiadn"));

            rv.setAdapter(new ArticlesAdapter(articles));
        }
    }

}
