package com.nmakademija.nmaakademija;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseUser;
import com.nmakademija.nmaakademija.adapter.BottomNavigationFragmentPagerAdapter;
import com.nmakademija.nmaakademija.utils.NMAPreferences;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends BaseActivity implements BottomNavigation.OnMenuItemSelectionListener {

    public static final String EXTRA_SELECTED_TAB_INDEX = "selected_index";

    private int selectedIndex;

    private BottomNavigation bottomNavigation;
    private ViewPager viewPager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mAuth.getCurrentUser().isAnonymous()) {
            getMenuInflater().inflate(R.menu.anonymous_main_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.main_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem account = menu.findItem(R.id.account);
        if (account != null && !NMAPreferences.getIsAcademic(this)) {
            account.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.account) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.logout || id == R.id.login) {
            openLogin();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openLogin() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.isAnonymous()) {
            user.delete();
        }
        NMAPreferences.clear(this);
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

        int color, subtitle;

        switch (position) {
            case 0:
                color = R.color.bottomNavigationNewsTab;
                subtitle = R.string.news;
                break;
            case 1:
                color = R.color.bottomNavigationUsersTab;
                subtitle = R.string.academics;
                break;
            case 2:
                color = R.color.bottomNavigationTimerTab;
                subtitle = R.string.timer;
                break;
            case 3:
                color = R.color.bottomNavigationScheduleTab;
                subtitle = R.string.schedule;
                break;
            default:
                throw new IllegalArgumentException("Tab with index " + position + " does not exists");
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
