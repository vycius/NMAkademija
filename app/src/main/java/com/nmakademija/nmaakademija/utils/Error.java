package com.nmakademija.nmaakademija.utils;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;

public class Error {

    public static void getData(@Nullable View view, View.OnClickListener listener) {
        if (view != null) {
            Snackbar snackbar = Snackbar.make(view, R.string.get_request_failed, Snackbar.LENGTH_INDEFINITE);
            TextView tv = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackbar.setAction(R.string.retry, listener);
            snackbar.show();
        }
    }

    public static void saveData(@Nullable View view, View.OnClickListener listener) {
        if (view != null) {
            Snackbar snackbar = Snackbar.make(view, R.string.save_request_failed, Snackbar.LENGTH_INDEFINITE);
            TextView tv = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackbar.setAction(R.string.retry, listener);
            snackbar.show();
        }
    }

    public static void loadProfile(@Nullable View view) {
        if (view != null) {
            Snackbar snackbar = Snackbar.make(view, R.string.profile_request_failed, Snackbar.LENGTH_INDEFINITE);
            TextView tv = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }
}
