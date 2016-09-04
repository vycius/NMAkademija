package com.nmakademija.nmaakademija;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nmakademija.nmaakademija.entity.Article;

public class ArticleActivity extends BaseActivity {
    public static final String EXTRA_ARTICLE = "com.nmakademija.nmaakademija.article";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Article article = (Article) getIntent().getSerializableExtra(EXTRA_ARTICLE);
        TextView tv = (TextView) findViewById(R.id.new_html_view);
        tv.setText(Html.fromHtml(article.getContent()));
        ((TextView) findViewById(R.id.new_description)).setText(
                article.getDescription());
        ((TextView) findViewById(R.id.new_title)).setText(
                article.getTitle());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle(R.string.news);
        Glide.with(this).load(article.getTitleImage()).into((ImageView) findViewById(R.id.new_image));
    }
}
