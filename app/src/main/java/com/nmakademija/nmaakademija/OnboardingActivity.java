package com.nmakademija.nmaakademija;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.nmakademija.nmaakademija.fragment.OnboardingFragment;
import com.nmakademija.nmaakademija.utils.NMAPreferences;

public class OnboardingActivity extends BaseActivity {

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_onboarding);

        getSupportActionBar().hide();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, new OnboardingFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (NMAPreferences.getSection(this) != 0) {
            finish();
        } else if (doubleBackToExitPressedOnce) {
            finish();
        } else {

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Dar kartą, kad išeitum", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }
}
