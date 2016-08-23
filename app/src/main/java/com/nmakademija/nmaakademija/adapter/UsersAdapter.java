package com.nmakademija.nmaakademija.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.User;

import java.util.List;

public class UsersAdapter extends ArrayAdapter<User> {

    public UsersAdapter(Context c, List<User> users) {
        super(c, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_user, parent, false);

        ImageView iv = (ImageView) convertView.findViewById(R.id.user_image);

        // TODO Load from url using Glide
        Glide.with(getContext()).load(R.drawable.leon).into(iv);

        TextView tv = (TextView) convertView.findViewById(R.id.user_name);
        tv.setText(getItem(position).getName());

        return convertView;
    }
}
