package com.nmakademija.nmaakademija.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.nmakademija.nmaakademija.R;

public class NMAPreferences {
    public static SharedPreferences getDefault(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public static boolean isFirstTime(Context context) {
        return getSection(context) == 0;
    }

    public static int getSection(Context context) {
        return getDefault(context)
                .getInt(context.getString(R.string.section_key), 0);
    }

    public static void setSection(Context context, int section) {
        getDefault(context)
                .edit()
                .putInt(context.getString(R.string.section_key), section)
                .apply();
    }

    public static boolean getNotifications(Context context) {
        return getDefault(context)
                .getBoolean(context.getString(R.string.get_notifications_key), true);
    }
}
