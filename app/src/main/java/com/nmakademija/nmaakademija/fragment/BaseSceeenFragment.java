package com.nmakademija.nmaakademija.fragment;

import android.support.v4.app.Fragment;

import com.nmakademija.nmaakademija.App;
import com.squareup.leakcanary.RefWatcher;


public class BaseSceeenFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
