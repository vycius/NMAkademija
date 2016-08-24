package com.nmakademija.nmaakademija.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nmakademija.nmaakademija.ProfileActivity;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.UserListAdapter;
import com.nmakademija.nmaakademija.api.API;
import com.nmakademija.nmaakademija.entity.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        API.nmaService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    pager.setAdapter(new UserListAdapter(activity,
                            (ArrayList<User>) response.body(), getChildFragmentManager()));
                    tabs.setupWithViewPager(pager);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                View view = getView();
                if (view != null)
                    Snackbar.make(view, R.string.request_failed, Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.activity_user_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_profile) {
            //TODO pass user
            Intent i = new Intent(getActivity(), ProfileActivity.class);
            i.putExtra(ProfileActivity.EXTRA_ALLOW_EDIT, true);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
