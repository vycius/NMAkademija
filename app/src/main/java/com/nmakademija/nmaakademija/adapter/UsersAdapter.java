package com.nmakademija.nmaakademija.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nmakademija.nmaakademija.ProfileActivity;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.Section;
import com.nmakademija.nmaakademija.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> implements Filterable {
    private List<User> users;
    private List<User> allUsers;
    private List<Section> sections;

    public UsersAdapter(List<User> users, List<Section> sections) {
        super();
        allUsers = users;
        this.sections = sections;
        this.users = allUsers;
    }

    @Nullable
    public String getSupervisor(int i) {
        for (Section section : sections)
            if (section.getId() == i)
                return section.getSupervisor();
        return null;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                users = (List<User>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                int i = Integer.parseInt(constraint.toString());

                List<User> newUserList = new ArrayList<>();
                if (i == 0) {
                    newUserList = allUsers;
                } else {
                    for (User user : allUsers)
                        if (user.getSection() == i)
                            newUserList.add(user);
                }

                FilterResults results = new FilterResults();
                results.count = newUserList.size();
                results.values = newUserList;

                return results;
            }
        };
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_user, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final User user = users.get(position);
        holder.name.setText(user.getName());
        Glide.with(holder.itemView.getContext()).load(user.getImage()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_ALLOW_EDIT, false);
                intent.putExtra(ProfileActivity.EXTRA_USER, user);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;

        public UserViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.user_name);
            image = (ImageView) view.findViewById(R.id.user_image);
        }
    }
}
