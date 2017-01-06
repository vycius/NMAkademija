package com.nmakademija.nmaakademija;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.NMAPreferences;

public class StartActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        boolean notificationsEnabled = NMAPreferences.getNotifications(this);

        AppEvent.getInstance(this).setNotificationEnabledUserProperty(notificationsEnabled);
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
}
