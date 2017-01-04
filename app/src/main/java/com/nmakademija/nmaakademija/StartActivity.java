package com.nmakademija.nmaakademija;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.NMAPreferences;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean notificationsEnabled = NMAPreferences.getNotifications(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        AppEvent.getInstance(this).setNotificationEnabledUserProperty(notificationsEnabled);
    }

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
