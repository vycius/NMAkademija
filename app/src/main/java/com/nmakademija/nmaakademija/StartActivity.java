package com.nmakademija.nmaakademija;

import android.content.Intent;

import com.nmakademija.nmaakademija.utils.NMAPreferences;

public class StartActivity extends BaseActivity {
    @Override
    protected void onResume() {
        super.onResume();

        Intent intent;
        if (NMAPreferences.getSection(this) == 0) {
            intent = new Intent(this, OnboardingActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
