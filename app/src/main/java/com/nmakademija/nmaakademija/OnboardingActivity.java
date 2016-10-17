package com.nmakademija.nmaakademija;

import android.os.Bundle;

import com.nmakademija.nmaakademija.fragment.OnboardingFragment;

public class OnboardingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_onboarding);

        getSupportActionBar().hide();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new OnboardingFragment())
                .commit();
    }
}
