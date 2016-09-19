package com.nmakademija.nmaakademija;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;

public class StartActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent;
        if (!"0".equals(PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString(getString(R.string.section_key), "0"))) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, OnboardingActivity.class);
        }
        startActivity(intent);
    }
}
