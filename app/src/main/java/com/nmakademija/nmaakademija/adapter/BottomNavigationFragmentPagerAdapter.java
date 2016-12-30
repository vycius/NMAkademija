package com.nmakademija.nmaakademija.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nmakademija.nmaakademija.fragment.NewsFragment;
import com.nmakademija.nmaakademija.fragment.ScheduleFragment;
import com.nmakademija.nmaakademija.fragment.TimeUntilSessionFragment;
import com.nmakademija.nmaakademija.fragment.UsersFragment;

public class BottomNavigationFragmentPagerAdapter extends FragmentPagerAdapter {

    public BottomNavigationFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NewsFragment.getInstance();
            case 1:
                return UsersFragment.getInstance();
            case 2:
                return TimeUntilSessionFragment.getInstance();
            case 3:
                return ScheduleFragment.getInstance();
        }

        throw new IllegalArgumentException("Tab with index " + position + " does not exists");
    }

    @Override
    public int getCount() {
        return 4;
    }
}
