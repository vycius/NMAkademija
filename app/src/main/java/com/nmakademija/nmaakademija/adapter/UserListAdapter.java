package com.nmakademija.nmaakademija.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.entity.User;
import com.nmakademija.nmaakademija.fragment.UserListFragment;
import com.nmakademija.nmaakademija.fragment.UsersFragment;

import java.util.ArrayList;

/**
 * Created by karolis on 23/08/16.
 */
public class UserListAdapter extends FragmentStatePagerAdapter {
    private UsersFragment usersFragment;

    public UserListAdapter(UsersFragment usersFragment, FragmentManager fm) {
        super(fm);
        this.usersFragment = usersFragment;
    }

    @Override
    public Fragment getItem(int position) {
        ArrayList<User> a = new ArrayList<>();
        for (int i = 0; i < 20; i++) a.add(new User());
        return UserListFragment.newInstance(a);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return usersFragment.getString(R.string.akademikai);
            case 1:
                return usersFragment.getString(R.string.vadoviukai);
            case 2:
                return usersFragment.getString(R.string.destytojai);
            default:
                return "" + position;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
