package com.nmakademija.nmaakademija.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.utils.Preferences;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = Preferences.get(getContext().getApplicationContext());
        final String getNotifications = Preferences.GET_NOTIFICATIONS;
        final SharedPreferences.Editor editor = preferences.edit();

        CheckBoxPreference checkBox = (CheckBoxPreference) getPreferenceManager().findPreference("get_notifications");
        checkBox.setChecked(preferences.getBoolean(getNotifications, false));

        checkBox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue instanceof Boolean) {
                    editor.putBoolean(getNotifications, (Boolean) newValue)
                            .apply();
                    return true;
                }
                return false;
            }
        });
    }
}
