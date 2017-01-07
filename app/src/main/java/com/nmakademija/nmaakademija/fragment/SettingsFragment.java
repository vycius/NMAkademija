package com.nmakademija.nmaakademija.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.nmakademija.nmaakademija.App;
import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.utils.AppEvent;
import com.squareup.leakcanary.RefWatcher;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private AppEvent appEvent;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        appEvent = AppEvent.getInstance(getContext());
        appEvent.trackCurrentScreen(getActivity(), "open_settings");
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (getString(R.string.get_notifications_key).equals(s)) {
            boolean enabled = sharedPreferences.getBoolean(s, true);

            appEvent.trackNotificationsEnabled(enabled);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
