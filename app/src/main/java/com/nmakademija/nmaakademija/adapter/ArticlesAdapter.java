package com.nmakademija.nmaakademija.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.Article;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {
    private List<Article> articlesList;

    public ArticlesAdapter(List<Article> articlesList) {
        this.articlesList = articlesList;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        final Article article = articlesList.get(position);
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        if (!TextUtils.isEmpty(article.getTitleImage())) {
            Glide.with(holder.itemView.getContext()).load(article.getTitleImage()).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    @Override
    public long getItemId(int position) {
        return articlesList.get(position).getId();
    }

    public Article getArticle(int position) {
        return articlesList.get(position);
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;
        public ImageView image;

        public ArticleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.news_title);
            description = (TextView) view.findViewById(R.id.news_description);
            image = (ImageView) view.findViewById(R.id.news_image);
        }
    }
}
