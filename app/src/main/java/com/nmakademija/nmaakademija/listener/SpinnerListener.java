package com.nmakademija.nmaakademija.listener;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.UserListAdapter;

public class SpinnerListener implements AdapterView.OnItemSelectedListener {
    private final TextView supervisor;
    private ListView listView;

    public SpinnerListener(ListView listView, Activity activity) {
        this.listView = listView;
        supervisor = (TextView) activity.findViewById(R.id.supervisor);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (listView != null) {
            if (i == 0) {
                supervisor.setVisibility(View.GONE);
            } else {
                supervisor.setText(view.getResources().getString(
                        R.string.before_supervisor_name).concat(
                        ((UserListAdapter) listView.getAdapter()).
                                getSupervisor(i)));
                supervisor.setVisibility(View.VISIBLE);
            }
            ((UserListAdapter) listView.getAdapter()).getFilter().filter(String.valueOf(i));
            listView.setSelectionAfterHeaderView();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
