package com.nmakademija.nmaakademija.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.User;
import com.nmakademija.nmaakademija.fragment.UserListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karolis on 23/08/16.
 */
public class UserListAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private ArrayList<User> users;

    public UserListAdapter(Context c, ArrayList<User> u, FragmentManager fm) {
        super(fm);
        context = c;
        users = u;
    }

    @Override
    public Fragment getItem(int position) {
        return UserListFragment.newInstance(users);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.akademikai);
            case 1:
                return context.getString(R.string.vadoviukai);
            case 2:
                return context.getString(R.string.destytojai);
            default:
                return "" + position;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
