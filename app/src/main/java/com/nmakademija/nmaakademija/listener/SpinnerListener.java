package com.nmakademija.nmaakademija.listener;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.nmakademija.nmaakademija.R;
import com.nmakademija.nmaakademija.adapter.UserListAdapter;

public class SpinnerListener implements AdapterView.OnItemSelectedListener {
    private final View supervisorLayout;
    private final TextView supervisor;
    private ListView listView;

    public SpinnerListener(ListView listView, Activity activity) {
        this.listView = listView;
        supervisorLayout = activity.findViewById(R.id.supervisor_layout);
        supervisor = (TextView) activity.findViewById(R.id.supervisor);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (listView != null) {
            if (i == 0) {
                supervisorLayout.setVisibility(View.GONE);
            } else {
                supervisor.setText(((UserListAdapter) listView.getAdapter()).getSupervisor(i));
                supervisorLayout.setVisibility(View.VISIBLE);
            }
            ((UserListAdapter) listView.getAdapter()).getFilter().filter(String.valueOf(i));
            listView.setSelectionAfterHeaderView();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
