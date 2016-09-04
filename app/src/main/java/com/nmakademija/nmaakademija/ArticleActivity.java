package com.nmakademija.nmaakademija;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

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
    }
}
