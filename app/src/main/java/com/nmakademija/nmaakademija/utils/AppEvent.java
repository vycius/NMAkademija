package com.nmakademija.nmaakademija.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.analytics.FirebaseAnalytics;

public class AppEvent {

    private static AppEvent appEvent;
    private FirebaseAnalytics firebaseAnalytics;

    private AppEvent(Context context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context.getApplicationContext());
    }

    public static AppEvent getInstance(Context context) {
        if (appEvent == null) {
            appEvent = new AppEvent(context);
        }

        return appEvent;
    }

    public void trackCurrentScreen(@NonNull Activity activity, @NonNull String name) {
        firebaseAnalytics.setCurrentScreen(activity, name, null);

        logEvent(name, null);
    }

    public void trackArticleClicked(int id) {
        Bundle bundle = new Bundle();

        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(id));
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "article");

        logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void trackUserClicked(@NonNull String name) {
        Bundle bundle = new Bundle();

        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "user");

        logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    void logEvent(@NonNull String name, @Nullable Bundle bundle) {
        firebaseAnalytics.logEvent(name, bundle);
    }

}
