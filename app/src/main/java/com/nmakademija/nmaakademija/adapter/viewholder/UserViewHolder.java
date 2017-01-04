package com.nmakademija.nmaakademija.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.User;

public class UserViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView image;

    public UserViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.user_name);
        image = (ImageView) view.findViewById(R.id.user_image);
    }

    public void bindData(final User user) {
        name.setText(user.getName());

        if (user.getImage() != null) {
            Glide.with(itemView.getContext())
                    .load(user.getImage())
                    .error(R.drawable.profile)
                    .into(image);

        }
    }
}
