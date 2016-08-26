package com.nmakademija.nmaakademija.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.nmakademija.nmaakademija.R;

public class Error {
    public static void getData(View view) {
        if (view != null)
            Snackbar.make(view, R.string.request_failed, Snackbar.LENGTH_SHORT).show();
    }

    public static void oldData(View view) {
        if (view != null) {
            Snackbar.make(view, R.string.old_data_in_API, Snackbar.LENGTH_LONG).show();
        }
    }
}
