package com.nmakademija.nmaakademija;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class ArticleActivity extends AppCompatActivity {
    public static final String EXTRA_ARTICLE_CONTENT = "com.nmakademija.nmaakademija.article_content";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        String html = getIntent().getStringExtra(EXTRA_ARTICLE_CONTENT);
        TextView tv = (TextView) findViewById(R.id.htmlView);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(Html.fromHtml(html));
    }
}
