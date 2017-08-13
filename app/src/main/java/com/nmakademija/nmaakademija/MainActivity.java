package com.nmakademija.nmaakademija;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseUser;
import com.nmakademija.nmaakademija.adapter.BottomNavigationFragmentPagerAdapter;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends BaseActivity implements BottomNavigation.OnMenuItemSelectionListener {

    public static final String EXTRA_SELECTED_TAB_INDEX = "selected_index";

    private int selectedIndex;

    private BottomNavigation bottomNavigation;
    private ViewPager viewPager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && currentUser.isAnonymous()) {
            getMenuInflater().inflate(R.menu.anonymous_main_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.main_menu, menu);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.account:
                Intent intent = new Intent(this, EditProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
            case R.id.login:
                openLogin(id == R.id.login);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openLogin(boolean delete) {
        if (delete && mAuth.getCurrentUser() != null)
            mAuth.getCurrentUser().delete();
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setHomeButtonEnabled(false);

        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            selectedIndex = savedInstanceState.getInt(EXTRA_SELECTED_TAB_INDEX);
        }

        bottomNavigation = (BottomNavigation) findViewById(R.id.bottom_navigation);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        initBottomNavigation();
    }

    void initBottomNavigation() {
        viewPager.setAdapter(new BottomNavigationFragmentPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(final int position) {
                bottomNavigation.setSelectedIndex(position, false);
            }
        });

        bottomNavigation.setSelectedIndex(selectedIndex, false);
        setCurrentFragmentProperties(selectedIndex);


        bottomNavigation.setOnMenuItemClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_SELECTED_TAB_INDEX, selectedIndex);

        super.onSaveInstanceState(outState);
    }

    void setCurrentFragmentProperties(int position) {
        selectedIndex = position;

        int subtitle;

        switch (position) {
            case 0:
                subtitle = R.string.news;
                break;
            case 1:
                subtitle = R.string.academics;
                break;
            case 2:
                subtitle = R.string.timer;
                break;
            case 3:
                subtitle = R.string.schedule;
                break;
            case 4:
                subtitle = R.string.settings;
                break;
            default:
                throw new IllegalArgumentException("Tab with index " + position + " does not exists");
        }
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setSubtitle(getResources().getString(subtitle));
        }
    }

    @Override
    protected void onDestroy() {
        viewPager.clearOnPageChangeListeners();
        super.onDestroy();
    }

    @Override
    public void onMenuItemSelect(@IdRes int itemId, final int position, boolean fromUser) {
        setCurrentFragmentProperties(position);
        if (fromUser) {
            viewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onMenuItemReselect(@IdRes int itemId, final int position, boolean fromUser) {
    }
}
