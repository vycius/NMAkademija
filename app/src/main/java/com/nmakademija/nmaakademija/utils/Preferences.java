package com.nmakademija.nmaakademija.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    public final static String GET_NOTIFICATIONS = "getNotifications";
    private final static String PREFS_NAME = "NMA_session_prefs";

    public static SharedPreferences get(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0);
    }
}
