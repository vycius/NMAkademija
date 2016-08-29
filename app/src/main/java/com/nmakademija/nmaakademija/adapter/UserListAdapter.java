package com.nmakademija.nmaakademija.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.User;

import java.util.ArrayList;

public class UserListAdapter extends ArrayAdapter<User> implements Filterable {
    private Context context;
    private ArrayList<User> users;
    private ArrayList<User> allUsers;

    public UserListAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
        this.context = context;
        allUsers = users;
        this.users = allUsers;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int i) {
        return users.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_user, parent, false);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.user_image);
        TextView name = (TextView) convertView.findViewById(R.id.user_name);

        Glide.with(context).load(user.getImage()).into(image);
        name.setText(user.getName());

        return convertView;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {

            //            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                users = (ArrayList<User>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                int i = Integer.parseInt(constraint.toString());
                Log.d("filter", String.valueOf(i));
                ArrayList<User> newUserList = new ArrayList<>();
                if (i == 0) {
                    newUserList = allUsers;
                } else {
                    for (User user : allUsers)
                        if (user.getSection() == i)
                            newUserList.add(user);
                }

                results.count = newUserList.size();
                results.values = newUserList;

                return results;
            }
        };
    }

}
