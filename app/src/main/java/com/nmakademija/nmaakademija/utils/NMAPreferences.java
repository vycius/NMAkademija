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
        return getSection(context) < 0;
    }

    public static int getSection(Context context) {
        return getDefault(context)
                .getInt(context.getString(R.string.section_key), -1);
    }

    public static void setSection(Context context, int section) {
        getDefault(context)
                .edit()
                .putInt(context.getString(R.string.section_key), section)
                .apply();
    }

    public static boolean getIsAcademic(Context context) {
        return getDefault(context)
                .getBoolean(context.getString(R.string.is_academic_key), false);
    }

    public static boolean isSetIsAcademic(Context context) {
        return getDefault(context)
                .contains(context.getString(R.string.is_academic_key));
    }

    public static void clear(Context context) {
        getDefault(context).edit().clear().apply();
    }

    public static void setIsAcademic(Context context, boolean isAcademic) {
        getDefault(context)
                .edit()
                .putBoolean(context.getString(R.string.is_academic_key), isAcademic)
                .apply();
    }

    public static boolean getNotifications(Context context) {
        return getDefault(context)
                .getBoolean(context.getString(R.string.get_notifications_key), true);
    }
}
