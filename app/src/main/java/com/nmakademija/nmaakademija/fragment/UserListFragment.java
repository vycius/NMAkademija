package com.nmakademija.nmaakademija.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import com.nmakademija.nmaakademija.adapter.UsersAdapter;
import com.nmakademija.nmaakademija.entity.User;

import java.util.ArrayList;

/**
 * Created by NMA on 2016.08.23.
 */
public class UserListFragment extends ListFragment {
    private final static String KEY_LIST = "list";
    private ArrayList<User> users;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = (ArrayList<User>) getArguments().getSerializable(KEY_LIST);
        setListAdapter(new UsersAdapter(getActivity(), users));
    }

    public static UserListFragment newInstance(ArrayList<User> users) {
        UserListFragment ulf = new UserListFragment();
        Bundle b = new Bundle();
        b.putSerializable(KEY_LIST, users);
        ulf.setArguments(b);
        return ulf;
    }

}
