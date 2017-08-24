package com.nmakademija.nmaakademija;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nmakademija.nmaakademija.BaseActivity;
import com.nmakademija.nmaakademija.entity.Article;

/**
 * Created by wallflower on 17.8.23.
 */

public class ArticleCreateActivity extends BaseActivity{
    Button postButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        postButton = findViewById(R.id.button2);
//        postButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                postArticle();
//            }
//        });

        setContentView(R.layout.activity_article_create);
    }

    private void postArticle() {
        String articleTitle = ((TextView)(findViewById(R.id.article_name_field))).getText().toString();
        String articleContent = ((TextView)(findViewById(R.id.article_content_field))).getText().toString();

        Article article = new Article();
        article.setTitle(articleTitle);
        article.setContent(articleContent);
        article.setId(System.currentTimeMillis() / 1000L);
        article.setCreator("Batman");
        article.setDescription("lmao");
    }
}
