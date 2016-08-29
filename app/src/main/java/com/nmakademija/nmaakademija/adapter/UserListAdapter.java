package com.nmakademija.nmaakademija.adapter;

import android.content.Context;
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
import com.nmakademija.nmaakademija.entity.Section;
import com.nmakademija.nmaakademija.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> implements Filterable {
    private Context context;
    private ArrayList<User> users;
    private ArrayList<User> allUsers;
    private List<Section> sections;
    private View supervisorLayout;
    private TextView supervisor;

    public UserListAdapter(Context context, ArrayList<User> users, List<Section> sections) {
        super(context, 0, users);
        this.context = context;
        allUsers = users;
        this.sections = sections;
        this.users = allUsers;
//        supervisorLayout = ((Activity) context).findViewById(R.id.supervisor_layout);
//        supervisor = (TextView) ((Activity) context).findViewById(R.id.supervisor);
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

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                users = (ArrayList<User>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                int i = Integer.parseInt(constraint.toString());

                ArrayList<User> newUserList = new ArrayList<>();
                if (i == 0) {
                    newUserList = allUsers;
//                    supervisorLayout.setVisibility(View.GONE);
                } else {
//                    supervisor.setText(sections.get(i).getSupervisor());
//                    supervisorLayout.setVisibility(View.VISIBLE);
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

}
