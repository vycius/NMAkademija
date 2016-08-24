package com.nmakademija.nmaakademija;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.nmakademija.nmaakademija.fragment.NewsFragment;
import com.nmakademija.nmaakademija.fragment.ScheduleFragment;
import com.nmakademija.nmaakademija.fragment.TimeUntilSessionFragment;
import com.nmakademija.nmaakademija.fragment.UsersFragment;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends AppCompatActivity implements BottomNavigation.OnMenuItemSelectionListener {


    private BottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        bottomNavigation = (BottomNavigation) findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnMenuItemClickListener(this);
        setCurrentFragment(0);
    }

    void setCurrentFragment(int position) {
        Fragment fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        switch (position) {
            case 0:
                fragment = NewsFragment.getInstance();
                break;
            case 1:
                fragment = ScheduleFragment.getInstance();
                break;
            case 2:
                fragment = TimeUntilSessionFragment.getInstance();
                break;

            default:
                fragment = UsersFragment.getInstance();

        }

        transaction.replace(R.id.main_frame, fragment);

        transaction.commit();
    }

    @Override
    public void onMenuItemSelect(@IdRes final int itemId, final int position) {
        setCurrentFragment(position);
    }

    @Override
    public void onMenuItemReselect(@IdRes final int itemId, final int position) {

    }
}
