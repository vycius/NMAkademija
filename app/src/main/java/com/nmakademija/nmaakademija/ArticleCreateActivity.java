package com.nmakademija.nmaakademija;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nmakademija.nmaakademija.BaseActivity;
import com.nmakademija.nmaakademija.api.FirebaseRealtimeApi;
import com.nmakademija.nmaakademija.api.listener.ArticleCreatedListener;
import com.nmakademija.nmaakademija.entity.Article;
import com.nmakademija.nmaakademija.utils.NMAPreferences;

import static com.nmakademija.nmaakademija.utils.DateUtils.getUnixTime;

public class ArticleCreateActivity extends BaseActivity{
    Button postButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_create);

        postButton = findViewById(R.id.button2);
        postButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                postArticle();
            }
        });

    }

    private Article formArticle() {
        String articleTitle = ((TextView)(findViewById(R.id.article_name_field))).getText().toString();
        String articleContent = ((TextView)(findViewById(R.id.article_content_field))).getText().toString();

        Article article = new Article();
        article.setTitle(articleTitle);
        article.setContent(articleContent);
        article.setId(getUnixTime());
        article.setCreator(mAuth.getCurrentUser().getEmail());
        article.setDescription("lmao");

        return article;
    }

    private void postArticle() {
        final ArticleCreateActivity activity = this;

        FirebaseRealtimeApi.addArticle(formArticle(), new ArticleCreatedListener() {
            @Override
            public void onArticleCreated(Article article) {
                finish();
            }

            @Override
            public void onArticleCreateFailed(Exception exception) {
                Snackbar.make(findViewById(R.id.content), R.string.unable_to_post_article, Snackbar.LENGTH_INDEFINITE).show();
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });
    }
}
