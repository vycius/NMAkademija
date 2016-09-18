package com.nmakademija.nmaakademija;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.nmakademija.nmaakademija.fragment.SettingsFragment;

public class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setSubtitle(R.string.settings);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new SettingsFragment())
                .commit();
    }
}