package com.nmakademija.nmaakademija.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.nmakademija.nmaakademija.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
    }
}
