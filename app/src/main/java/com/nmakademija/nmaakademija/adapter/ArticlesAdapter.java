package com.nmakademija.nmaakademija.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nmakademija.nmaakademija.ArticleActivity;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.Article;

import java.util.ArrayList;

public class ArticlesAdapter extends ArrayAdapter<Article> {

    Context context;

    public ArticlesAdapter(Context context, ArrayList<Article> articles) {
        super(context, R.layout.list_item_news, articles);
        this.context = context;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.list_item_news, parent, false);
        final Article article = getItem(position);

        TextView title = (TextView) convertView.findViewById(R.id.news_title);
        TextView description = (TextView) convertView.findViewById(R.id.news_description);
        ImageView image = (ImageView) convertView.findViewById(R.id.news_image);
        LinearLayout item = (LinearLayout) convertView.findViewById(R.id.news_item);
        title.setText(article.getTitle());
        description.setText(article.getDescription());
        Glide.with(getContext()).load(article.getTitleImage()).into(image);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c = view.getContext();
                Intent i = new Intent(c, ArticleActivity.class);
                i.putExtra(ArticleActivity.EXTRA_ARTICLE_CONTENT, article.getContent());
                c.startActivity(i);
            }
        });
        return convertView;
    }

    /*
    private List<Article> articlesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.news_title);
            description = (TextView) view.findViewById(R.id.news_description);
            image = (ImageView) view.findViewById(R.id.news_image);
        }
    }


    public ArticlesAdapter(List<Article> articlesList) {
        this.articlesList = articlesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_news, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Article article = articlesList.get(position);
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        Glide.with(holder.itemView.getContext()).load(article.getTitleImage()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context c = view.getContext();
                Intent i = new Intent(c, ArticleActivity.class);
                i.putExtra(ArticleActivity.EXTRA_ARTICLE_CONTENT, article.getContent());
                c.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }*/
}
