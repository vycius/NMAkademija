package com.nmakademija.nmaakademija;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.nmakademija.nmaakademija.fragment.NewsFragment;
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
        setCurrentFragment(0, getSupportActionBar());
    }

    void setCurrentFragment(int position, android.support.v7.app.ActionBar bar) {
        Fragment fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        switch (position) {
            case 0:
                fragment = NewsFragment.getInstance();
                bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bottomNavigationItem1)));
                bar.setSubtitle(getResources().getString(R.string.news));
                break;
            case 1:
                fragment = UsersFragment.getInstance();
                bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bottomNavigationItem2)));
                bar.setSubtitle(getResources().getString(R.string.users));
                break;
            case 2:
                fragment = TimeUntilSessionFragment.getInstance();
                bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bottomNavigationItem3)));
                bar.setSubtitle(getResources().getString(R.string.timer));
                break;
            case 3:
                fragment = TimeUntilSessionFragment.getInstance();
                bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bottomNavigationItem4)));
                bar.setSubtitle(getResources().getString(R.string.schedule));
                break;
            default:
                fragment = NewsFragment.getInstance();
                bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bottomNavigationItem1)));
                bar.setSubtitle(getResources().getString(R.string.news));


        }

        transaction.replace(R.id.main_frame, fragment);

        transaction.commit();
    }

    @Override
    public void onMenuItemSelect(@IdRes final int itemId, final int position) {
        setCurrentFragment(position, getSupportActionBar());
    }

    @Override
    public void onMenuItemReselect(@IdRes final int itemId, final int position) {

    }
}
