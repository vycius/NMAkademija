package com.nmakademija.nmaakademija.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.UserListAdapter;

public class UsersFragment extends Fragment {

    public static UsersFragment getInstance() {
        return new UsersFragment();
    }

    private ViewPager pager;
    private TabLayout tabs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.users_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pager = (ViewPager) getView().findViewById(R.id.viewPager);
        tabs = (TabLayout) getView().findViewById(R.id.tabs);

        pager.setAdapter(new UserListAdapter(this, getChildFragmentManager()));
        tabs.setupWithViewPager(pager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.activity_user_list, menu);
    }

}
