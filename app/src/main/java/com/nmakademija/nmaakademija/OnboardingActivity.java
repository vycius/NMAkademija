package com.nmakademija.nmaakademija;

import android.os.Bundle;

import com.nmakademija.nmaakademija.fragment.OnboardingFragment;

public class OnboardingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new OnboardingFragment())
                .commit();
    }
}
