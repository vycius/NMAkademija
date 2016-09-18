package com.nmakademija.nmaakademija.fragment;

import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.api.API;
import com.nmakademija.nmaakademija.entity.Section;
import com.nmakademija.nmaakademija.utils.Error;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends PreferenceFragmentCompat {

    List<Section> sections;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        API.nmaService.getSections().enqueue(new Callback<List<Section>>() {
            @Override
            public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                sections = response.body();
                CharSequence[] entries = new CharSequence[sections.size()];
                CharSequence[] entriesValues = new CharSequence[sections.size()];
                for (int i = 0; i < sections.size(); i++) {
                    Section section = sections.get(i);
                    entries[i] = section.getName();
                    entriesValues[i] = String.valueOf(section.getId());
                }
                addPreferencesFromResource(R.xml.preferences);
                ListPreference chooseSection = (ListPreference) findPreference(getResources().getString(R.string.section_key));
                chooseSection.setEntries(entries);
                chooseSection.setEntryValues(entriesValues);
            }

            @Override
            public void onFailure(Call<List<Section>> call, Throwable t) {
                Error.getData(getView());
            }
        });
    }

}
