package com.nmakademija.nmaakademija;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;

import com.nmakademija.nmaakademija.fragment.OnboardingFragment;

public class OnboardingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!"0".equals(PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString(getString(R.string.section_key), "0"))) {
            Intent intent = new Intent(this, MainActivity.class);
            //startActivity(intent);
            //finish();
        }

        setContentView(R.layout.activity_onboarding);

        //getSupportActionBar().hide();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new OnboardingFragment())
                .commit();
    }
}
