package com.nmakademija.nmaakademija.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.nmakademija.nmaakademija.ProfileActivity;
import com.nmakademija.nmaakademija.adapter.UsersAdapter;
import com.nmakademija.nmaakademija.entity.User;

import java.util.ArrayList;

public class UserListFragment extends ListFragment {
    private final static String KEY_LIST = "list";
    private ArrayList<User> users;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = (ArrayList<User>) getArguments().getSerializable(KEY_LIST);
        setListAdapter(new UsersAdapter(getActivity(), users));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(getActivity(), ProfileActivity.class);
        i.putExtra(ProfileActivity.EXTRA_ALLOW_EDIT, false);
        i.putExtra(ProfileActivity.EXTRA_USER, users.get(position));
        startActivity(i);
    }

    public static UserListFragment newInstance(ArrayList<User> users) {
        UserListFragment ulf = new UserListFragment();
        Bundle b = new Bundle();
        b.putSerializable(KEY_LIST, users);
        ulf.setArguments(b);
        return ulf;
    }

}
