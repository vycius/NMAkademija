package com.nmakademija.nmaakademija.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.Article;

import java.util.List;

/**
 * Created by karolis on 23/08/16.
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {

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
        Article article = articlesList.get(position);
        holder.title.setText(article.getTitle());
        holder.description.setText(article.getDescription());
        holder.image.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }
}
