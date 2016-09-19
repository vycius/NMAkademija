package com.nmakademija.nmaakademija;

import android.content.Intent;
import android.support.v7.preference.PreferenceManager;

public class StartActivity extends BaseActivity {
    @Override
    protected void onResume() {
        super.onResume();

        Intent intent;
        if ("0".equals(PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString(getString(R.string.section_key), "0"))) {
            intent = new Intent(this, OnboardingActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
