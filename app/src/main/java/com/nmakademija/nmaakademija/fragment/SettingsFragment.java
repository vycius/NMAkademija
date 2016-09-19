package com.nmakademija.nmaakademija.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.nmakademija.nmaakademija.OnboardingActivity;
import com.nmakademija.nmaakademija.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        Preference myPref = findPreference(getString(R.string.section_key));
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getContext(), OnboardingActivity.class);
                startActivity(intent);
                getActivity().finish();
                return true;
            }
        });
    }


}
