package com.nmakademija.nmaakademija;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.nmakademija.nmaakademija.api.FirebaseRealtimeApi;
import com.nmakademija.nmaakademija.api.listener.ArticleCreatedListener;
import com.nmakademija.nmaakademija.entity.Article;

import static com.nmakademija.nmaakademija.utils.DateUtils.getUnixTime;

public class ArticleCreateActivity extends BaseActivity implements ArticleCreatedListener {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_create_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.post) {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            postArticle();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_create);
    }

    private Article formArticle() {
        String articleTitle = ((TextView) (findViewById(R.id.article_name_field))).getText().toString();
        String articleContent = ((TextView) (findViewById(R.id.article_content_field))).getText().toString();

        Article article = new Article();
        article.setTitle(articleTitle);
        article.setContent(articleContent);
        article.setId(getUnixTime());
        article.setCreator(mAuth.getCurrentUser().getEmail());

        return article;
    }

    private void postArticle() {
        FirebaseRealtimeApi.addArticle(formArticle(), this);
        finish();
    }

    @Override
    public void onArticleCreated(Article article) {
    }

    @Override
    public void onArticleCreateFailed(Exception exception) {
    }
}
