package com.nmakademija.nmaakademija;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.squareup.leakcanary.LeakCanary;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        setFirebaseOptions();
    }

    public void setFirebaseOptions() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseDatabase.setPersistenceEnabled(true);
        firebaseDatabase.setLogLevel(BuildConfig.DEBUG ? Logger.Level.INFO : Logger.Level.NONE);
    }
}
