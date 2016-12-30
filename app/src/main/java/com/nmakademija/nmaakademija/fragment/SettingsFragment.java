package com.nmakademija.nmaakademija.fragment;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.utils.AppEvent;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AppEvent.getInstance(getContext()).trackCurrentScreen(getActivity(), "open_settings");
    }
}
