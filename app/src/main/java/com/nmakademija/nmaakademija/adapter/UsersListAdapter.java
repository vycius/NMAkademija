package com.nmakademija.nmaakademija.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.nmakademija.nmaakademija.entity.User;

import java.util.List;

public class UsersListAdapter extends ArrayAdapter<User> {

    public UsersListAdapter(Context context, List<User> users) {
        super(context, 0, users);
    }
/*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        // Populate the data into the template view using the data object
        tvName.setText(user.name);
        tvHome.setText(user.hometown);
        // Return the completed view to render on screen
        return convertView;
    }
*/
}
