package com.nmakademija.nmaakademija;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
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
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
        article.setDescription("lmao");

        return article;
    }

    private void postArticle() {
        FirebaseRealtimeApi.addArticle(formArticle(), this);
    }

    @Override
    public void onArticleCreated(Article article) {
        finish();
    }

    @Override
    public void onArticleCreateFailed(Exception exception) {
        Snackbar.make(findViewById(R.id.content), R.string.unable_to_post_article, Snackbar.LENGTH_INDEFINITE).show();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
