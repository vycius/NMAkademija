package com.nmakademija.nmaakademija;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nmakademija.nmaakademija.entity.Article;
import com.nmakademija.nmaakademija.utils.AppEvent;

public class ArticleActivity extends BaseActivity {

    public static final String EXTRA_ARTICLE = "com.nmakademija.nmaakademija.article";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Article article = getIntent().getParcelableExtra(EXTRA_ARTICLE);

        TextView tv = (TextView) findViewById(R.id.new_html_view);
        //noinspection deprecation
        tv.setText(Html.fromHtml(article.getContent()));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(article.getTitle());
        }

        Glide.with(this).load(article.getTitleImage()).into((ImageView) findViewById(R.id.new_image));
        AppEvent.getInstance(this).trackCurrentScreen(this, "open_article");
    }
}
