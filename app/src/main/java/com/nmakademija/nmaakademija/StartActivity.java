package com.nmakademija.nmaakademija;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.google.firebase.database.FirebaseDatabase;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.nmakademija.nmaakademija.utils.NMAPreferences;

public class StartActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        boolean notificationsEnabled = NMAPreferences.getNotifications(this);

        AppEvent.getInstance(this).setNotificationEnabledUserProperty(notificationsEnabled);

        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
}
