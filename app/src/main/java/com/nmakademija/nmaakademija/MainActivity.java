package com.nmakademija.nmaakademija;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.nmakademija.nmaakademija.fragment.NewsFragment;
import com.nmakademija.nmaakademija.fragment.ScheduleFragment;
import com.nmakademija.nmaakademija.fragment.TimeUntilSessionFragment;
import com.nmakademija.nmaakademija.fragment.UsersFragment;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends AppCompatActivity implements BottomNavigation.OnMenuItemSelectionListener {

    private BottomNavigation bottomNavigation;
    private int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        bottomNavigation = (BottomNavigation) findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnMenuItemClickListener(this);

        selectedIndex = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bottomNavigation.getSelectedIndex() == -1) {
            setCurrentFragment(selectedIndex);
            bottomNavigation.setSelectedIndex(selectedIndex, false);
        }
    }

    void setCurrentFragment(int position) {
        selectedIndex = position;
        Fragment fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        int color, subtitle;

        switch (position) {
            case 1:
                fragment = UsersFragment.getInstance();
                color = R.color.bottomNavigationUsersTab;
                subtitle = R.string.users;
                break;
            case 2:
                fragment = TimeUntilSessionFragment.getInstance();
                color = R.color.bottomNavigationTimerTab;
                subtitle = R.string.timer;
                break;
            case 3:
                fragment = ScheduleFragment.getInstance();
                color = R.color.bottomNavigationScheduleTab;
                subtitle = R.string.schedule;
                break;
            default:
                fragment = NewsFragment.getInstance();
                color = R.color.bottomNavigationNewsTab;
                subtitle = R.string.news;
        }
        int colorResource = ContextCompat.getColor(this, color);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(colorResource));
        bar.setSubtitle(getResources().getString(subtitle));

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(colorResource);
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
